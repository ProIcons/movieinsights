package gr.movieinsights.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Movie.
 */
@Entity
@Table(name = "movie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "movie")
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tmdb_id", nullable = false, unique = true)
    private Long tmdbId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "tagline")
    private String tagline;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Column(name = "revenue")
    private Long revenue;

    @Column(name = "budget")
    private Long budget;

    @Column(name = "imdb_id")
    private String imdbId;

    @NotNull
    @Column(name = "popularity", nullable = false)
    private Double popularity;

    @Column(name = "runtime")
    private Integer runtime;

    @Column(name = "poster_path")
    private String posterPath;

    @Column(name = "backdrop_path")
    private String backdropPath;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @OneToOne
    @JoinColumn(unique = true)
    private Vote vote;

    @OneToMany(mappedBy = "movie")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Credit> credits = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "movie_companies",
        joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "companies_id", referencedColumnName = "id"))
    private Set<ProductionCompany> companies = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "movie_countries",
        joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "countries_id", referencedColumnName = "id"))
    private Set<ProductionCountry> countries = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "movie_genres",
        joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "genres_id", referencedColumnName = "id"))
    private Set<Genre> genres = new HashSet<>();

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

    public Movie tmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
        return this;
    }

    public void setTmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
    }

    public String getName() {
        return name;
    }

    public Movie name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return tagline;
    }

    public Movie tagline(String tagline) {
        this.tagline = tagline;
        return this;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getDescription() {
        return description;
    }

    public Movie description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRevenue() {
        return revenue;
    }

    public Movie revenue(Long revenue) {
        this.revenue = revenue;
        return this;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    public Long getBudget() {
        return budget;
    }

    public Movie budget(Long budget) {
        this.budget = budget;
        return this;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public String getImdbId() {
        return imdbId;
    }

    public Movie imdbId(String imdbId) {
        this.imdbId = imdbId;
        return this;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public Double getPopularity() {
        return popularity;
    }

    public Movie popularity(Double popularity) {
        this.popularity = popularity;
        return this;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public Movie runtime(Integer runtime) {
        this.runtime = runtime;
        return this;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Movie posterPath(String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Movie backdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
        return this;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Movie releaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Vote getVote() {
        return vote;
    }

    public Movie vote(Vote vote) {
        this.vote = vote;
        return this;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public Set<Credit> getCredits() {
        return credits;
    }

    public Movie credits(Set<Credit> credits) {
        this.credits = credits;
        return this;
    }

    public Movie addCredits(Credit credit) {
        this.credits.add(credit);
        credit.setMovie(this);
        return this;
    }

    public Movie removeCredits(Credit credit) {
        this.credits.remove(credit);
        credit.setMovie(null);
        return this;
    }

    public void setCredits(Set<Credit> credits) {
        this.credits = credits;
    }

    public Set<ProductionCompany> getCompanies() {
        return companies;
    }

    public Movie companies(Set<ProductionCompany> productionCompanies) {
        this.companies = productionCompanies;
        return this;
    }

    public Movie addCompanies(ProductionCompany productionCompany) {
        this.companies.add(productionCompany);
        productionCompany.getMovies().add(this);
        return this;
    }

    public Movie removeCompanies(ProductionCompany productionCompany) {
        this.companies.remove(productionCompany);
        productionCompany.getMovies().remove(this);
        return this;
    }

    public void setCompanies(Set<ProductionCompany> productionCompanies) {
        this.companies = productionCompanies;
    }

    public Set<ProductionCountry> getCountries() {
        return countries;
    }

    public Movie countries(Set<ProductionCountry> productionCountries) {
        this.countries = productionCountries;
        return this;
    }

    public Movie addCountries(ProductionCountry productionCountry) {
        this.countries.add(productionCountry);
        productionCountry.getMovies().add(this);
        return this;
    }

    public Movie removeCountries(ProductionCountry productionCountry) {
        this.countries.remove(productionCountry);
        productionCountry.getMovies().remove(this);
        return this;
    }

    public void setCountries(Set<ProductionCountry> productionCountries) {
        this.countries = productionCountries;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public Movie genres(Set<Genre> genres) {
        this.genres = genres;
        return this;
    }

    public Movie addGenres(Genre genre) {
        this.genres.add(genre);
        genre.getMovies().add(this);
        return this;
    }

    public Movie removeGenres(Genre genre) {
        this.genres.remove(genre);
        genre.getMovies().remove(this);
        return this;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie)) {
            return false;
        }
        return id != null && id.equals(((Movie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Movie{" +
            "id=" + getId() +
            ", tmdbId=" + getTmdbId() +
            ", name='" + getName() + "'" +
            ", tagline='" + getTagline() + "'" +
            ", description='" + getDescription() + "'" +
            ", revenue=" + getRevenue() +
            ", budget=" + getBudget() +
            ", imdbId='" + getImdbId() + "'" +
            ", popularity=" + getPopularity() +
            ", runtime=" + getRuntime() +
            ", posterPath='" + getPosterPath() + "'" +
            ", backdropPath='" + getBackdropPath() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            "}";
    }
}
