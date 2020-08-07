package gr.movieinsights.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProductionCompany.
 */
@Entity
@Table(name = "production_company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "productioncompany")
public class ProductionCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
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

    @OneToMany(mappedBy = "productionCompany")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<BannedPersistentEntity> banReasons = new HashSet<>();

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

    public Set<BannedPersistentEntity> getBanReasons() {
        return banReasons;
    }

    public ProductionCompany banReasons(Set<BannedPersistentEntity> bannedPersistentEntities) {
        this.banReasons = bannedPersistentEntities;
        return this;
    }

    public ProductionCompany addBanReasons(BannedPersistentEntity bannedPersistentEntity) {
        this.banReasons.add(bannedPersistentEntity);
        bannedPersistentEntity.setProductionCompany(this);
        return this;
    }

    public ProductionCompany removeBanReasons(BannedPersistentEntity bannedPersistentEntity) {
        this.banReasons.remove(bannedPersistentEntity);
        bannedPersistentEntity.setProductionCompany(null);
        return this;
    }

    public void setBanReasons(Set<BannedPersistentEntity> bannedPersistentEntities) {
        this.banReasons = bannedPersistentEntities;
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
