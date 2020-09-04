package gr.movieinsights.service.dto.company;

import gr.movieinsights.service.dto.movie.BasicMovieDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * A DTO for the {@link gr.movieinsights.domain.ProductionCompany} entity.
 */
public class ProductionCompanyDTO extends BasicProductionCompanyDTO {



    private String originCountry;

    private List<BasicMovieDTO> movies;

    public List<BasicMovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(List<BasicMovieDTO> movies) {
        this.movies = movies;
    }



    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ProductionCompanyDTO)) return false;

        ProductionCompanyDTO that = (ProductionCompanyDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(originCountry, that.originCountry)
            .append(movies,that.movies)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(originCountry)
            .append(movies)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("name", getName())
            .append("tmdbId", getTmdbId())
            .append("originCountry", originCountry)
            .append("movies",movies)
            .toString();
    }
}
