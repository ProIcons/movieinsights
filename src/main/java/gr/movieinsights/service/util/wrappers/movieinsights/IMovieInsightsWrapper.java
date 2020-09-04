package gr.movieinsights.service.util.wrappers.movieinsights;

import gr.movieinsights.domain.Movie;
import gr.movieinsights.service.enumeration.MovieInsightsCategory;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.BaseWrapper;

import java.util.Collection;

public interface IMovieInsightsWrapper {
    MovieInsightsCategory getCategory();

    boolean isSlave();

    BaseWrapper<?> getSource();

    MovieInsightsYearWrapper getMovieInsightsWrapperByYear(int year);

    Collection<MovieInsightsYearWrapper> getMovieInsightsPerYearWrappers();
    void submitMovie(Movie movie);
}
