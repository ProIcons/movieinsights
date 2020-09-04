package gr.movieinsights.service.dto.genre;

import gr.movieinsights.service.dto.movie.BasicMovieDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * A DTO for the {@link gr.movieinsights.domain.Genre} entity.
 */
public class GenreDTO extends BasicGenreDTO {
    @NotNull
    private Long tmdbId;

    private List<BasicMovieDTO> movies;

    public Long getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
    }

    public List<BasicMovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(List<BasicMovieDTO> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof GenreDTO)) return false;

        GenreDTO genreDTO = (GenreDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(tmdbId, genreDTO.tmdbId)
            .append(movies,genreDTO.movies)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(tmdbId)
            .append(movies)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("name", getName())
            .append("tmdbId", tmdbId)
            .append("movies",movies)
            .toString();
    }
}
