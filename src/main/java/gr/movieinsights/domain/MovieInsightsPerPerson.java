package gr.movieinsights.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gr.movieinsights.domain.enumeration.CreditRole;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * A MovieInsightsPerPerson.
 */
@Entity
@Table(name = "movie_insights_per_person")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "movieinsightsperperson")
public class MovieInsightsPerPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_as", nullable = false)
    private CreditRole as;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private MovieInsights movieInsights;

    @OneToMany(mappedBy = "movieInsightsPerPerson")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MovieInsightsPerYear> movieInsightsPerYears = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "movieInsightsPerPeople", allowSetters = true)
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CreditRole getAs() {
        return as;
    }

    public MovieInsightsPerPerson as(CreditRole as) {
        this.as = as;
        return this;
    }

    public void setAs(CreditRole as) {
        this.as = as;
    }

    public MovieInsights getMovieInsights() {
        return movieInsights;
    }

    public MovieInsightsPerPerson movieInsights(MovieInsights movieInsights) {
        this.movieInsights = movieInsights;
        return this;
    }

    public void setMovieInsights(MovieInsights movieInsights) {
        this.movieInsights = movieInsights;
    }

    public Set<MovieInsightsPerYear> getMovieInsightsPerYears() {
        return movieInsightsPerYears;
    }

    public MovieInsightsPerPerson movieInsightsPerYears(Set<MovieInsightsPerYear> movieInsightsPerYears) {
        this.movieInsightsPerYears = movieInsightsPerYears;
        return this;
    }

    public MovieInsightsPerPerson addMovieInsightsPerYear(MovieInsightsPerYear movieInsightsPerYear) {
        this.movieInsightsPerYears.add(movieInsightsPerYear);
        movieInsightsPerYear.setMovieInsightsPerPerson(this);
        return this;
    }

    public MovieInsightsPerPerson removeMovieInsightsPerYear(MovieInsightsPerYear movieInsightsPerYear) {
        this.movieInsightsPerYears.remove(movieInsightsPerYear);
        movieInsightsPerYear.setMovieInsightsPerPerson(null);
        return this;
    }

    public void setMovieInsightsPerYears(Set<MovieInsightsPerYear> movieInsightsPerYears) {
        this.movieInsightsPerYears = movieInsightsPerYears;
    }

    public Person getPerson() {
        return person;
    }

    public MovieInsightsPerPerson person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieInsightsPerPerson)) {
            return false;
        }
        return id != null && id.equals(((MovieInsightsPerPerson) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieInsightsPerPerson{" +
            "id=" + getId() +
            ", as='" + getAs() + "'" +
            "}";
    }
}
