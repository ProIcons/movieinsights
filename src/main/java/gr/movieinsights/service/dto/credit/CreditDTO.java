package gr.movieinsights.service.dto.credit;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link gr.movieinsights.domain.Credit} entity.
 */
public class CreditDTO extends BasicCreditDTO {
    @NotNull
    private String creditId;

    private Long movieTmdbId;
    private Long personTmdbId;


    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public Long getMovieTmdbId() {
        return movieTmdbId;
    }

    public void setMovieTmdbId(Long movieTmdbId) {
        this.movieTmdbId = movieTmdbId;
    }

    public Long getPersonTmdbId() {
        return personTmdbId;
    }

    public void setPersonTmdbId(Long personTmdbId) {
        this.personTmdbId = personTmdbId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CreditDTO)) return false;

        CreditDTO creditDTO = (CreditDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(creditId, creditDTO.creditId)
            .append(movieTmdbId, creditDTO.movieTmdbId)
            .append(personTmdbId, creditDTO.personTmdbId)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(creditId)
            .append(movieTmdbId)
            .append(personTmdbId)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("role", getRole())
            .append("creditId", creditId)
            .append("movieTmdbId", movieTmdbId)
            .append("personTmdbId", personTmdbId)
            .toString();
    }
}
