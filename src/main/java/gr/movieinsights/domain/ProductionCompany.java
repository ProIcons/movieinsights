package gr.movieinsights.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProductionCompany.
 */
@Entity
@Table(name = "production_company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductionCompany implements Serializable, IdentifiedNamedEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companySequenceGenerator")
    @SequenceGenerator(name = "companySequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "tmdb_id", nullable = false, unique = true)
    private Long tmdbId;

    @Column(name = "logo_path")
    private String logoPath;

    @Column(name = "origin_country")
    private String originCountry;

    @ManyToMany(mappedBy = "companies")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Movie> movies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ProductionCompany name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTmdbId() {
        return tmdbId;
    }

    public ProductionCompany tmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
        return this;
    }

    public void setTmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public ProductionCompany logoPath(String logoPath) {
        this.logoPath = logoPath;
        return this;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public ProductionCompany originCountry(String originCountry) {
        this.originCountry = originCountry;
        return this;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public ProductionCompany movies(Set<Movie> movies) {
        this.movies = movies;
        return this;
    }

    public ProductionCompany addMovies(Movie movie) {
        this.movies.add(movie);
        movie.getCompanies().add(this);
        return this;
    }

    public ProductionCompany removeMovies(Movie movie) {
        this.movies.remove(movie);
        movie.getCompanies().remove(this);
        return this;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductionCompany)) {
            return false;
        }
        return id != null && id.equals(((ProductionCompany) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductionCompany{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", tmdbId=" + getTmdbId() +
            ", logoPath='" + getLogoPath() + "'" +
            ", originCountry='" + getOriginCountry() + "'" +
            "}";
    }
}
