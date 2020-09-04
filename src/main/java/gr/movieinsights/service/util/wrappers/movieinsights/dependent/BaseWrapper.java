package gr.movieinsights.service.util.wrappers.movieinsights.dependent;

import gr.movieinsights.domain.Movie;
import gr.movieinsights.service.enumeration.MovieInsightsCategory;
import gr.movieinsights.util.CalculationUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseWrapper<T> implements Serializable {
    public final T object;
    public final Set<Movie> movies;
    public int count;
    public MovieInsightsCategory category;

    public BaseWrapper(T object, MovieInsightsCategory category) {
        this.object = object;
        this.movies = new HashSet<>();
        this.category = category;
    }

    protected Double getMoviesAverageScore() {
        return  movies.parallelStream().mapToDouble(m->CalculationUtils.calculateVoteLogarithmicScore(m.getVote(),true)).average().orElse(0D);
    }

    protected Double getScore() {
        return CalculationUtils.getLogarithmicScore(count,getMoviesAverageScore(),true,2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BaseWrapper)) return false;

        BaseWrapper<?> that = (BaseWrapper<?>) o;

        return new EqualsBuilder()
            .append(object, that.object)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(object)
            .toHashCode();
    }
}

