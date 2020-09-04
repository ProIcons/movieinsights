package gr.movieinsights.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Person implements Serializable, IdentifiedNamedEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PersonSequenceGenerator")
    @SequenceGenerator(name = "PersonSequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tmdb_id", nullable = false, unique = true)
    private Long tmdbId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "imdb_id")
    private String imdbId;

    @Column(name = "popularity")
    private Double popularity;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "biography")
    private String biography;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @Column(name = "profile_path")
    private String profilePath;

    @OneToMany(mappedBy = "person")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Credit> credits = new HashSet<>();

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

    public Person tmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
        return this;
    }

    public void setTmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
    }

    public String getName() {
        return name;
    }

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImdbId() {
        return imdbId;
    }

    public Person imdbId(String imdbId) {
        this.imdbId = imdbId;
        return this;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public Double getPopularity() {
        return popularity;
    }

    public Person popularity(Double popularity) {
        this.popularity = popularity;
        return this;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getBiography() {
        return biography;
    }

    public Person biography(String biography) {
        this.biography = biography;
        return this;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public Person birthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
        return this;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public Person profilePath(String profilePath) {
        this.profilePath = profilePath;
        return this;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public Set<Credit> getCredits() {
        return credits;
    }

    public Person credits(Set<Credit> credits) {
        this.credits = credits;
        return this;
    }

    public Person addCredits(Credit credit) {
        this.credits.add(credit);
        credit.setPerson(this);
        return this;
    }

    public Person removeCredits(Credit credit) {
        this.credits.remove(credit);
        credit.setPerson(null);
        return this;
    }

    public void setCredits(Set<Credit> credits) {
        this.credits = credits;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", tmdbId=" + getTmdbId() +
            ", name='" + getName() + "'" +
            ", imdbId='" + getImdbId() + "'" +
            ", popularity=" + getPopularity() +
            ", biography='" + getBiography() + "'" +
            ", birthDay='" + getBirthDay() + "'" +
            ", profilePath='" + getProfilePath() + "'" +
            "}";
    }
}
