package gr.movieinsights.models;

import gr.movieinsights.domain.MovieInsightsContainerWithEntity;
import gr.movieinsights.domain.MovieInsightsPerYear;

public interface MovieInsightsContainerWithYearsWithEntity<E> extends MovieInsightsContainerWithYears, MovieInsightsContainerWithEntity<E> {
    MovieInsightsContainerWithYearsWithEntity<E> addMovieInsightsPerYear(MovieInsightsPerYear movieInsightsPerYear);
}
