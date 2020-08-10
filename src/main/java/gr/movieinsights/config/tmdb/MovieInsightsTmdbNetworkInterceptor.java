package gr.movieinsights.config.tmdb;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.vavr.CheckedFunction1;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MovieInsightsTmdbNetworkInterceptor implements Interceptor {
    private final Logger log = LoggerFactory.getLogger(MovieInsightsTmdbNetworkInterceptor.class);
    private final CacheControl cacheControl = new CacheControl.Builder()
        .maxAge(365, TimeUnit.DAYS)
        .build();

    private final MovieInsightsTmdb tmdb;
    private final CheckedFunction1<Chain, Response> responseSupplier;

    // Interceptor to ask for cached only responses
    public MovieInsightsTmdbNetworkInterceptor(MovieInsightsTmdb tmdb, RateLimiter rateLimiter) {
        this.tmdb = tmdb;
        responseSupplier = RateLimiter.decorateCheckedFunction(rateLimiter, this::rateLimitedIntercept);
    }

    public Response rateLimitedIntercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        String url = chain.request().url().toString().replace(tmdb.apiKey(), "{redacted}");
        log.debug("Request on: " + url + " - Routed through: " + chain.connection().socket().getLocalAddress().getHostAddress());
        return response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build();
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!com.uwetrottmann.tmdb2.Tmdb.API_HOST.equals(request.url().host())) {
            // do not intercept requests for other hosts
            // this allows the interceptor to be used on a shared okhttp client
            return chain.proceed(request);
        }

        try {
            return responseSupplier.apply(chain);
        } catch (Throwable throwable) {
            if (throwable instanceof IOException) {
                throw (IOException) throwable;
            }
            return null;
        }
    }
}
