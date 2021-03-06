package gr.movieinsights.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gr.movieinsights.models.MovieInsightsContainerWithYearsWithEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A MovieInsightsPerGenre.
 */
@Entity
@Table(name = "movie_insights_per_genre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MovieInsightsPerGenre implements Serializable, MovieInsightsContainerWithYearsWithEntity<Genre> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movieInsightsPerGenreSequenceGenerator")
    @SequenceGenerator(name = "movieInsightsPerGenreSequenceGenerator")
    private Long id;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private MovieInsights movieInsights;

    @OneToMany(mappedBy = "movieInsightsPerGenre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MovieInsightsPerYear> movieInsightsPerYears = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "movieInsightsPerGenres", allowSetters = true)
    private Genre genre;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MovieInsights getMovieInsights() {
        return movieInsights;
    }

    public MovieInsightsPerGenre movieInsights(MovieInsights movieInsights) {
        this.movieInsights = movieInsights;
        return this;
    }

    public void setMovieInsights(MovieInsights movieInsights) {
        this.movieInsights = movieInsights;
    }

    public Set<MovieInsightsPerYear> getMovieInsightsPerYears() {
        return movieInsightsPerYears;
    }

    public MovieInsightsPerGenre movieInsightsPerYears(Set<MovieInsightsPerYear> movieInsightsPerYears) {
        this.movieInsightsPerYears = movieInsightsPerYears;
        return this;
    }

    public MovieInsightsPerGenre addMovieInsightsPerYear(MovieInsightsPerYear movieInsightsPerYear) {
        this.movieInsightsPerYears.add(movieInsightsPerYear);
        movieInsightsPerYear.setMovieInsightsPerGenre(this);
        return this;
    }

    public MovieInsightsPerGenre removeMovieInsightsPerYear(MovieInsightsPerYear movieInsightsPerYear) {
        this.movieInsightsPerYears.remove(movieInsightsPerYear);
        movieInsightsPerYear.setMovieInsightsPerGenre(null);
        return this;
    }

    public void setMovieInsightsPerYears(Set<MovieInsightsPerYear> movieInsightsPerYears) {
        this.movieInsightsPerYears = movieInsightsPerYears;
    }

    public Genre getGenre() {
        return genre;
    }

    public MovieInsightsPerGenre genre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieInsightsPerGenre)) {
            return false;
        }
        return id != null && id.equals(((MovieInsightsPerGenre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieInsightsPerGenre{" +
            "id=" + getId() +
            "}";
    }

    @Override
    public Genre getEntity() {
        return genre;
    }

    @Override
    public void setEntity(Genre entity) {
        genre = entity;
    }
}
