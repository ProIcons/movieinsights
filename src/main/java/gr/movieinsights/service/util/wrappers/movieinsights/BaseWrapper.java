package gr.movieinsights.service.util.wrappers.movieinsights;

import gr.movieinsights.domain.Movie;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

public abstract class BaseWrapper<T> {
    public T object;
    public Set<Movie> movies;
    public int count;

    public BaseWrapper(T object) {
        this.object = object;
        this.movies = new HashSet<>();
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

