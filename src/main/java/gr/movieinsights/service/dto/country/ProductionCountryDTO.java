package gr.movieinsights.service.dto.country;

import gr.movieinsights.service.dto.movie.BasicMovieDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * A DTO for the {@link gr.movieinsights.domain.ProductionCountry} entity.
 */
public class ProductionCountryDTO extends BasicProductionCountryDTO {
    private List<BasicMovieDTO> movies;

    public List<BasicMovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(List<BasicMovieDTO> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ProductionCountryDTO)) return false;

        ProductionCountryDTO that = (ProductionCountryDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(movies,that.movies)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(movies)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("name", getName())
            .append("iso31661", getIso31661())
            .append("movies", movies)
            .toString();
    }
}
