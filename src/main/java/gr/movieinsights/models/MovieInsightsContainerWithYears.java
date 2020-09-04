package gr.movieinsights.models;

import gr.movieinsights.domain.MovieInsightsContainer;
import gr.movieinsights.domain.MovieInsightsPerYear;

import java.util.Set;

public interface MovieInsightsContainerWithYears extends MovieInsightsContainer {
    MovieInsightsContainerWithYears addMovieInsightsPerYear(MovieInsightsPerYear movieInsightsPerYear);
    Set<MovieInsightsPerYear> getMovieInsightsPerYears();
    void setMovieInsightsPerYears(Set<MovieInsightsPerYear> movieInsightsPerYears);
}
