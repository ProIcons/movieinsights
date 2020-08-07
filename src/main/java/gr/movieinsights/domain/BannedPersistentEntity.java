package gr.movieinsights.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import gr.movieinsights.domain.enumeration.BanReason;

/**
 * A BannedPersistentEntity.
 */
@Entity
@Table(name = "banned_persistent_entity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "bannedpersistententity")
public class BannedPersistentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false)
    private BanReason reason;

    @Column(name = "reason_text")
    private String reasonText;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private LocalDate timestamp;

    @OneToOne(mappedBy = "bannedPersistentEntity")
    @JsonIgnore
    private BannedEntity bannedEntity;

    @ManyToOne
    @JsonIgnoreProperties(value = "banReasons", allowSetters = true)
    private Movie movie;

    @ManyToOne
    @JsonIgnoreProperties(value = "banReasons", allowSetters = true)
    private Person person;

    @ManyToOne
    @JsonIgnoreProperties(value = "banReasons", allowSetters = true)
    private ProductionCompany productionCompany;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BanReason getReason() {
        return reason;
    }

    public BannedPersistentEntity reason(BanReason reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(BanReason reason) {
        this.reason = reason;
    }

    public String getReasonText() {
        return reasonText;
    }

    public BannedPersistentEntity reasonText(String reasonText) {
        this.reasonText = reasonText;
        return this;
    }

    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public BannedPersistentEntity timestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public BannedEntity getBannedEntity() {
        return bannedEntity;
    }

    public BannedPersistentEntity bannedEntity(BannedEntity bannedEntity) {
        this.bannedEntity = bannedEntity;
        return this;
    }

    public void setBannedEntity(BannedEntity bannedEntity) {
        this.bannedEntity = bannedEntity;
    }

    public Movie getMovie() {
        return movie;
    }

    public BannedPersistentEntity movie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Person getPerson() {
        return person;
    }

    public BannedPersistentEntity person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ProductionCompany getProductionCompany() {
        return productionCompany;
    }

    public BannedPersistentEntity productionCompany(ProductionCompany productionCompany) {
        this.productionCompany = productionCompany;
        return this;
    }

    public void setProductionCompany(ProductionCompany productionCompany) {
        this.productionCompany = productionCompany;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BannedPersistentEntity)) {
            return false;
        }
        return id != null && id.equals(((BannedPersistentEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BannedPersistentEntity{" +
            "id=" + getId() +
            ", reason='" + getReason() + "'" +
            ", reasonText='" + getReasonText() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
