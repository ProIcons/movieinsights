package gr.movieinsights.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link gr.movieinsights.domain.MovieInsightsPerCompany} entity.
 */
public class MovieInsightsPerCompanyDTO implements Serializable {
    
    private Long id;


    private Long movieInsightsId;

    private Long companyId;
    
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long productionCompanyId) {
        this.companyId = productionCompanyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieInsightsPerCompanyDTO)) {
            return false;
        }

        return id != null && id.equals(((MovieInsightsPerCompanyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieInsightsPerCompanyDTO{" +
            "id=" + getId() +
            ", movieInsightsId=" + getMovieInsightsId() +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
