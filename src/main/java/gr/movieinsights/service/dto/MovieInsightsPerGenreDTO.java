package gr.movieinsights.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link gr.movieinsights.domain.MovieInsightsPerGenre} entity.
 */
public class MovieInsightsPerGenreDTO implements Serializable {

    private Long id;


    private Long movieInsightsId;

    private Long genreId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieInsightsId() {
        return movieInsightsId;
    }

    public void setMovieInsightsId(Long movieInsightsId) {
        this.movieInsightsId = movieInsightsId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieInsightsPerGenreDTO)) {
            return false;
        }

        return id != null && id.equals(((MovieInsightsPerGenreDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieInsightsPerGenreDTO{" +
            "id=" + getId() +
            ", movieInsightsId=" + getMovieInsightsId() +
            ", genreId=" + getGenreId() +
            "}";
    }
}
