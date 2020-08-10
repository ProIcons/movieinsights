package gr.movieinsights.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link gr.movieinsights.domain.Genre} entity.
 */
public class GenreDTO implements Serializable {

    private Long id;

    @NotNull
    private Long tmdbId;

    @NotNull
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GenreDTO)) {
            return false;
        }

        return id != null && id.equals(((GenreDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GenreDTO{" +
            "id=" + getId() +
            ", tmdbId=" + getTmdbId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
