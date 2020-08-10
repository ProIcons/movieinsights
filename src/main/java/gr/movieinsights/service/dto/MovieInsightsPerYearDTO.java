package gr.movieinsights.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link gr.movieinsights.domain.MovieInsightsPerYear} entity.
 */
public class MovieInsightsPerYearDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer year;


    private Long movieInsightsId;

    private Long movieInsightsPerCountryId;

    private Long movieInsightsPerCompanyId;

    private Long movieInsightsPerPersonId;

    private Long movieInsightsPerGenreId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getMovieInsightsId() {
        return movieInsightsId;
    }

    public void setMovieInsightsId(Long movieInsightsId) {
        this.movieInsightsId = movieInsightsId;
    }

    public Long getMovieInsightsPerCountryId() {
        return movieInsightsPerCountryId;
    }

    public void setMovieInsightsPerCountryId(Long movieInsightsPerCountryId) {
        this.movieInsightsPerCountryId = movieInsightsPerCountryId;
    }

    public Long getMovieInsightsPerCompanyId() {
        return movieInsightsPerCompanyId;
    }

    public void setMovieInsightsPerCompanyId(Long movieInsightsPerCompanyId) {
        this.movieInsightsPerCompanyId = movieInsightsPerCompanyId;
    }

    public Long getMovieInsightsPerPersonId() {
        return movieInsightsPerPersonId;
    }

    public void setMovieInsightsPerPersonId(Long movieInsightsPerPersonId) {
        this.movieInsightsPerPersonId = movieInsightsPerPersonId;
    }

    public Long getMovieInsightsPerGenreId() {
        return movieInsightsPerGenreId;
    }

    public void setMovieInsightsPerGenreId(Long movieInsightsPerGenreId) {
        this.movieInsightsPerGenreId = movieInsightsPerGenreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieInsightsPerYearDTO)) {
            return false;
        }

        return id != null && id.equals(((MovieInsightsPerYearDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieInsightsPerYearDTO{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", movieInsightsId=" + getMovieInsightsId() +
            ", movieInsightsPerCountryId=" + getMovieInsightsPerCountryId() +
            ", movieInsightsPerCompanyId=" + getMovieInsightsPerCompanyId() +
            ", movieInsightsPerPersonId=" + getMovieInsightsPerPersonId() +
            ", movieInsightsPerGenreId=" + getMovieInsightsPerGenreId() +
            "}";
    }
}
