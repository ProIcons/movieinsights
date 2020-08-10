package gr.movieinsights.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import gr.movieinsights.domain.enumeration.TmdbEntityType;
import gr.movieinsights.domain.enumeration.BanReason;

/**
 * A DTO for the {@link gr.movieinsights.domain.BannedEntity} entity.
 */
public class BannedEntityDTO implements Serializable {
    
    private Long id;

    private Long tmdbId;

    private String imdbId;

    @NotNull
    private TmdbEntityType type;

    @NotNull
    private BanReason reason;

    private String reasonText;

    @NotNull
    private LocalDate timestamp;

    
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

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public TmdbEntityType getType() {
        return type;
    }

    public void setType(TmdbEntityType type) {
        this.type = type;
    }

    public BanReason getReason() {
        return reason;
    }

    public void setReason(BanReason reason) {
        this.reason = reason;
    }

    public String getReasonText() {
        return reasonText;
    }

    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
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
            ", imdbId='" + getImdbId() + "'" +
            ", type='" + getType() + "'" +
            ", reason='" + getReason() + "'" +
            ", reasonText='" + getReasonText() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
