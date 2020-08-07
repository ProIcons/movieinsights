package gr.movieinsights.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A MovieInsightsPerYear.
 */
@Entity
@Table(name = "movie_insights_per_year")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "movieinsightsperyear")
public class MovieInsightsPerYear implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private MovieInsights movieInsights;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsightsPerYears", allowSetters = true)
    private MovieInsightsPerCountry movieInsightsPerCountry;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsightsPerYears", allowSetters = true)
    private MovieInsightsPerCompany movieInsightsPerCompany;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsightsPerYears", allowSetters = true)
    private MovieInsightsPerPerson movieInsightsPerPerson;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsightsPerYears", allowSetters = true)
    private MovieInsightsPerGenre movieInsightsPerGenre;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public MovieInsightsPerYear year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public MovieInsights getMovieInsights() {
        return movieInsights;
    }

    public MovieInsightsPerYear movieInsights(MovieInsights movieInsights) {
        this.movieInsights = movieInsights;
        return this;
    }

    public void setMovieInsights(MovieInsights movieInsights) {
        this.movieInsights = movieInsights;
    }

    public MovieInsightsPerCountry getMovieInsightsPerCountry() {
        return movieInsightsPerCountry;
    }

    public MovieInsightsPerYear movieInsightsPerCountry(MovieInsightsPerCountry movieInsightsPerCountry) {
        this.movieInsightsPerCountry = movieInsightsPerCountry;
        return this;
    }

    public void setMovieInsightsPerCountry(MovieInsightsPerCountry movieInsightsPerCountry) {
        this.movieInsightsPerCountry = movieInsightsPerCountry;
    }

    public MovieInsightsPerCompany getMovieInsightsPerCompany() {
        return movieInsightsPerCompany;
    }

    public MovieInsightsPerYear movieInsightsPerCompany(MovieInsightsPerCompany movieInsightsPerCompany) {
        this.movieInsightsPerCompany = movieInsightsPerCompany;
        return this;
    }

    public void setMovieInsightsPerCompany(MovieInsightsPerCompany movieInsightsPerCompany) {
        this.movieInsightsPerCompany = movieInsightsPerCompany;
    }

    public MovieInsightsPerPerson getMovieInsightsPerPerson() {
        return movieInsightsPerPerson;
    }

    public MovieInsightsPerYear movieInsightsPerPerson(MovieInsightsPerPerson movieInsightsPerPerson) {
        this.movieInsightsPerPerson = movieInsightsPerPerson;
        return this;
    }

    public void setMovieInsightsPerPerson(MovieInsightsPerPerson movieInsightsPerPerson) {
        this.movieInsightsPerPerson = movieInsightsPerPerson;
    }

    public MovieInsightsPerGenre getMovieInsightsPerGenre() {
        return movieInsightsPerGenre;
    }

    public MovieInsightsPerYear movieInsightsPerGenre(MovieInsightsPerGenre movieInsightsPerGenre) {
        this.movieInsightsPerGenre = movieInsightsPerGenre;
        return this;
    }

    public void setMovieInsightsPerGenre(MovieInsightsPerGenre movieInsightsPerGenre) {
        this.movieInsightsPerGenre = movieInsightsPerGenre;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieInsightsPerYear)) {
            return false;
        }
        return id != null && id.equals(((MovieInsightsPerYear) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieInsightsPerYear{" +
            "id=" + getId() +
            ", year=" + getYear() +
            "}";
    }
}
