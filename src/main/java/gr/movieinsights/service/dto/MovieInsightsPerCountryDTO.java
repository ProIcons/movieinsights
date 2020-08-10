package gr.movieinsights.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link gr.movieinsights.domain.MovieInsightsPerCountry} entity.
 */
public class MovieInsightsPerCountryDTO implements Serializable {

    private Long id;


    private Long movieInsightsId;

    private Long countryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieInsightsId() {
        return movieInsightsId;
    }

    public void setMovieInsightsId(Long movieInsightsId) {
        this.movieInsightsId = movieInsightsId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long productionCountryId) {
        this.countryId = productionCountryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieInsightsPerCountryDTO)) {
            return false;
        }

        return id != null && id.equals(((MovieInsightsPerCountryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieInsightsPerCountryDTO{" +
            "id=" + getId() +
            ", movieInsightsId=" + getMovieInsightsId() +
            ", countryId=" + getCountryId() +
            "}";
    }
}
