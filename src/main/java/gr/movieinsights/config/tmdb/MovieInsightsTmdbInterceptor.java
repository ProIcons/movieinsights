package gr.movieinsights.config.tmdb;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class MovieInsightsTmdbInterceptor implements Interceptor {

    private final Logger log = LoggerFactory.getLogger(MovieInsightsTmdbInterceptor.class);

    private final MovieInsightsTmdb tmdb;

    public MovieInsightsTmdbInterceptor(MovieInsightsTmdb tmdb) {
        this.tmdb = tmdb;

    }

    private void addSessionToken(com.uwetrottmann.tmdb2.Tmdb tmdb, HttpUrl.Builder urlBuilder) {
        // prefer account session if both are available
        if (tmdb.getSessionId() != null) {
            urlBuilder.addEncodedQueryParameter(com.uwetrottmann.tmdb2.Tmdb.PARAM_SESSION_ID, tmdb.getSessionId());
        } else if (tmdb.getGuestSessionId() != null) {
            urlBuilder.addEncodedQueryParameter(com.uwetrottmann.tmdb2.Tmdb.PARAM_GUEST_SESSION_ID, tmdb.getGuestSessionId());
        }
    }

    private Request processTmdbRequest(Request request) {
        HttpUrl.Builder urlBuilder = request.url().newBuilder();
        urlBuilder.setEncodedQueryParameter(com.uwetrottmann.tmdb2.Tmdb.PARAM_API_KEY, tmdb.apiKey());

        if (tmdb.isLoggedIn()) {
            // add auth only for paths that require it
            List<String> pathSegments = request.url().pathSegments();
            if ((pathSegments.size() >= 2 && pathSegments.get(1).equals("account"))
                || pathSegments.get(pathSegments.size() - 1).equals("account_states")
                || pathSegments.get(pathSegments.size() - 1).equals("rating")
                || !request.method().equals("GET")) {
                addSessionToken(tmdb, urlBuilder);
            }
        }

        return request.newBuilder().url(urlBuilder.build()).build();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!com.uwetrottmann.tmdb2.Tmdb.API_HOST.equals(request.url().host())) {
            // do not intercept requests for other hosts
            // this allows the interceptor to be used on a shared okhttp client
            return chain.proceed(request);
        }

        final Request finalRequest = processTmdbRequest(request);

        String url = request.url().toString().replace(tmdb.apiKey(), "{redacted}");
        log.debug("Request on: " + url);
        Response response;

        response = chain.proceed(finalRequest);

        if (!response.isSuccessful()) {

            // re-try if the server indicates we should
            String retryHeader = response.header("Retry-After");
            if (retryHeader != null) {
                try {

                    int retry = Integer.parseInt(retryHeader);
                    log.warn("Response from {} was scheduled in {}s for another run cause RateLimit was exceeded.", url, (retry + 0.5));
                    Thread.sleep((int) ((retry + 0.5) * 1000));

                    // close body of unsuccessful response
                    if (response.body() != null) {
                        response.body().close();
                    }

                    // is fine because, unlike a network interceptor, an application interceptor can re-try requests
                    return intercept(chain);
                } catch (NumberFormatException | InterruptedException ignored) {
                }
            }
        }

        log.debug("Response from " + url + " was successful.");

        return response;
    }
}
