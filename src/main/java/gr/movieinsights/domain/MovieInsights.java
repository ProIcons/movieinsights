package gr.movieinsights.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A MovieInsights.
 */
@Entity
@Table(name = "movie_insights")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "movieinsights")
public class MovieInsights implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "average_rating", nullable = false)
    private Double averageRating;

    @NotNull
    @Column(name = "average_budget", nullable = false)
    private Double averageBudget;

    @NotNull
    @Column(name = "average_revenue", nullable = false)
    private Double averageRevenue;

    @NotNull
    @Column(name = "total_movies", nullable = false)
    private Integer totalMovies;

    @NotNull
    @Column(name = "most_popular_genre_movie_count", nullable = false)
    private Integer mostPopularGenreMovieCount;

    @NotNull
    @Column(name = "most_popular_actor_movie_count", nullable = false)
    private Integer mostPopularActorMovieCount;

    @NotNull
    @Column(name = "most_popular_writer_movie_count", nullable = false)
    private Integer mostPopularWriterMovieCount;

    @NotNull
    @Column(name = "most_popular_producer_movie_count", nullable = false)
    private Integer mostPopularProducerMovieCount;

    @NotNull
    @Column(name = "most_popular_director_movie_count", nullable = false)
    private Integer mostPopularDirectorMovieCount;

    @NotNull
    @Column(name = "most_popular_production_company_movie_count", nullable = false)
    private Integer mostPopularProductionCompanyMovieCount;

    @NotNull
    @Column(name = "most_popular_production_country_movie_count", nullable = false)
    private Integer mostPopularProductionCountryMovieCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Movie highestRatedMovie;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Movie lowestRatedMovie;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Movie highestBudgetMovie;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Movie lowestBudgetMovie;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Movie highestRevenueMovie;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Movie lowestRevenueMovie;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Genre mostPopularGenre;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Person mostPopularActor;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Person mostPopularProducer;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Person mostPopularWriter;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Person mostPopularDirector;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private ProductionCountry mostPopularProductionCountry;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private ProductionCompany mostPopularProductionCompany;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public MovieInsights averageRating(Double averageRating) {
        this.averageRating = averageRating;
        return this;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Double getAverageBudget() {
        return averageBudget;
    }

    public MovieInsights averageBudget(Double averageBudget) {
        this.averageBudget = averageBudget;
        return this;
    }

    public void setAverageBudget(Double averageBudget) {
        this.averageBudget = averageBudget;
    }

    public Double getAverageRevenue() {
        return averageRevenue;
    }

    public MovieInsights averageRevenue(Double averageRevenue) {
        this.averageRevenue = averageRevenue;
        return this;
    }

    public void setAverageRevenue(Double averageRevenue) {
        this.averageRevenue = averageRevenue;
    }

    public Integer getTotalMovies() {
        return totalMovies;
    }

    public MovieInsights totalMovies(Integer totalMovies) {
        this.totalMovies = totalMovies;
        return this;
    }

    public void setTotalMovies(Integer totalMovies) {
        this.totalMovies = totalMovies;
    }

    public Integer getMostPopularGenreMovieCount() {
        return mostPopularGenreMovieCount;
    }

    public MovieInsights mostPopularGenreMovieCount(Integer mostPopularGenreMovieCount) {
        this.mostPopularGenreMovieCount = mostPopularGenreMovieCount;
        return this;
    }

    public void setMostPopularGenreMovieCount(Integer mostPopularGenreMovieCount) {
        this.mostPopularGenreMovieCount = mostPopularGenreMovieCount;
    }

    public Integer getMostPopularActorMovieCount() {
        return mostPopularActorMovieCount;
    }

    public MovieInsights mostPopularActorMovieCount(Integer mostPopularActorMovieCount) {
        this.mostPopularActorMovieCount = mostPopularActorMovieCount;
        return this;
    }

    public void setMostPopularActorMovieCount(Integer mostPopularActorMovieCount) {
        this.mostPopularActorMovieCount = mostPopularActorMovieCount;
    }

    public Integer getMostPopularWriterMovieCount() {
        return mostPopularWriterMovieCount;
    }

    public MovieInsights mostPopularWriterMovieCount(Integer mostPopularWriterMovieCount) {
        this.mostPopularWriterMovieCount = mostPopularWriterMovieCount;
        return this;
    }

    public void setMostPopularWriterMovieCount(Integer mostPopularWriterMovieCount) {
        this.mostPopularWriterMovieCount = mostPopularWriterMovieCount;
    }

    public Integer getMostPopularProducerMovieCount() {
        return mostPopularProducerMovieCount;
    }

    public MovieInsights mostPopularProducerMovieCount(Integer mostPopularProducerMovieCount) {
        this.mostPopularProducerMovieCount = mostPopularProducerMovieCount;
        return this;
    }

    public void setMostPopularProducerMovieCount(Integer mostPopularProducerMovieCount) {
        this.mostPopularProducerMovieCount = mostPopularProducerMovieCount;
    }

    public Integer getMostPopularDirectorMovieCount() {
        return mostPopularDirectorMovieCount;
    }

    public MovieInsights mostPopularDirectorMovieCount(Integer mostPopularDirectorMovieCount) {
        this.mostPopularDirectorMovieCount = mostPopularDirectorMovieCount;
        return this;
    }

    public void setMostPopularDirectorMovieCount(Integer mostPopularDirectorMovieCount) {
        this.mostPopularDirectorMovieCount = mostPopularDirectorMovieCount;
    }

    public Integer getMostPopularProductionCompanyMovieCount() {
        return mostPopularProductionCompanyMovieCount;
    }

    public MovieInsights mostPopularProductionCompanyMovieCount(Integer mostPopularProductionCompanyMovieCount) {
        this.mostPopularProductionCompanyMovieCount = mostPopularProductionCompanyMovieCount;
        return this;
    }

    public void setMostPopularProductionCompanyMovieCount(Integer mostPopularProductionCompanyMovieCount) {
        this.mostPopularProductionCompanyMovieCount = mostPopularProductionCompanyMovieCount;
    }

    public Integer getMostPopularProductionCountryMovieCount() {
        return mostPopularProductionCountryMovieCount;
    }

    public MovieInsights mostPopularProductionCountryMovieCount(Integer mostPopularProductionCountryMovieCount) {
        this.mostPopularProductionCountryMovieCount = mostPopularProductionCountryMovieCount;
        return this;
    }

    public void setMostPopularProductionCountryMovieCount(Integer mostPopularProductionCountryMovieCount) {
        this.mostPopularProductionCountryMovieCount = mostPopularProductionCountryMovieCount;
    }

    public Movie getHighestRatedMovie() {
        return highestRatedMovie;
    }

    public MovieInsights highestRatedMovie(Movie movie) {
        this.highestRatedMovie = movie;
        return this;
    }

    public void setHighestRatedMovie(Movie movie) {
        this.highestRatedMovie = movie;
    }

    public Movie getLowestRatedMovie() {
        return lowestRatedMovie;
    }

    public MovieInsights lowestRatedMovie(Movie movie) {
        this.lowestRatedMovie = movie;
        return this;
    }

    public void setLowestRatedMovie(Movie movie) {
        this.lowestRatedMovie = movie;
    }

    public Movie getHighestBudgetMovie() {
        return highestBudgetMovie;
    }

    public MovieInsights highestBudgetMovie(Movie movie) {
        this.highestBudgetMovie = movie;
        return this;
    }

    public void setHighestBudgetMovie(Movie movie) {
        this.highestBudgetMovie = movie;
    }

    public Movie getLowestBudgetMovie() {
        return lowestBudgetMovie;
    }

    public MovieInsights lowestBudgetMovie(Movie movie) {
        this.lowestBudgetMovie = movie;
        return this;
    }

    public void setLowestBudgetMovie(Movie movie) {
        this.lowestBudgetMovie = movie;
    }

    public Movie getHighestRevenueMovie() {
        return highestRevenueMovie;
    }

    public MovieInsights highestRevenueMovie(Movie movie) {
        this.highestRevenueMovie = movie;
        return this;
    }

    public void setHighestRevenueMovie(Movie movie) {
        this.highestRevenueMovie = movie;
    }

    public Movie getLowestRevenueMovie() {
        return lowestRevenueMovie;
    }

    public MovieInsights lowestRevenueMovie(Movie movie) {
        this.lowestRevenueMovie = movie;
        return this;
    }

    public void setLowestRevenueMovie(Movie movie) {
        this.lowestRevenueMovie = movie;
    }

    public Genre getMostPopularGenre() {
        return mostPopularGenre;
    }

    public MovieInsights mostPopularGenre(Genre genre) {
        this.mostPopularGenre = genre;
        return this;
    }

    public void setMostPopularGenre(Genre genre) {
        this.mostPopularGenre = genre;
    }

    public Person getMostPopularActor() {
        return mostPopularActor;
    }

    public MovieInsights mostPopularActor(Person person) {
        this.mostPopularActor = person;
        return this;
    }

    public void setMostPopularActor(Person person) {
        this.mostPopularActor = person;
    }

    public Person getMostPopularProducer() {
        return mostPopularProducer;
    }

    public MovieInsights mostPopularProducer(Person person) {
        this.mostPopularProducer = person;
        return this;
    }

    public void setMostPopularProducer(Person person) {
        this.mostPopularProducer = person;
    }

    public Person getMostPopularWriter() {
        return mostPopularWriter;
    }

    public MovieInsights mostPopularWriter(Person person) {
        this.mostPopularWriter = person;
        return this;
    }

    public void setMostPopularWriter(Person person) {
        this.mostPopularWriter = person;
    }

    public Person getMostPopularDirector() {
        return mostPopularDirector;
    }

    public MovieInsights mostPopularDirector(Person person) {
        this.mostPopularDirector = person;
        return this;
    }

    public void setMostPopularDirector(Person person) {
        this.mostPopularDirector = person;
    }

    public ProductionCountry getMostPopularProductionCountry() {
        return mostPopularProductionCountry;
    }

    public MovieInsights mostPopularProductionCountry(ProductionCountry productionCountry) {
        this.mostPopularProductionCountry = productionCountry;
        return this;
    }

    public void setMostPopularProductionCountry(ProductionCountry productionCountry) {
        this.mostPopularProductionCountry = productionCountry;
    }

    public ProductionCompany getMostPopularProductionCompany() {
        return mostPopularProductionCompany;
    }

    public MovieInsights mostPopularProductionCompany(ProductionCompany productionCompany) {
        this.mostPopularProductionCompany = productionCompany;
        return this;
    }

    public void setMostPopularProductionCompany(ProductionCompany productionCompany) {
        this.mostPopularProductionCompany = productionCompany;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieInsights)) {
            return false;
        }
        return id != null && id.equals(((MovieInsights) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieInsights{" +
            "id=" + getId() +
            ", averageRating=" + getAverageRating() +
            ", averageBudget=" + getAverageBudget() +
            ", averageRevenue=" + getAverageRevenue() +
            ", totalMovies=" + getTotalMovies() +
            ", mostPopularGenreMovieCount=" + getMostPopularGenreMovieCount() +
            ", mostPopularActorMovieCount=" + getMostPopularActorMovieCount() +
            ", mostPopularWriterMovieCount=" + getMostPopularWriterMovieCount() +
            ", mostPopularProducerMovieCount=" + getMostPopularProducerMovieCount() +
            ", mostPopularDirectorMovieCount=" + getMostPopularDirectorMovieCount() +
            ", mostPopularProductionCompanyMovieCount=" + getMostPopularProductionCompanyMovieCount() +
            ", mostPopularProductionCountryMovieCount=" + getMostPopularProductionCountryMovieCount() +
            "}";
    }
}
