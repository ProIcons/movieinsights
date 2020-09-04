package gr.movieinsights.service.dto.movieinsights.general;

import gr.movieinsights.service.dto.movieinsights.IMovieInsightsContainerExtendedDTO;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class MovieInsightsGeneralDTO extends MovieInsightsGeneralBasicDTO implements IMovieInsightsContainerExtendedDTO {
    @Override
    public List<MovieInsightsPerYearDTO> getMovieInsightsPerYears() {
        return this.movieInsightsPerYears;
    }

    @Override
    public void setMovieInsightsPerYears(List<MovieInsightsPerYearDTO> movieInsightsPerYears) {
        this.movieInsightsPerYears = movieInsightsPerYears;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof MovieInsightsGeneralDTO)) return false;

        MovieInsightsGeneralDTO that = (MovieInsightsGeneralDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(movieInsightsPerYears, that.movieInsightsPerYears)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(movieInsightsPerYears)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("movieInsights", getMovieInsights())
            .append("movieInsightsPerYears", getMovieInsightsPerYears())
            .toString();
    }
}
