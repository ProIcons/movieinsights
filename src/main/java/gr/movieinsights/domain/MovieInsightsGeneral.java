package gr.movieinsights.domain;

import gr.movieinsights.models.MovieInsightsContainerWithYears;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A MovieInsightsGeneral.
 */
@Entity
@Table(name = "movie_insights_general")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MovieInsightsGeneral implements Serializable, MovieInsightsContainerWithYears {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movieInsightsGeneralSequenceGenerator")
    @SequenceGenerator(name = "movieInsightsGeneralSequenceGenerator")
    private Long id;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private MovieInsights movieInsights;

    @OneToMany(mappedBy = "movieInsightsGeneral")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MovieInsightsPerYear> movieInsightsPerYears = new HashSet<>();

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

    public MovieInsightsGeneral movieInsights(MovieInsights movieInsights) {
        this.movieInsights = movieInsights;
        return this;
    }

    public void setMovieInsights(MovieInsights movieInsights) {
        this.movieInsights = movieInsights;
    }


    public Set<MovieInsightsPerYear> getMovieInsightsPerYears() {
        return movieInsightsPerYears;
    }

    public MovieInsightsGeneral movieInsightsPerYears(Set<MovieInsightsPerYear> movieInsightsPerYears) {
        this.movieInsightsPerYears = movieInsightsPerYears;
        return this;
    }

    public MovieInsightsGeneral addMovieInsightsPerYear(MovieInsightsPerYear movieInsightsPerYear) {
        this.movieInsightsPerYears.add(movieInsightsPerYear);
        movieInsightsPerYear.setMovieInsightsGeneral(this);
        return this;
    }

    public MovieInsightsGeneral removeMovieInsightsPerYear(MovieInsightsPerYear movieInsightsPerYear) {
        this.movieInsightsPerYears.remove(movieInsightsPerYear);
        movieInsightsPerYear.setMovieInsightsGeneral(null);
        return this;
    }

    public void setMovieInsightsPerYears(Set<MovieInsightsPerYear> movieInsightsPerYears) {
        this.movieInsightsPerYears = movieInsightsPerYears;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieInsightsGeneral)) {
            return false;
        }
        return id != null && id.equals(((MovieInsightsGeneral) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieInsightsGeneral{" +
            "id=" + getId() +
            "}";
    }
}
