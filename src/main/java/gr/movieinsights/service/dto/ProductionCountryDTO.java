package gr.movieinsights.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link gr.movieinsights.domain.ProductionCountry} entity.
 */
public class ProductionCountryDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String iso31661;

    
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

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductionCountryDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductionCountryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductionCountryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", iso31661='" + getIso31661() + "'" +
            "}";
    }
}
