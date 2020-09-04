package gr.movieinsights.service.dto.movieinsights;

public interface IMovieInsightsContainerDTO {
    Long getId();
    void setId(Long id);
    MovieInsightsDTO getMovieInsights();
    void setMovieInsights(MovieInsightsDTO movieInsights);
}
