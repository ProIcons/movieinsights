package gr.movieinsights.service.dto.movieinsights;

import gr.movieinsights.service.dto.BaseDTO;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public abstract class BaseMovieInsightsContainerDTO extends BaseDTO implements IMovieInsightsContainerDTO {
    private MovieInsightsDTO movieInsights;

    protected List<MovieInsightsPerYearDTO> movieInsightsPerYears;

    @Override
    public MovieInsightsDTO getMovieInsights() {
        return movieInsights;
    }

    @Override
    public void setMovieInsights(MovieInsightsDTO movieInsightsDTO) {
        this.movieInsights = movieInsightsDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BaseMovieInsightsContainerDTO)) return false;

        BaseMovieInsightsContainerDTO that = (BaseMovieInsightsContainerDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(movieInsights, that.movieInsights)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(movieInsights)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("movieInsights", getMovieInsights())
            .toString();
    }
}
