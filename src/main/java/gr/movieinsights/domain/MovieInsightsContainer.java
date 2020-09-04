package gr.movieinsights.domain;

public interface MovieInsightsContainer extends IdentifiedEntity {
    MovieInsights getMovieInsights();
    void setMovieInsights(MovieInsights movieInsights);
}
