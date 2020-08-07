package gr.movieinsights.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import gr.movieinsights.domain.enumeration.EntityType;

/**
 * A BannedEntity.
 */
@Entity
@Table(name = "banned_entity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "bannedentity")
public class BannedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tmdb_id", nullable = false)
    private Long tmdbId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private EntityType type;

    @OneToOne
    @JoinColumn(unique = true)
    private BannedPersistentEntity bannedPersistentEntity;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTmdbId() {
        return tmdbId;
    }

    public BannedEntity tmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
        return this;
    }

    public void setTmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
    }

    public EntityType getType() {
        return type;
    }

    public BannedEntity type(EntityType type) {
        this.type = type;
        return this;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public BannedPersistentEntity getBannedPersistentEntity() {
        return bannedPersistentEntity;
    }

    public BannedEntity bannedPersistentEntity(BannedPersistentEntity bannedPersistentEntity) {
        this.bannedPersistentEntity = bannedPersistentEntity;
        return this;
    }

    public void setBannedPersistentEntity(BannedPersistentEntity bannedPersistentEntity) {
        this.bannedPersistentEntity = bannedPersistentEntity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BannedEntity)) {
            return false;
        }
        return id != null && id.equals(((BannedEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BannedEntity{" +
            "id=" + getId() +
            ", tmdbId=" + getTmdbId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
