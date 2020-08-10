package gr.movieinsights.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link gr.movieinsights.domain.ProductionCompany} entity.
 */
public class ProductionCompanyDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Long tmdbId;

    private String logoPath;

    private String originCountry;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductionCompanyDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductionCompanyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductionCompanyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", tmdbId=" + getTmdbId() +
            ", logoPath='" + getLogoPath() + "'" +
            ", originCountry='" + getOriginCountry() + "'" +
            "}";
    }
}
