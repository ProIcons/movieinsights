package gr.movieinsights.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * Properties specific to Movie Insights.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private Tmdb tmdb = new Tmdb();

    public Tmdb getTmdb() {
        return tmdb;
    }

    public void setTmdb(Tmdb tmdb) {
        this.tmdb = tmdb;
    }

    public static class Cache {
        private String path = "tmdb.cache";
        private Duration maxAge = Duration.ofDays(1);
        private Long maxSizeBytes = 10 * 1024 * 1024 * 1024L;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Duration getMaxAge() {
            return maxAge;
        }

        public void setMaxAge(Duration maxAge) {
            this.maxAge = maxAge;
        }

        public Long getMaxSizeBytes() {
            return maxSizeBytes;
        }

        public void setMaxSizeBytes(Long maxSizeBytes) {
            this.maxSizeBytes = maxSizeBytes;
        }
    }

    public static class Tmdb {

        private Cache cache = null;

        private String apiKey = null;

        public Cache getCache() {
            return cache;
        }

        public void setCache(Cache cache) {
            this.cache = cache;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            apiKey = apiKey;
        }
    }
}
