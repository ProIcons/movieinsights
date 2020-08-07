package gr.movieinsights.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import gr.movieinsights.domain.enumeration.BanReason;

/**
 * A DTO for the {@link gr.movieinsights.domain.BannedPersistentEntity} entity.
 */
public class BannedPersistentEntityDTO implements Serializable {
    
    private Long id;

    @NotNull
    private BanReason reason;

    private String reasonText;

    @NotNull
    private LocalDate timestamp;


    private Long movieId;

    private Long personId;

    private Long productionCompanyId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getProductionCompanyId() {
        return productionCompanyId;
    }

    public void setProductionCompanyId(Long productionCompanyId) {
        this.productionCompanyId = productionCompanyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BannedPersistentEntityDTO)) {
            return false;
        }

        return id != null && id.equals(((BannedPersistentEntityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BannedPersistentEntityDTO{" +
            "id=" + getId() +
            ", reason='" + getReason() + "'" +
            ", reasonText='" + getReasonText() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", movieId=" + getMovieId() +
            ", personId=" + getPersonId() +
            ", productionCompanyId=" + getProductionCompanyId() +
            "}";
    }
}
