package gr.movieinsights.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import gr.movieinsights.domain.enumeration.EntityType;

/**
 * A DTO for the {@link gr.movieinsights.domain.BannedEntity} entity.
 */
public class BannedEntityDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Long tmdbId;

    @NotNull
    private EntityType type;


    private Long bannedPersistentEntityId;
    
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

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public Long getBannedPersistentEntityId() {
        return bannedPersistentEntityId;
    }

    public void setBannedPersistentEntityId(Long bannedPersistentEntityId) {
        this.bannedPersistentEntityId = bannedPersistentEntityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BannedEntityDTO)) {
            return false;
        }

        return id != null && id.equals(((BannedEntityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BannedEntityDTO{" +
            "id=" + getId() +
            ", tmdbId=" + getTmdbId() +
            ", type='" + getType() + "'" +
            ", bannedPersistentEntityId=" + getBannedPersistentEntityId() +
            "}";
    }
}
