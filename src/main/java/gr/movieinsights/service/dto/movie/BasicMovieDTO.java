package gr.movieinsights.service.dto.movie;

import gr.movieinsights.service.dto.BaseNamedDTO;
import gr.movieinsights.service.dto.vote.BasicVoteDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class BasicMovieDTO extends BaseNamedDTO {
    @NotNull
    private Long tmdbId;

    private Long revenue;

    private Long budget;

    private String imdbId;

    private Integer runtime;

    private String posterPath;

    private String backdropPath;

    private LocalDate releaseDate;

    private BasicVoteDTO vote;

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

    public Long getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
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

    public BasicVoteDTO getVote() {
        return vote;
    }

    public void setVote(BasicVoteDTO vote) {
        this.vote = vote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BasicMovieDTO)) return false;

        BasicMovieDTO that = (BasicMovieDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(tmdbId, that.tmdbId)
            .append(revenue, that.revenue)
            .append(budget, that.budget)
            .append(imdbId, that.imdbId)
            .append(runtime, that.runtime)
            .append(posterPath, that.posterPath)
            .append(backdropPath, that.backdropPath)
            .append(releaseDate, that.releaseDate)
            .append(vote, that.vote)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(tmdbId)
            .append(revenue)
            .append(budget)
            .append(imdbId)
            .append(runtime)
            .append(posterPath)
            .append(backdropPath)
            .append(releaseDate)
            .append(vote)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("name", getName())
            .append("id", getId())
            .append("tmdbId", tmdbId)
            .append("revenue", revenue)
            .append("budget", budget)
            .append("imdbId", imdbId)
            .append("runtime", runtime)
            .append("posterPath", posterPath)
            .append("backdropPath", backdropPath)
            .append("releaseDate", releaseDate)
            .append("vote", vote)
            .toString();
    }
}
