package gr.movieinsights.domain.enumeration;

/**
 * The EntityType enumeration.
 */
public enum TmdbEntityType {
    MOVIE("movie_ids"),
    PERSON("person_ids"),
    COMPANY("company_ids"),
    GENRE("genre_ids"),
    COUNTRY("country_ids"),
    CREDIT("credit_ids"),
    VOTE("vote_ids");

    String identifier;

    TmdbEntityType(String identifier) {
        this.identifier = identifier;
    }

    public String getExportIdentifier() {
        return identifier;
    }
}
