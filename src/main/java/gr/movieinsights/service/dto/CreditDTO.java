package gr.movieinsights.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import gr.movieinsights.domain.enumeration.CreditRole;

/**
 * A DTO for the {@link gr.movieinsights.domain.Credit} entity.
 */
public class CreditDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String creditId;

    @NotNull
    private Long personTmdbId;

    @NotNull
    private Long movieTmdbId;

    @NotNull
    private CreditRole role;


    private Long movieId;

    private Long personId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public Long getPersonTmdbId() {
        return personTmdbId;
    }

    public void setPersonTmdbId(Long personTmdbId) {
        this.personTmdbId = personTmdbId;
    }

    public Long getMovieTmdbId() {
        return movieTmdbId;
    }

    public void setMovieTmdbId(Long movieTmdbId) {
        this.movieTmdbId = movieTmdbId;
    }

    public CreditRole getRole() {
        return role;
    }

    public void setRole(CreditRole role) {
        this.role = role;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
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
        if (!(o instanceof CreditDTO)) {
            return false;
        }

        return id != null && id.equals(((CreditDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreditDTO{" +
            "id=" + getId() +
            ", creditId='" + getCreditId() + "'" +
            ", personTmdbId=" + getPersonTmdbId() +
            ", movieTmdbId=" + getMovieTmdbId() +
            ", role='" + getRole() + "'" +
            ", movieId=" + getMovieId() +
            ", personId=" + getPersonId() +
            "}";
    }
}
