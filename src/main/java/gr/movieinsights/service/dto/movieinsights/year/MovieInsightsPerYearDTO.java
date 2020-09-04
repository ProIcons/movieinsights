package gr.movieinsights.service.dto.movieinsights.year;

import gr.movieinsights.service.dto.movieinsights.BaseMovieInsightsContainerDTO;
import gr.movieinsights.service.dto.movieinsights.IMovieInsightsContainerCategorizedDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class MovieInsightsPerYearDTO extends BaseMovieInsightsContainerDTO implements IMovieInsightsContainerCategorizedDTO<Integer> {

    private Integer entity;

    @Override
    public Integer getEntity() {
        return entity;
    }

    @Override
    public void setEntity(Integer entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof MovieInsightsPerYearDTO)) return false;

        MovieInsightsPerYearDTO that = (MovieInsightsPerYearDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(entity, that.entity)
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
            .append("movieInsights", getMovieInsights())
            .append("entity", entity)
            .toString();
    }
}
