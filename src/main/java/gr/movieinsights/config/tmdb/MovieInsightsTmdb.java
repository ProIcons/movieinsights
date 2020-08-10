package gr.movieinsights.config.tmdb;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.TmdbHelper;
import gr.movieinsights.config.Constants;
import gr.movieinsights.config.tmdb.services.MoviesService;
import gr.movieinsights.config.tmdb.util.AddressUtils;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MovieInsightsTmdb extends Tmdb {

    List<Retrofit> retrofitInstances;
    List<OkHttpClient> okHttpClientInstances;
    AtomicInteger retrofitInstancePointer;
    AtomicInteger okHttpClientInstancePointer;

    RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
        .timeoutDuration(Duration.ofSeconds(Constants.TMDB_RATELIMITER_TIMEOUT_SECONDS))
        .limitForPeriod(Constants.TMDB_RATELIMITER_LIMIT_REQUESTS)
        .build();

    RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(rateLimiterConfig);

    private final Logger log = LoggerFactory.getLogger(MovieInsightsTmdbInterceptor.class);

    Cache cache = new Cache(new File("tmdb.cache"), 10 * 1024 * 1024 * 1024L);

    List<InetAddress> inetAddresses;

    /**
     * Create a new manager instance.
     *
     * @param apiKey
     *     Your TMDB API key.
     */
    public MovieInsightsTmdb(String apiKey) {
        super(apiKey);
        this.retrofitInstances = new LinkedList<>();
        this.okHttpClientInstances = new LinkedList<>();
        try {
            inetAddresses = AddressUtils.getMachineIPAddresses("https://1.1.1.1/");
            if (inetAddresses.size() == 0) {
                throw new RuntimeException("You have no addresses accessible to the Internet.");
            }
            if (inetAddresses.get(0).isSiteLocalAddress()) {
                buildHttpClient(inetAddresses.get(0));
            } else {
                for (InetAddress inetAddress : inetAddresses) {
                    buildHttpClient(inetAddress);
                }
            }
        } catch (SocketException ignored) {
        }
        retrofitInstancePointer = new AtomicInteger(0);
        okHttpClientInstancePointer = new AtomicInteger(0);
        log.debug("Using {} HTTP Client(s) over {} address(es).", okHttpClientInstances.size(), okHttpClientInstances.size());
    }

    private void buildHttpClient(InetAddress inetAddress) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cache(cache)
            .socketFactory(new MovieInsightsSocketFactory(inetAddress))
            .addNetworkInterceptor(new MovieInsightsTmdbNetworkInterceptor(this, rateLimiterRegistry.rateLimiter(inetAddress.getCanonicalHostName())))
            .addInterceptor(new MovieInsightsTmdbInterceptor(this))
            .build();
        okHttpClient.dispatcher().setMaxRequestsPerHost(40);
        okHttpClient.dispatcher().setMaxRequests(40 * 10);
        okHttpClientInstances.add(okHttpClient);

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create(TmdbHelper.getGsonBuilder().create()))
            .client(okHttpClient)
            .build();
        retrofitInstances.add(retrofit);

    }

    public int getMaximumRequestsPerCycle() {
        return okHttpClientInstances.size() * 40;
    }

    @Override
    protected Retrofit getRetrofit() {
        synchronized (retrofitInstances) {
            if (retrofitInstancePointer.get() == retrofitInstances.size()) {
                retrofitInstancePointer.set(0);
            }
            return retrofitInstances.get(retrofitInstancePointer.getAndIncrement());
        }
    }

    public synchronized OkHttpClient getOkHttpClient() {
        if (okHttpClientInstancePointer.get() == okHttpClientInstances.size()) {
            okHttpClientInstancePointer.set(0);
        }
        return okHttpClientInstances.get(okHttpClientInstancePointer.getAndIncrement());
    }

    public MoviesService moviesService2() {
        return getRetrofit().create(MoviesService.class);
    }
}
