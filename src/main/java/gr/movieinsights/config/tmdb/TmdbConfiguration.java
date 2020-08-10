package gr.movieinsights.config.tmdb;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TmdbConfiguration {
    private MovieInsightsTmdb movieInsightsTmdbInstance;

    @Value("${application.tmdb.api-key}")
    private String apiKey;

    @Bean
    public MovieInsightsTmdb tmdbInstance() {
        if (movieInsightsTmdbInstance == null) {
            movieInsightsTmdbInstance = new MovieInsightsTmdb(apiKey);
        }
        return movieInsightsTmdbInstance;
    }

    @Bean
    public OkHttpClient okHttpClientInstance() {
        return tmdbInstance().getOkHttpClient();
    }

}
