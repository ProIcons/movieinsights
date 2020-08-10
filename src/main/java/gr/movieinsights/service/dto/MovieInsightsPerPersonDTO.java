package gr.movieinsights.service.dto;

import gr.movieinsights.domain.enumeration.CreditRole;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link gr.movieinsights.domain.MovieInsightsPerPerson} entity.
 */
public class MovieInsightsPerPersonDTO implements Serializable {

    private Long id;

    @NotNull
    private CreditRole as;


    private Long movieInsightsId;

    private Long personId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CreditRole getAs() {
        return as;
    }

    public void setAs(CreditRole as) {
        this.as = as;
    }

    public Long getMovieInsightsId() {
        return movieInsightsId;
    }

    public void setMovieInsightsId(Long movieInsightsId) {
        this.movieInsightsId = movieInsightsId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieInsightsPerPersonDTO)) {
            return false;
        }

        return id != null && id.equals(((MovieInsightsPerPersonDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieInsightsPerPersonDTO{" +
            "id=" + getId() +
            ", as='" + getAs() + "'" +
            ", movieInsightsId=" + getMovieInsightsId() +
            ", personId=" + getPersonId() +
            "}";
    }
}
