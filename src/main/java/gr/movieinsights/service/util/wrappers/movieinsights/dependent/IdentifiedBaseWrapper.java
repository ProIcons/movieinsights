package gr.movieinsights.service.util.wrappers.movieinsights.dependent;

import gr.movieinsights.domain.IdentifiedEntity;
import gr.movieinsights.service.enumeration.MovieInsightsCategory;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class IdentifiedBaseWrapper<T extends IdentifiedEntity> extends BaseWrapper<T> {
    public IdentifiedBaseWrapper(T object, MovieInsightsCategory category) {
        super(object,category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof IdentifiedBaseWrapper)) return false;

        IdentifiedBaseWrapper<? extends IdentifiedEntity> that = (IdentifiedBaseWrapper<? extends IdentifiedEntity>) o;

        return new EqualsBuilder()
            .append(object.getId(), that.object.getId())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(object.getId())
            .toHashCode();
    }
}
