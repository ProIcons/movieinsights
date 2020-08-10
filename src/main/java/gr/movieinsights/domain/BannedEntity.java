package gr.movieinsights.domain;

import gr.movieinsights.domain.enumeration.BanReason;
import gr.movieinsights.domain.enumeration.TmdbEntityType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

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

    @Column(name = "tmdb_id")
    private Long tmdbId;

    @Column(name = "imdb_id")
    private String imdbId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TmdbEntityType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false,columnDefinition = "varchar(32) default 'UNDEFINED'")
    private BanReason reason;

    @Column(name = "reason_text")
    private String reasonText;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private LocalDate timestamp;

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

    public String getImdbId() {
        return imdbId;
    }

    public BannedEntity imdbId(String imdbId) {
        this.imdbId = imdbId;
        return this;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public TmdbEntityType getType() {
        return type;
    }

    public BannedEntity type(TmdbEntityType type) {
        this.type = type;
        return this;
    }

    public void setType(TmdbEntityType type) {
        this.type = type;
    }

    public BanReason getReason() {
        return reason;
    }

    public BannedEntity reason(BanReason reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(BanReason reason) {
        this.reason = reason;
    }

    public String getReasonText() {
        return reasonText;
    }

    public BannedEntity reasonText(String reasonText) {
        this.reasonText = reasonText;
        return this;
    }

    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public BannedEntity timestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
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
            ", imdbId='" + getImdbId() + "'" +
            ", type='" + getType() + "'" +
            ", reason='" + getReason() + "'" +
            ", reasonText='" + getReasonText() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
