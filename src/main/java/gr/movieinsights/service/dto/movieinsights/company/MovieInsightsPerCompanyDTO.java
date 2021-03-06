package gr.movieinsights.service.dto.movieinsights.company;

import gr.movieinsights.service.dto.movieinsights.IMovieInsightsContainerExtendedDTO;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * A DTO for the {@link gr.movieinsights.domain.MovieInsightsPerCompany} entity.
 */
public class MovieInsightsPerCompanyDTO extends MovieInsightsPerCompanyBasicDTO implements IMovieInsightsContainerExtendedDTO {
    @Override
    public List<MovieInsightsPerYearDTO> getMovieInsightsPerYears() {
        return movieInsightsPerYears;
    }

    @Override
    public void setMovieInsightsPerYears(List<MovieInsightsPerYearDTO> movieInsightsPerYears) {
        this.movieInsightsPerYears = movieInsightsPerYears;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof MovieInsightsPerCompanyDTO)) return false;

        MovieInsightsPerCompanyDTO that = (MovieInsightsPerCompanyDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(movieInsightsPerYears,that.movieInsightsPerYears)
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
            .append("entity", getEntity())
            .append("movieInsights", getMovieInsights())
            .append("movieInsightsPerYears", getMovieInsightsPerYears())
            .toString();
    }


}
