package gr.movieinsights.service.dto.movieinsights.person;

import gr.movieinsights.domain.enumeration.CreditRole;
import gr.movieinsights.service.dto.movieinsights.BaseMovieInsightsContainerCategorizedDTO;
import gr.movieinsights.service.dto.person.BasicPersonDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

public class MovieInsightsPerPersonBasicDTO extends BaseMovieInsightsContainerCategorizedDTO<BasicPersonDTO> {
    @NotNull
    private CreditRole as;
    public CreditRole getAs() {
        return as;
    }

    public void setAs(CreditRole as) {
        this.as = as;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof MovieInsightsPerPersonBasicDTO)) return false;

        MovieInsightsPerPersonBasicDTO that = (MovieInsightsPerPersonBasicDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(as, that.as)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(as)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("entity", getEntity())
            .append("as", as)
            .append("movieInsights", getMovieInsights())
            .toString();
    }
}
