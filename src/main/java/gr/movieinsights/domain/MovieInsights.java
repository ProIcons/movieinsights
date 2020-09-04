package gr.movieinsights.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DiscriminatorFormula;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("0")
@DiscriminatorFormula("0")
public class MovieInsights implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movieInsightsSequenceGenerator")
    @SequenceGenerator(name = "movieInsightsSequenceGenerator")
    private Long id;

    //region Movies
    @NotNull
    @Column(name = "total_movies", nullable = false)
    private Integer totalMovies = 0;

    //region Rating
    @NotNull
    @Column(name = "average_rating", nullable = false)
    private Double averageRating = 0D;

    @NotNull
    @Column(name = "total_rated_movies", nullable = false)
    private Integer totalRatedMovies = 0;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Movie highestRatedMovie;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Movie lowestRatedMovie;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Movie highestRatedLogarithmicMovie;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Movie lowestRatedLogarithmicMovie;
    //endregion

    //region Budget
    @NotNull
    @Column(name = "average_budget", nullable = false)
    private Double averageBudget =0D;

    @NotNull
    @Column(name = "total_budget", nullable = false)
    private Long totalBudget = 0L;

    @NotNull
    @Column(name = "total_budget_movies", nullable = false)
    private Integer totalBudgetMovies =0;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Movie highestBudgetMovie;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Movie lowestBudgetMovie;
    //endregion

    //region Revenue
    @NotNull
    @Column(name = "total_revenue", nullable = false)
    private Long totalRevenue = 0L;

    @NotNull
    @Column(name = "average_revenue", nullable = false)
    private Double averageRevenue = 0D;

    @NotNull
    @Column(name = "total_revenue_movies", nullable = false)
    private Integer totalRevenueMovies = 0;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Movie highestRevenueMovie;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Movie lowestRevenueMovie;
    //endregion
    //endregion

    //region Genres
    @NotNull
    @Column(name = "total_genres", nullable = false)
    private Integer totalGenres;

    @NotNull
    @Column(name = "average_genre_count", nullable = false)
    private Double averageGenreCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Genre mostPopularGenre;

    @NotNull
    @Column(name = "most_popular_genre_movie_count", nullable = false)
    private Integer mostPopularGenreMovieCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Genre leastPopularGenre;

    @NotNull
    @Column(name = "least_popular_genre_movie_count", nullable = false)
    private Integer leastPopularGenreMovieCount;
    //endregion

    //region Actors
    @NotNull
    @Column(name = "total_actors", nullable = false)
    private Integer totalActors;

    @NotNull
    @Column(name = "average_actor_count", nullable = false)
    private Double averageActorCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Person mostPopularActor;

    @NotNull
    @Column(name = "most_popular_actor_movie_count", nullable = false)
    private Integer mostPopularActorMovieCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Person leastPopularActor;

    @NotNull
    @Column(name = "least_popular_actor_movie_count", nullable = false)
    private Integer leastPopularActorMovieCount;
    //endregion

    //region Producers
    @NotNull
    @Column(name = "total_producers", nullable = false)
    private Integer totalProducers;

    @NotNull
    @Column(name = "average_producer_count", nullable = false)
    private Double averageProducerCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Person mostPopularProducer;

    @NotNull
    @Column(name = "most_popular_producer_movie_count", nullable = false)
    private Integer mostPopularProducerMovieCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Person leastPopularProducer;

    @NotNull
    @Column(name = "least_popular_producer_movie_count", nullable = false)
    private Integer leastPopularProducerMovieCount;
    //endregion

    //region Writers
    @NotNull
    @Column(name = "total_writers", nullable = false)
    private Integer totalWriters;

    @NotNull
    @Column(name = "average_writer_count", nullable = false)
    private Double averageWriterCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Person mostPopularWriter;

    @NotNull
    @Column(name = "most_popular_writer_movie_count", nullable = false)
    private Integer mostPopularWriterMovieCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Person leastPopularWriter;

    @NotNull
    @Column(name = "least_popular_writer_movie_count", nullable = false)
    private Integer leastPopularWriterMovieCount;
    //endregion

    //region Directors
    @NotNull
    @Column(name = "total_directors", nullable = false)
    private Integer totalDirectors;

    @NotNull
    @Column(name = "average_director_count", nullable = false)
    private Double averageDirectorCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Person mostPopularDirector;

    @NotNull
    @Column(name = "most_popular_director_movie_count", nullable = false)
    private Integer mostPopularDirectorMovieCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private Person leastPopularDirector;

    @NotNull
    @Column(name = "least_popular_director_movie_count", nullable = false)
    private Integer leastPopularDirectorMovieCount;
    //endregion

    //region ProductionCompanies
    @NotNull
    @Column(name = "total_production_companies", nullable = false)
    private Integer totalProductionCompanies;

    @NotNull
    @Column(name = "average_production_company_count", nullable = false)
    private Double averageProductionCompanyCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private ProductionCompany mostPopularProductionCompany;

    @NotNull
    @Column(name = "most_popular_production_company_movie_count", nullable = false)
    private Integer mostPopularProductionCompanyMovieCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private ProductionCompany leastPopularProductionCompany;

    @NotNull
    @Column(name = "least_popular_production_company_movie_count", nullable = false)
    private Integer leastPopularProductionCompanyMovieCount;
    //endregion

    //region ProductionCountries
    @NotNull
    @Column(name = "total_production_countries", nullable = false)
    private Integer totalProductionCountries;

    @NotNull
    @Column(name = "average_production_country_count", nullable = false)
    private Double averageProductionCountryCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private ProductionCountry mostPopularProductionCountry;

    @NotNull
    @Column(name = "most_popular_production_country_movie_count", nullable = false)
    private Integer mostPopularProductionCountryMovieCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "movieInsights", allowSetters = true)
    private ProductionCountry leastPopularProductionCountry;

    @NotNull
    @Column(name = "least_popular_production_country_movie_count", nullable = false)
    private Integer leastPopularProductionCountryMovieCount;
    //endregion

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

    public Long getTotalBudget() {
        return totalBudget;
    }

    public MovieInsights totalBudget(Long totalBudget) {
        this.totalBudget = totalBudget;
        return this;
    }

    public void setTotalBudget(Long totalBudget) {
        this.totalBudget = totalBudget;
    }

    public Integer getTotalBudgetMovies() {
        return totalBudgetMovies;
    }

    public MovieInsights totalBudgetMovies(Integer totalBudgetMovies) {
        this.totalBudgetMovies = totalBudgetMovies;
        return this;
    }

    public void setTotalBudgetMovies(Integer totalBudgetMovies) {
        this.totalBudgetMovies = totalBudgetMovies;
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

    public Long getTotalRevenue() {
        return totalRevenue;
    }

    public MovieInsights totalRevenue(Long totalRevenue) {
        this.totalRevenue = totalRevenue;
        return this;
    }

    public void setTotalRevenue(Long totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Integer getTotalRevenueMovies() {
        return totalRevenueMovies;
    }

    public MovieInsights totalRevenueMovies(Integer totalRevenueMovies) {
        this.totalRevenueMovies = totalRevenueMovies;
        return this;
    }

    public void setTotalRevenueMovies(Integer totalRevenueMovies) {
        this.totalRevenueMovies = totalRevenueMovies;
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

    public Integer getTotalRatedMovies() {
        return totalRatedMovies;
    }

    public void setTotalRatedMovies(Integer totalRatedMovies) {
        this.totalRatedMovies = totalRatedMovies;
    }

    public Movie getHighestRatedLogarithmicMovie() {
        return highestRatedLogarithmicMovie;
    }

    public void setHighestRatedLogarithmicMovie(Movie highestRatedLogarithmicMovie) {
        this.highestRatedLogarithmicMovie = highestRatedLogarithmicMovie;
    }

    public Movie getLowestRatedLogarithmicMovie() {
        return lowestRatedLogarithmicMovie;
    }

    public void setLowestRatedLogarithmicMovie(Movie lowestRatedLogarithmicMovie) {
        this.lowestRatedLogarithmicMovie = lowestRatedLogarithmicMovie;
    }

    public Integer getTotalGenres() {
        return totalGenres;
    }

    public void setTotalGenres(Integer totalGenres) {
        this.totalGenres = totalGenres;
    }

    public Genre getLeastPopularGenre() {
        return leastPopularGenre;
    }

    public void setLeastPopularGenre(Genre leastPopularGenre) {
        this.leastPopularGenre = leastPopularGenre;
    }

    public Integer getLeastPopularGenreMovieCount() {
        return leastPopularGenreMovieCount;
    }

    public void setLeastPopularGenreMovieCount(Integer leastPopularGenreMovieCount) {
        this.leastPopularGenreMovieCount = leastPopularGenreMovieCount;
    }

    public Integer getTotalActors() {
        return totalActors;
    }

    public void setTotalActors(Integer totalActors) {
        this.totalActors = totalActors;
    }

    public Person getLeastPopularActor() {
        return leastPopularActor;
    }

    public void setLeastPopularActor(Person leastPopularActor) {
        this.leastPopularActor = leastPopularActor;
    }

    public Integer getLeastPopularActorMovieCount() {
        return leastPopularActorMovieCount;
    }

    public void setLeastPopularActorMovieCount(Integer leastPopularActorMovieCount) {
        this.leastPopularActorMovieCount = leastPopularActorMovieCount;
    }

    public Integer getTotalProducers() {
        return totalProducers;
    }

    public void setTotalProducers(Integer totalProducers) {
        this.totalProducers = totalProducers;
    }

    public Person getLeastPopularProducer() {
        return leastPopularProducer;
    }

    public void setLeastPopularProducer(Person leastPopularProducer) {
        this.leastPopularProducer = leastPopularProducer;
    }

    public Integer getLeastPopularProducerMovieCount() {
        return leastPopularProducerMovieCount;
    }

    public void setLeastPopularProducerMovieCount(Integer leastPopularProducerMovieCount) {
        this.leastPopularProducerMovieCount = leastPopularProducerMovieCount;
    }

    public Integer getTotalWriters() {
        return totalWriters;
    }

    public void setTotalWriters(Integer totalWriters) {
        this.totalWriters = totalWriters;
    }

    public Person getLeastPopularWriter() {
        return leastPopularWriter;
    }

    public void setLeastPopularWriter(Person leastPopularWriter) {
        this.leastPopularWriter = leastPopularWriter;
    }

    public Integer getLeastPopularWriterMovieCount() {
        return leastPopularWriterMovieCount;
    }

    public void setLeastPopularWriterMovieCount(Integer leastPopularWriterMovieCount) {
        this.leastPopularWriterMovieCount = leastPopularWriterMovieCount;
    }

    public Integer getTotalDirectors() {
        return totalDirectors;
    }

    public void setTotalDirectors(Integer totalDirectors) {
        this.totalDirectors = totalDirectors;
    }

    public Person getLeastPopularDirector() {
        return leastPopularDirector;
    }

    public void setLeastPopularDirector(Person leastPopularDirector) {
        this.leastPopularDirector = leastPopularDirector;
    }

    public Integer getLeastPopularDirectorMovieCount() {
        return leastPopularDirectorMovieCount;
    }

    public void setLeastPopularDirectorMovieCount(Integer leastPopularDirectorMovieCount) {
        this.leastPopularDirectorMovieCount = leastPopularDirectorMovieCount;
    }

    public Integer getTotalProductionCompanies() {
        return totalProductionCompanies;
    }

    public void setTotalProductionCompanies(Integer totalProductionCompanies) {
        this.totalProductionCompanies = totalProductionCompanies;
    }

    public ProductionCompany getLeastPopularProductionCompany() {
        return leastPopularProductionCompany;
    }

    public void setLeastPopularProductionCompany(ProductionCompany leastPopularProductionCompany) {
        this.leastPopularProductionCompany = leastPopularProductionCompany;
    }

    public Integer getLeastPopularProductionCompanyMovieCount() {
        return leastPopularProductionCompanyMovieCount;
    }

    public void setLeastPopularProductionCompanyMovieCount(Integer leastPopularProductionCompanyMovieCount) {
        this.leastPopularProductionCompanyMovieCount = leastPopularProductionCompanyMovieCount;
    }

    public Integer getTotalProductionCountries() {
        return totalProductionCountries;
    }

    public void setTotalProductionCountries(Integer totalProductionCountries) {
        this.totalProductionCountries = totalProductionCountries;
    }

    public ProductionCountry getLeastPopularProductionCountry() {
        return leastPopularProductionCountry;
    }

    public void setLeastPopularProductionCountry(ProductionCountry leastPopularProductionCountry) {
        this.leastPopularProductionCountry = leastPopularProductionCountry;
    }

    public Integer getLeastPopularProductionCountryMovieCount() {
        return leastPopularProductionCountryMovieCount;
    }

    public void setLeastPopularProductionCountryMovieCount(Integer leastPopularProductionCountryMovieCount) {
        this.leastPopularProductionCountryMovieCount = leastPopularProductionCountryMovieCount;
    }

    public Double getAverageGenreCount() {
        return averageGenreCount;
    }

    public void setAverageGenreCount(Double averageGenreCount) {
        this.averageGenreCount = averageGenreCount;
    }

    public Double getAverageActorCount() {
        return averageActorCount;
    }

    public void setAverageActorCount(Double averageActorCount) {
        this.averageActorCount = averageActorCount;
    }

    public Double getAverageProducerCount() {
        return averageProducerCount;
    }

    public void setAverageProducerCount(Double averageProducerCount) {
        this.averageProducerCount = averageProducerCount;
    }

    public Double getAverageWriterCount() {
        return averageWriterCount;
    }

    public void setAverageWriterCount(Double averageWriterCount) {
        this.averageWriterCount = averageWriterCount;
    }

    public Double getAverageDirectorCount() {
        return averageDirectorCount;
    }

    public void setAverageDirectorCount(Double averageDirectorCount) {
        this.averageDirectorCount = averageDirectorCount;
    }

    public Double getAverageProductionCompanyCount() {
        return averageProductionCompanyCount;
    }

    public void setAverageProductionCompanyCount(Double averageProductionCompanyCount) {
        this.averageProductionCompanyCount = averageProductionCompanyCount;
    }

    public Double getAverageProductionCountryCount() {
        return averageProductionCountryCount;
    }

    public void setAverageProductionCountryCount(Double averageProductionCountryCount) {
        this.averageProductionCountryCount = averageProductionCountryCount;
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
            ", totalBudget=" + getTotalBudget() +
            ", totalBudgetMovies=" + getTotalBudgetMovies() +
            ", averageRevenue=" + getAverageRevenue() +
            ", totalRevenue=" + getTotalRevenue() +
            ", totalRevenueMovies=" + getTotalRevenueMovies() +
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
