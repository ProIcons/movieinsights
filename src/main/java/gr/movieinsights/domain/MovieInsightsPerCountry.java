package gr.movieinsights.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A MovieInsightsPerCountry.
 */
@Entity
@Table(name = "movie_insights_per_country")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "movieinsightspercountry")
public class MovieInsightsPerCountry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private MovieInsights movieInsights;

    @OneToMany(mappedBy = "movieInsightsPerCountry")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MovieInsightsPerYear> movieInsightsPerYears = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "movieInsightsPerCountries", allowSetters = true)
    private ProductionCountry country;

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

    public MovieInsightsPerCountry movieInsights(MovieInsights movieInsights) {
        this.movieInsights = movieInsights;
        return this;
    }

    public void setMovieInsights(MovieInsights movieInsights) {
        this.movieInsights = movieInsights;
    }

    public Set<MovieInsightsPerYear> getMovieInsightsPerYears() {
        return movieInsightsPerYears;
    }

    public MovieInsightsPerCountry movieInsightsPerYears(Set<MovieInsightsPerYear> movieInsightsPerYears) {
        this.movieInsightsPerYears = movieInsightsPerYears;
        return this;
    }

    public MovieInsightsPerCountry addMovieInsightsPerYear(MovieInsightsPerYear movieInsightsPerYear) {
        this.movieInsightsPerYears.add(movieInsightsPerYear);
        movieInsightsPerYear.setMovieInsightsPerCountry(this);
        return this;
    }

    public MovieInsightsPerCountry removeMovieInsightsPerYear(MovieInsightsPerYear movieInsightsPerYear) {
        this.movieInsightsPerYears.remove(movieInsightsPerYear);
        movieInsightsPerYear.setMovieInsightsPerCountry(null);
        return this;
    }

    public void setMovieInsightsPerYears(Set<MovieInsightsPerYear> movieInsightsPerYears) {
        this.movieInsightsPerYears = movieInsightsPerYears;
    }

    public ProductionCountry getCountry() {
        return country;
    }

    public MovieInsightsPerCountry country(ProductionCountry productionCountry) {
        this.country = productionCountry;
        return this;
    }

    public void setCountry(ProductionCountry productionCountry) {
        this.country = productionCountry;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieInsightsPerCountry)) {
            return false;
        }
        return id != null && id.equals(((MovieInsightsPerCountry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieInsightsPerCountry{" +
            "id=" + getId() +
            "}";
    }
}
