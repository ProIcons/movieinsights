package gr.movieinsights.service.util.wrappers.movieinsights;

import gr.movieinsights.domain.MovieInsightsContainer;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.BaseWrapper;

public interface IParameterizedMovieInsightsWrapper<C extends MovieInsightsContainer, B extends BaseWrapper<E>, E> extends IMovieInsightsWrapper {
    IParameterizedMovieInsightsWrapper<? extends MovieInsightsContainer, ? extends BaseWrapper<?>, ?> getMaster();

    B getSource();

    C build();
}
