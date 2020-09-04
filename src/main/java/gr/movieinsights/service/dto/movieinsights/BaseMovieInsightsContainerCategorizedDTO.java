package gr.movieinsights.service.dto.movieinsights;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class BaseMovieInsightsContainerCategorizedDTO<T> extends BaseMovieInsightsContainerDTO implements IMovieInsightsContainerCategorizedDTO<T> {

    private T entity;

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BaseMovieInsightsContainerCategorizedDTO)) return false;

        BaseMovieInsightsContainerCategorizedDTO<?> that = (BaseMovieInsightsContainerCategorizedDTO<?>) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(entity,that.entity)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(entity)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("entity", entity)
            .append("movieInsights", getMovieInsights())
            .toString();
    }
}
