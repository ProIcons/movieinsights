package gr.movieinsights.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gr.movieinsights.domain.enumeration.CreditRole;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Credit.
 */
@Entity
@Table(name = "credit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Credit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "creditSequenceGenerator")
    @SequenceGenerator(name = "creditSequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "credit_id", nullable = false)
    private String creditId;

    @NotNull
    @Column(name = "person_tmdb_id", nullable = false)
    private Long personTmdbId;

    @NotNull
    @Column(name = "movie_tmdb_id", nullable = false)
    private Long movieTmdbId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private CreditRole role;

    @ManyToOne
    @JsonIgnoreProperties(value = "credits", allowSetters = true)
    @Field(type = FieldType.Nested)
    private Movie movie;

    @ManyToOne
    @JsonIgnoreProperties(value = "credits", allowSetters = true)
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreditId() {
        return creditId;
    }

    public Credit creditId(String creditId) {
        this.creditId = creditId;
        return this;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public Long getPersonTmdbId() {
        return personTmdbId;
    }

    public Credit personTmdbId(Long personTmdbId) {
        this.personTmdbId = personTmdbId;
        return this;
    }

    public void setPersonTmdbId(Long personTmdbId) {
        this.personTmdbId = personTmdbId;
    }

    public Long getMovieTmdbId() {
        return movieTmdbId;
    }

    public Credit movieTmdbId(Long movieTmdbId) {
        this.movieTmdbId = movieTmdbId;
        return this;
    }

    public void setMovieTmdbId(Long movieTmdbId) {
        this.movieTmdbId = movieTmdbId;
    }

    public CreditRole getRole() {
        return role;
    }

    public Credit role(CreditRole role) {
        this.role = role;
        return this;
    }

    public void setRole(CreditRole role) {
        this.role = role;
    }

    public Movie getMovie() {
        return movie;
    }

    public Credit movie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Person getPerson() {
        return person;
    }

    public Credit person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Credit)) {
            return false;
        }
        return id != null && id.equals(((Credit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Credit{" +
            "id=" + getId() +
            ", creditId='" + getCreditId() + "'" +
            ", personTmdbId=" + getPersonTmdbId() +
            ", movieTmdbId=" + getMovieTmdbId() +
            ", role='" + getRole() + "'" +
            "}";
    }
}
