package gr.movieinsights.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String ANONYMOUS_USER = "anonymoususer";

    public static final Integer TMDB_RATELIMITER_TIMEOUT_SECONDS = 11;
    public static final Integer TMDB_RATELIMITER_LIMIT_REQUESTS = 40;

    public static final Long MINIMUM_REVENUE_THRESHOLD = 10000L;
    public static final Long MINIMUM_BUDGET_THRESHOLD = 10000L;
    public static final Long MINIMUM_VOTES_THRESHOLD = 10000L;
    private Constants() {
    }
}
