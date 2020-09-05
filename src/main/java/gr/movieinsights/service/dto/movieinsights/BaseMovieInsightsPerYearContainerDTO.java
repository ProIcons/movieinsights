package gr.movieinsights.service.dto.movieinsights;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class BaseMovieInsightsPerYearContainerDTO extends BaseMovieInsightsContainerDTO {
    private List<Integer> years;

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BaseMovieInsightsPerYearContainerDTO)) return false;

        BaseMovieInsightsPerYearContainerDTO that = (BaseMovieInsightsPerYearContainerDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(years, that.years)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(years)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("years", years)
            .append("movieInsights", getMovieInsights())
            .append("id", getId())
            .toString();
    }
}
