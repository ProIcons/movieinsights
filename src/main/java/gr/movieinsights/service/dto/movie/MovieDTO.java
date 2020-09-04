package gr.movieinsights.service.dto.movie;

import gr.movieinsights.service.dto.company.BasicProductionCompanyDTO;
import gr.movieinsights.service.dto.country.BasicProductionCountryDTO;
import gr.movieinsights.service.dto.credit.BasicCreditDTO;
import gr.movieinsights.service.dto.genre.BasicGenreDTO;
import gr.movieinsights.service.dto.vote.VoteDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link gr.movieinsights.domain.Movie} entity.
 */
public class MovieDTO extends BasicMovieDTO {

    private String tagline;

    @Lob
    private String description;

    @NotNull
    private Double popularity;

    private VoteDTO vote;

    private Set<BasicProductionCompanyDTO> companies = new HashSet<>();
    private Set<BasicProductionCountryDTO> countries = new HashSet<>();
    private Set<BasicGenreDTO> genres = new HashSet<>();
    private Set<BasicCreditDTO> credits = new HashSet<>();

    public Set<BasicCreditDTO> getCredits() {
        return credits;
    }

    public void setCredits(Set<BasicCreditDTO> credits) {
        this.credits = credits;
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


    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }


    public Set<BasicProductionCompanyDTO> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<BasicProductionCompanyDTO> productionCompanies) {
        this.companies = productionCompanies;
    }

    public Set<BasicProductionCountryDTO> getCountries() {
        return countries;
    }

    public void setCountries(Set<BasicProductionCountryDTO> productionCountries) {
        this.countries = productionCountries;
    }

    public Set<BasicGenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(Set<BasicGenreDTO> genres) {
        this.genres = genres;
    }

    @Override
    public VoteDTO getVote() {
        return vote;
    }

    public void setVote(VoteDTO vote) {
        this.vote = vote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof MovieDTO)) return false;

        MovieDTO movieDTO = (MovieDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(tagline, movieDTO.tagline)
            .append(description, movieDTO.description)
            .append(popularity, movieDTO.popularity)
            .append(companies, movieDTO.companies)
            .append(countries, movieDTO.countries)
            .append(genres, movieDTO.genres)
            .append(credits, movieDTO.credits)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(tagline)
            .append(description)
            .append(popularity)
            .append(companies)
            .append(countries)
            .append(genres)
            .append(credits)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("name", getName())
            .append("tmdbId", getTmdbId())
            .append("runtime", getRuntime())
            .append("posterPath", getPosterPath())
            .append("backdropPath", getBackdropPath())
            .append("releaseDate", getReleaseDate())
            .append("revenue", getRevenue())
            .append("budget", getBudget())
            .append("imdbId", getImdbId())
            .append("tagline", tagline)
            .append("description", description)
            .append("popularity", popularity)
            .append("vote", vote)
            .append("companies", companies)
            .append("countries", countries)
            .append("genres", genres)
            .append("credits", credits)
            .toString();
    }
}
