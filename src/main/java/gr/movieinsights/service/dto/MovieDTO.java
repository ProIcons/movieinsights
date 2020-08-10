package gr.movieinsights.service.dto;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link gr.movieinsights.domain.Movie} entity.
 */
public class MovieDTO implements Serializable {

    private Long id;

    @NotNull
    private Long tmdbId;

    @NotNull
    private String name;

    private String tagline;

    @Lob
    private String description;

    private Long revenue;

    private Long budget;

    private String imdbId;

    @NotNull
    private Double popularity;

    private Integer runtime;

    private String posterPath;

    private String backdropPath;

    private LocalDate releaseDate;


    private Long voteId;
    private Set<ProductionCompanyDTO> companies = new HashSet<>();
    private Set<ProductionCountryDTO> countries = new HashSet<>();
    private Set<GenreDTO> genres = new HashSet<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRevenue() {
        return revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    public Set<ProductionCompanyDTO> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<ProductionCompanyDTO> productionCompanies) {
        this.companies = productionCompanies;
    }

    public Set<ProductionCountryDTO> getCountries() {
        return countries;
    }

    public void setCountries(Set<ProductionCountryDTO> productionCountries) {
        this.countries = productionCountries;
    }

    public Set<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreDTO> genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieDTO)) {
            return false;
        }

        return id != null && id.equals(((MovieDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieDTO{" +
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
            ", voteId=" + getVoteId() +
            ", companies='" + getCompanies() + "'" +
            ", countries='" + getCountries() + "'" +
            ", genres='" + getGenres() + "'" +
            "}";
    }
}
