package gr.movieinsights.service.dto.movieinsights;

import gr.movieinsights.service.dto.BaseDTO;
import gr.movieinsights.service.dto.company.BasicProductionCompanyDTO;
import gr.movieinsights.service.dto.country.BasicProductionCountryDTO;
import gr.movieinsights.service.dto.genre.BasicGenreDTO;
import gr.movieinsights.service.dto.movie.BasicMovieDTO;
import gr.movieinsights.service.dto.person.BasicPersonDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link gr.movieinsights.domain.MovieInsights} entity.
 */
public class MovieInsightsDTO extends BaseDTO {

    //region Movies
    @NotNull
    private Integer totalMovies;

    //region Rating
    @NotNull
    private Double averageRating;
    @NotNull
    private Integer totalRatedMovies;
    private BasicMovieDTO highestRatedMovie;
    private BasicMovieDTO highestRatedLogarithmicMovie;
    private BasicMovieDTO lowestRatedMovie;
    private BasicMovieDTO lowestRatedLogarithmicMovie;
    //endregion

    //region Budget
    @NotNull
    private Double averageBudget;
    @NotNull
    private Long totalBudget;
    @NotNull
    private Integer totalBudgetMovies;
    private BasicMovieDTO highestBudgetMovie;
    private BasicMovieDTO lowestBudgetMovie;
    //endregion

    //region Revenue
    @NotNull
    private Double averageRevenue;
    @NotNull
    private Long totalRevenue;
    @NotNull
    private Integer totalRevenueMovies;
    private BasicMovieDTO highestRevenueMovie;
    private BasicMovieDTO lowestRevenueMovie;
    //endregion
    //endregion

    //region Genres
    @NotNull
    private Integer totalGenres;
    @NotNull
    private Double averageGenreCount;
    private BasicGenreDTO mostPopularGenre;
    @NotNull
    private Integer mostPopularGenreMovieCount;
    private BasicGenreDTO leastPopularGenre;
    @NotNull
    private Integer leastPopularGenreMovieCount;
    //endregion

    //region Actors
    @NotNull
    private Integer totalActors;
    @NotNull
    private Double averageActorCount;
    private BasicPersonDTO mostPopularActor;
    @NotNull
    private Integer mostPopularActorMovieCount;
    private BasicPersonDTO leastPopularActor;
    @NotNull
    private Integer leastPopularActorMovieCount;
    //endregion

    //region Writers
    @NotNull
    private Integer totalWriters;
    @NotNull
    private Double averageWriterCount;
    private BasicPersonDTO mostPopularWriter;
    @NotNull
    private Integer mostPopularWriterMovieCount;
    private BasicPersonDTO leastPopularWriter;
    @NotNull
    private Integer leastPopularWriterMovieCount;
    //endregion

    //region Directors
    @NotNull
    private Integer totalDirectors;
    @NotNull
    private Double averageDirectorCount;
    private BasicPersonDTO mostPopularDirector;
    @NotNull
    private Integer mostPopularDirectorMovieCount;
    private BasicPersonDTO leastPopularDirector;
    @NotNull
    private Integer leastPopularDirectorMovieCount;
    //endregion

    //region Producers
    @NotNull
    private Integer totalProducers;
    @NotNull
    private Double averageProducerCount;
    private BasicPersonDTO mostPopularProducer;
    @NotNull
    private Integer mostPopularProducerMovieCount;
    private BasicPersonDTO leastPopularProducer;
    @NotNull
    private Integer leastPopularProducerMovieCount;
    //endregion

    //region Companies
    @NotNull
    private Integer totalProductionCompanies;
    @NotNull
    private Double averageProductionCompanyCount;
    private BasicProductionCompanyDTO mostPopularProductionCompany;
    @NotNull
    private Integer mostPopularProductionCompanyMovieCount;
    private BasicProductionCompanyDTO leastPopularProductionCompany;
    @NotNull
    private Integer leastPopularProductionCompanyMovieCount;
    //endregion

    //region Countries
    @NotNull
    private Integer totalProductionCountries;
    @NotNull
    private Double averageProductionCountryCount;
    private BasicProductionCountryDTO mostPopularProductionCountry;
    @NotNull
    private Integer mostPopularProductionCountryMovieCount;
    private BasicProductionCountryDTO leastPopularProductionCountry;
    @NotNull
    private Integer leastPopularProductionCountryMovieCount;
    //endregion

    //region Getters & Setters
    public Integer getTotalMovies() {
        return totalMovies;
    }

    public void setTotalMovies(Integer totalMovies) {
        this.totalMovies = totalMovies;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getTotalRatedMovies() {
        return totalRatedMovies;
    }

    public void setTotalRatedMovies(Integer totalRatedMovies) {
        this.totalRatedMovies = totalRatedMovies;
    }

    public BasicMovieDTO getHighestRatedMovie() {
        return highestRatedMovie;
    }

    public void setHighestRatedMovie(BasicMovieDTO highestRatedMovie) {
        this.highestRatedMovie = highestRatedMovie;
    }

    public BasicMovieDTO getHighestRatedLogarithmicMovie() {
        return highestRatedLogarithmicMovie;
    }

    public void setHighestRatedLogarithmicMovie(BasicMovieDTO highestRatedLogarithmicMovie) {
        this.highestRatedLogarithmicMovie = highestRatedLogarithmicMovie;
    }

    public BasicMovieDTO getLowestRatedMovie() {
        return lowestRatedMovie;
    }

    public void setLowestRatedMovie(BasicMovieDTO lowestRatedMovie) {
        this.lowestRatedMovie = lowestRatedMovie;
    }

    public BasicMovieDTO getLowestRatedLogarithmicMovie() {
        return lowestRatedLogarithmicMovie;
    }

    public void setLowestRatedLogarithmicMovie(BasicMovieDTO lowestRatedMovieLogarithmic) {
        this.lowestRatedLogarithmicMovie = lowestRatedMovieLogarithmic;
    }

    public Double getAverageBudget() {
        return averageBudget;
    }

    public void setAverageBudget(Double averageBudget) {
        this.averageBudget = averageBudget;
    }

    public Long getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(Long totalBudget) {
        this.totalBudget = totalBudget;
    }

    public Integer getTotalBudgetMovies() {
        return totalBudgetMovies;
    }

    public void setTotalBudgetMovies(Integer totalBudgetMovies) {
        this.totalBudgetMovies = totalBudgetMovies;
    }

    public BasicMovieDTO getHighestBudgetMovie() {
        return highestBudgetMovie;
    }

    public void setHighestBudgetMovie(BasicMovieDTO highestBudgetMovie) {
        this.highestBudgetMovie = highestBudgetMovie;
    }

    public BasicMovieDTO getLowestBudgetMovie() {
        return lowestBudgetMovie;
    }

    public void setLowestBudgetMovie(BasicMovieDTO lowestBudgetMovie) {
        this.lowestBudgetMovie = lowestBudgetMovie;
    }

    public Double getAverageRevenue() {
        return averageRevenue;
    }

    public void setAverageRevenue(Double averageRevenue) {
        this.averageRevenue = averageRevenue;
    }

    public Long getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Long totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Integer getTotalRevenueMovies() {
        return totalRevenueMovies;
    }

    public void setTotalRevenueMovies(Integer totalRevenueMovies) {
        this.totalRevenueMovies = totalRevenueMovies;
    }

    public BasicMovieDTO getHighestRevenueMovie() {
        return highestRevenueMovie;
    }

    public void setHighestRevenueMovie(BasicMovieDTO highestRevenueMovie) {
        this.highestRevenueMovie = highestRevenueMovie;
    }

    public BasicMovieDTO getLowestRevenueMovie() {
        return lowestRevenueMovie;
    }

    public void setLowestRevenueMovie(BasicMovieDTO lowestRevenueMovie) {
        this.lowestRevenueMovie = lowestRevenueMovie;
    }

    public Integer getTotalGenres() {
        return totalGenres;
    }

    public void setTotalGenres(Integer totalGenres) {
        this.totalGenres = totalGenres;
    }

    public Double getAverageGenreCount() {
        return averageGenreCount;
    }

    public void setAverageGenreCount(Double averageGenreCount) {
        this.averageGenreCount = averageGenreCount;
    }

    public BasicGenreDTO getMostPopularGenre() {
        return mostPopularGenre;
    }

    public void setMostPopularGenre(BasicGenreDTO mostPopularGenre) {
        this.mostPopularGenre = mostPopularGenre;
    }

    public Integer getMostPopularGenreMovieCount() {
        return mostPopularGenreMovieCount;
    }

    public void setMostPopularGenreMovieCount(Integer mostPopularGenreMovieCount) {
        this.mostPopularGenreMovieCount = mostPopularGenreMovieCount;
    }

    public BasicGenreDTO getLeastPopularGenre() {
        return leastPopularGenre;
    }

    public void setLeastPopularGenre(BasicGenreDTO leastPopularGenre) {
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

    public Double getAverageActorCount() {
        return averageActorCount;
    }

    public void setAverageActorCount(Double averageActorCount) {
        this.averageActorCount = averageActorCount;
    }

    public BasicPersonDTO getMostPopularActor() {
        return mostPopularActor;
    }

    public void setMostPopularActor(BasicPersonDTO mostPopularActor) {
        this.mostPopularActor = mostPopularActor;
    }

    public Integer getMostPopularActorMovieCount() {
        return mostPopularActorMovieCount;
    }

    public void setMostPopularActorMovieCount(Integer mostPopularActorMovieCount) {
        this.mostPopularActorMovieCount = mostPopularActorMovieCount;
    }

    public BasicPersonDTO getLeastPopularActor() {
        return leastPopularActor;
    }

    public void setLeastPopularActor(BasicPersonDTO leastPopularActor) {
        this.leastPopularActor = leastPopularActor;
    }

    public Integer getLeastPopularActorMovieCount() {
        return leastPopularActorMovieCount;
    }

    public void setLeastPopularActorMovieCount(Integer leastPopularActorMovieCount) {
        this.leastPopularActorMovieCount = leastPopularActorMovieCount;
    }

    public Integer getTotalWriters() {
        return totalWriters;
    }

    public void setTotalWriters(Integer totalWriters) {
        this.totalWriters = totalWriters;
    }

    public Double getAverageWriterCount() {
        return averageWriterCount;
    }

    public void setAverageWriterCount(Double averageWriterCount) {
        this.averageWriterCount = averageWriterCount;
    }

    public BasicPersonDTO getMostPopularWriter() {
        return mostPopularWriter;
    }

    public void setMostPopularWriter(BasicPersonDTO mostPopularWriter) {
        this.mostPopularWriter = mostPopularWriter;
    }

    public Integer getMostPopularWriterMovieCount() {
        return mostPopularWriterMovieCount;
    }

    public void setMostPopularWriterMovieCount(Integer mostPopularWriterMovieCount) {
        this.mostPopularWriterMovieCount = mostPopularWriterMovieCount;
    }

    public BasicPersonDTO getLeastPopularWriter() {
        return leastPopularWriter;
    }

    public void setLeastPopularWriter(BasicPersonDTO leastPopularWriter) {
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

    public Double getAverageDirectorCount() {
        return averageDirectorCount;
    }

    public void setAverageDirectorCount(Double averageDirectorCount) {
        this.averageDirectorCount = averageDirectorCount;
    }

    public BasicPersonDTO getMostPopularDirector() {
        return mostPopularDirector;
    }

    public void setMostPopularDirector(BasicPersonDTO mostPopularDirector) {
        this.mostPopularDirector = mostPopularDirector;
    }

    public Integer getMostPopularDirectorMovieCount() {
        return mostPopularDirectorMovieCount;
    }

    public void setMostPopularDirectorMovieCount(Integer mostPopularDirectorMovieCount) {
        this.mostPopularDirectorMovieCount = mostPopularDirectorMovieCount;
    }

    public BasicPersonDTO getLeastPopularDirector() {
        return leastPopularDirector;
    }

    public void setLeastPopularDirector(BasicPersonDTO leastPopularDirector) {
        this.leastPopularDirector = leastPopularDirector;
    }

    public Integer getLeastPopularDirectorMovieCount() {
        return leastPopularDirectorMovieCount;
    }

    public void setLeastPopularDirectorMovieCount(Integer leastPopularDirectorMovieCount) {
        this.leastPopularDirectorMovieCount = leastPopularDirectorMovieCount;
    }

    public Integer getTotalProducers() {
        return totalProducers;
    }

    public void setTotalProducers(Integer totalProducers) {
        this.totalProducers = totalProducers;
    }

    public Double getAverageProducerCount() {
        return averageProducerCount;
    }

    public void setAverageProducerCount(Double averageProducerCount) {
        this.averageProducerCount = averageProducerCount;
    }

    public BasicPersonDTO getMostPopularProducer() {
        return mostPopularProducer;
    }

    public void setMostPopularProducer(BasicPersonDTO mostPopularProducer) {
        this.mostPopularProducer = mostPopularProducer;
    }

    public Integer getMostPopularProducerMovieCount() {
        return mostPopularProducerMovieCount;
    }

    public void setMostPopularProducerMovieCount(Integer mostPopularProducerMovieCount) {
        this.mostPopularProducerMovieCount = mostPopularProducerMovieCount;
    }

    public BasicPersonDTO getLeastPopularProducer() {
        return leastPopularProducer;
    }

    public void setLeastPopularProducer(BasicPersonDTO leastPopularProducer) {
        this.leastPopularProducer = leastPopularProducer;
    }

    public Integer getLeastPopularProducerMovieCount() {
        return leastPopularProducerMovieCount;
    }

    public void setLeastPopularProducerMovieCount(Integer leastPopularProducerMovieCount) {
        this.leastPopularProducerMovieCount = leastPopularProducerMovieCount;
    }

    public Integer getTotalProductionCompanies() {
        return totalProductionCompanies;
    }

    public void setTotalProductionCompanies(Integer totalProductionCompanies) {
        this.totalProductionCompanies = totalProductionCompanies;
    }

    public Double getAverageProductionCompanyCount() {
        return averageProductionCompanyCount;
    }

    public void setAverageProductionCompanyCount(Double averageProductionCompanyCount) {
        this.averageProductionCompanyCount = averageProductionCompanyCount;
    }

    public BasicProductionCompanyDTO getMostPopularProductionCompany() {
        return mostPopularProductionCompany;
    }

    public void setMostPopularProductionCompany(BasicProductionCompanyDTO mostPopularProductionCompany) {
        this.mostPopularProductionCompany = mostPopularProductionCompany;
    }

    public Integer getMostPopularProductionCompanyMovieCount() {
        return mostPopularProductionCompanyMovieCount;
    }

    public void setMostPopularProductionCompanyMovieCount(Integer mostPopularProductionCompanyMovieCount) {
        this.mostPopularProductionCompanyMovieCount = mostPopularProductionCompanyMovieCount;
    }

    public BasicProductionCompanyDTO getLeastPopularProductionCompany() {
        return leastPopularProductionCompany;
    }

    public void setLeastPopularProductionCompany(BasicProductionCompanyDTO leastPopularProductionCompany) {
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

    public Double getAverageProductionCountryCount() {
        return averageProductionCountryCount;
    }

    public void setAverageProductionCountryCount(Double averageProductionCountryCount) {
        this.averageProductionCountryCount = averageProductionCountryCount;
    }

    public BasicProductionCountryDTO getMostPopularProductionCountry() {
        return mostPopularProductionCountry;
    }

    public void setMostPopularProductionCountry(BasicProductionCountryDTO mostPopularProductionCountry) {
        this.mostPopularProductionCountry = mostPopularProductionCountry;
    }

    public Integer getMostPopularProductionCountryMovieCount() {
        return mostPopularProductionCountryMovieCount;
    }

    public void setMostPopularProductionCountryMovieCount(Integer mostPopularProductionCountryMovieCount) {
        this.mostPopularProductionCountryMovieCount = mostPopularProductionCountryMovieCount;
    }

    public BasicProductionCountryDTO getLeastPopularProductionCountry() {
        return leastPopularProductionCountry;
    }

    public void setLeastPopularProductionCountry(BasicProductionCountryDTO leastPopularProductionCountry) {
        this.leastPopularProductionCountry = leastPopularProductionCountry;
    }

    public Integer getLeastPopularProductionCountryMovieCount() {
        return leastPopularProductionCountryMovieCount;
    }

    public void setLeastPopularProductionCountryMovieCount(Integer leastPopularProductionCountryMovieCount) {
        this.leastPopularProductionCountryMovieCount = leastPopularProductionCountryMovieCount;
    }
    //endregion

    //region Equals & HashCode & ToString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof MovieInsightsDTO)) return false;

        MovieInsightsDTO that = (MovieInsightsDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(totalMovies, that.totalMovies)
            .append(averageRating, that.averageRating)
            .append(totalRatedMovies, that.totalRatedMovies)
            .append(highestRatedMovie, that.highestRatedMovie)
            .append(highestRatedLogarithmicMovie, that.highestRatedLogarithmicMovie)
            .append(lowestRatedMovie, that.lowestRatedMovie)
            .append(lowestRatedLogarithmicMovie, that.lowestRatedLogarithmicMovie)
            .append(averageBudget, that.averageBudget)
            .append(totalBudget, that.totalBudget)
            .append(totalBudgetMovies, that.totalBudgetMovies)
            .append(highestBudgetMovie, that.highestBudgetMovie)
            .append(lowestBudgetMovie, that.lowestBudgetMovie)
            .append(averageRevenue, that.averageRevenue)
            .append(totalRevenue, that.totalRevenue)
            .append(totalRevenueMovies, that.totalRevenueMovies)
            .append(highestRevenueMovie, that.highestRevenueMovie)
            .append(lowestRevenueMovie, that.lowestRevenueMovie)
            .append(totalGenres, that.totalGenres)
            .append(averageGenreCount, that.averageGenreCount)
            .append(mostPopularGenre, that.mostPopularGenre)
            .append(mostPopularGenreMovieCount, that.mostPopularGenreMovieCount)
            .append(leastPopularGenre, that.leastPopularGenre)
            .append(leastPopularGenreMovieCount, that.leastPopularGenreMovieCount)
            .append(totalActors, that.totalActors)
            .append(averageActorCount, that.averageActorCount)
            .append(mostPopularActor, that.mostPopularActor)
            .append(mostPopularActorMovieCount, that.mostPopularActorMovieCount)
            .append(leastPopularActor, that.leastPopularActor)
            .append(leastPopularActorMovieCount, that.leastPopularActorMovieCount)
            .append(totalWriters, that.totalWriters)
            .append(averageWriterCount, that.averageWriterCount)
            .append(mostPopularWriter, that.mostPopularWriter)
            .append(mostPopularWriterMovieCount, that.mostPopularWriterMovieCount)
            .append(leastPopularWriter, that.leastPopularWriter)
            .append(leastPopularWriterMovieCount, that.leastPopularWriterMovieCount)
            .append(totalDirectors, that.totalDirectors)
            .append(averageDirectorCount, that.averageDirectorCount)
            .append(mostPopularDirector, that.mostPopularDirector)
            .append(mostPopularDirectorMovieCount, that.mostPopularDirectorMovieCount)
            .append(leastPopularDirector, that.leastPopularDirector)
            .append(leastPopularDirectorMovieCount, that.leastPopularDirectorMovieCount)
            .append(totalProducers, that.totalProducers)
            .append(averageProducerCount, that.averageProducerCount)
            .append(mostPopularProducer, that.mostPopularProducer)
            .append(mostPopularProducerMovieCount, that.mostPopularProducerMovieCount)
            .append(leastPopularProducer, that.leastPopularProducer)
            .append(leastPopularProducerMovieCount, that.leastPopularProducerMovieCount)
            .append(totalProductionCompanies, that.totalProductionCompanies)
            .append(averageProductionCompanyCount, that.averageProductionCompanyCount)
            .append(mostPopularProductionCompany, that.mostPopularProductionCompany)
            .append(mostPopularProductionCompanyMovieCount, that.mostPopularProductionCompanyMovieCount)
            .append(leastPopularProductionCompany, that.leastPopularProductionCompany)
            .append(leastPopularProductionCompanyMovieCount, that.leastPopularProductionCompanyMovieCount)
            .append(totalProductionCountries, that.totalProductionCountries)
            .append(averageProductionCountryCount, that.averageProductionCountryCount)
            .append(mostPopularProductionCountry, that.mostPopularProductionCountry)
            .append(mostPopularProductionCountryMovieCount, that.mostPopularProductionCountryMovieCount)
            .append(leastPopularProductionCountry, that.leastPopularProductionCountry)
            .append(leastPopularProductionCountryMovieCount, that.leastPopularProductionCountryMovieCount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(totalMovies)
            .append(averageRating)
            .append(totalRatedMovies)
            .append(highestRatedMovie)
            .append(highestRatedLogarithmicMovie)
            .append(lowestRatedMovie)
            .append(lowestRatedLogarithmicMovie)
            .append(averageBudget)
            .append(totalBudget)
            .append(totalBudgetMovies)
            .append(highestBudgetMovie)
            .append(lowestBudgetMovie)
            .append(averageRevenue)
            .append(totalRevenue)
            .append(totalRevenueMovies)
            .append(highestRevenueMovie)
            .append(lowestRevenueMovie)
            .append(totalGenres)
            .append(averageGenreCount)
            .append(mostPopularGenre)
            .append(mostPopularGenreMovieCount)
            .append(leastPopularGenre)
            .append(leastPopularGenreMovieCount)
            .append(totalActors)
            .append(averageActorCount)
            .append(mostPopularActor)
            .append(mostPopularActorMovieCount)
            .append(leastPopularActor)
            .append(leastPopularActorMovieCount)
            .append(totalWriters)
            .append(averageWriterCount)
            .append(mostPopularWriter)
            .append(mostPopularWriterMovieCount)
            .append(leastPopularWriter)
            .append(leastPopularWriterMovieCount)
            .append(totalDirectors)
            .append(averageDirectorCount)
            .append(mostPopularDirector)
            .append(mostPopularDirectorMovieCount)
            .append(leastPopularDirector)
            .append(leastPopularDirectorMovieCount)
            .append(totalProducers)
            .append(averageProducerCount)
            .append(mostPopularProducer)
            .append(mostPopularProducerMovieCount)
            .append(leastPopularProducer)
            .append(leastPopularProducerMovieCount)
            .append(totalProductionCompanies)
            .append(averageProductionCompanyCount)
            .append(mostPopularProductionCompany)
            .append(mostPopularProductionCompanyMovieCount)
            .append(leastPopularProductionCompany)
            .append(leastPopularProductionCompanyMovieCount)
            .append(totalProductionCountries)
            .append(averageProductionCountryCount)
            .append(mostPopularProductionCountry)
            .append(mostPopularProductionCountryMovieCount)
            .append(leastPopularProductionCountry)
            .append(leastPopularProductionCountryMovieCount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("totalMovies", totalMovies)
            .append("averageRating", averageRating)
            .append("totalRatedMovies", totalRatedMovies)
            .append("highestRatedMovie", highestRatedMovie)
            .append("highestRatedMovieLogarithmic", highestRatedLogarithmicMovie)
            .append("lowestRatedMovie", lowestRatedMovie)
            .append("lowestRatedMovieLogarithmic", lowestRatedLogarithmicMovie)
            .append("averageBudget", averageBudget)
            .append("totalBudget", totalBudget)
            .append("totalBudgetMovies", totalBudgetMovies)
            .append("highestBudgetMovie", highestBudgetMovie)
            .append("lowestBudgetMovie", lowestBudgetMovie)
            .append("averageRevenue", averageRevenue)
            .append("totalRevenue", totalRevenue)
            .append("totalRevenueMovies", totalRevenueMovies)
            .append("highestRevenueMovie", highestRevenueMovie)
            .append("lowestRevenueMovie", lowestRevenueMovie)
            .append("totalGenres", totalGenres)
            .append("averageGenreCount", averageGenreCount)
            .append("mostPopularGenre", mostPopularGenre)
            .append("mostPopularGenreMovieCount", mostPopularGenreMovieCount)
            .append("leastPopularGenre", leastPopularGenre)
            .append("leastPopularGenreMovieCount", leastPopularGenreMovieCount)
            .append("totalActors", totalActors)
            .append("averageActorCount", averageActorCount)
            .append("mostPopularActor", mostPopularActor)
            .append("mostPopularActorMovieCount", mostPopularActorMovieCount)
            .append("leastPopularActor", leastPopularActor)
            .append("leastPopularActorMovieCount", leastPopularActorMovieCount)
            .append("totalWriters", totalWriters)
            .append("averageWriterCount", averageWriterCount)
            .append("mostPopularWriter", mostPopularWriter)
            .append("mostPopularWriterMovieCount", mostPopularWriterMovieCount)
            .append("leastPopularWriter", leastPopularWriter)
            .append("leastPopularWriterMovieCount", leastPopularWriterMovieCount)
            .append("totalDirectors", totalDirectors)
            .append("averageDirectorCount", averageDirectorCount)
            .append("mostPopularDirector", mostPopularDirector)
            .append("mostPopularDirectorMovieCount", mostPopularDirectorMovieCount)
            .append("leastPopularDirector", leastPopularDirector)
            .append("leastPopularDirectorMovieCount", leastPopularDirectorMovieCount)
            .append("totalProducers", totalProducers)
            .append("averageProducerCount", averageProducerCount)
            .append("mostPopularProducer", mostPopularProducer)
            .append("mostPopularProducerMovieCount", mostPopularProducerMovieCount)
            .append("leastPopularProducer", leastPopularProducer)
            .append("leastPopularProducerMovieCount", leastPopularProducerMovieCount)
            .append("totalProductionCompanies", totalProductionCompanies)
            .append("averageProductionCompanyCount", averageProductionCompanyCount)
            .append("mostPopularProductionCompany", mostPopularProductionCompany)
            .append("mostPopularProductionCompanyMovieCount", mostPopularProductionCompanyMovieCount)
            .append("leastPopularProductionCompany", leastPopularProductionCompany)
            .append("leastPopularProductionCompanyMovieCount", leastPopularProductionCompanyMovieCount)
            .append("totalProductionCountries", totalProductionCountries)
            .append("averageProductionCountryCount", averageProductionCountryCount)
            .append("mostPopularProductionCountry", mostPopularProductionCountry)
            .append("mostPopularProductionCountryMovieCount", mostPopularProductionCountryMovieCount)
            .append("leastPopularProductionCountry", leastPopularProductionCountry)
            .append("leastPopularProductionCountryMovieCount", leastPopularProductionCountryMovieCount)
            .toString();
    }
    //endregion
}
