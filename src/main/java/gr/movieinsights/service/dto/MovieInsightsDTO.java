package gr.movieinsights.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link gr.movieinsights.domain.MovieInsights} entity.
 */
public class MovieInsightsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Double averageRating;

    @NotNull
    private Double averageBudget;

    @NotNull
    private Double averageRevenue;

    @NotNull
    private Integer totalMovies;

    @NotNull
    private Integer mostPopularGenreMovieCount;

    @NotNull
    private Integer mostPopularActorMovieCount;

    @NotNull
    private Integer mostPopularWriterMovieCount;

    @NotNull
    private Integer mostPopularProducerMovieCount;

    @NotNull
    private Integer mostPopularDirectorMovieCount;

    @NotNull
    private Integer mostPopularProductionCompanyMovieCount;

    @NotNull
    private Integer mostPopularProductionCountryMovieCount;


    private Long highestRatedMovieId;

    private Long lowestRatedMovieId;

    private Long highestBudgetMovieId;

    private Long lowestBudgetMovieId;

    private Long highestRevenueMovieId;

    private Long lowestRevenueMovieId;

    private Long mostPopularGenreId;

    private Long mostPopularActorId;

    private Long mostPopularProducerId;

    private Long mostPopularWriterId;

    private Long mostPopularDirectorId;

    private Long mostPopularProductionCountryId;

    private Long mostPopularProductionCompanyId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Double getAverageBudget() {
        return averageBudget;
    }

    public void setAverageBudget(Double averageBudget) {
        this.averageBudget = averageBudget;
    }

    public Double getAverageRevenue() {
        return averageRevenue;
    }

    public void setAverageRevenue(Double averageRevenue) {
        this.averageRevenue = averageRevenue;
    }

    public Integer getTotalMovies() {
        return totalMovies;
    }

    public void setTotalMovies(Integer totalMovies) {
        this.totalMovies = totalMovies;
    }

    public Integer getMostPopularGenreMovieCount() {
        return mostPopularGenreMovieCount;
    }

    public void setMostPopularGenreMovieCount(Integer mostPopularGenreMovieCount) {
        this.mostPopularGenreMovieCount = mostPopularGenreMovieCount;
    }

    public Integer getMostPopularActorMovieCount() {
        return mostPopularActorMovieCount;
    }

    public void setMostPopularActorMovieCount(Integer mostPopularActorMovieCount) {
        this.mostPopularActorMovieCount = mostPopularActorMovieCount;
    }

    public Integer getMostPopularWriterMovieCount() {
        return mostPopularWriterMovieCount;
    }

    public void setMostPopularWriterMovieCount(Integer mostPopularWriterMovieCount) {
        this.mostPopularWriterMovieCount = mostPopularWriterMovieCount;
    }

    public Integer getMostPopularProducerMovieCount() {
        return mostPopularProducerMovieCount;
    }

    public void setMostPopularProducerMovieCount(Integer mostPopularProducerMovieCount) {
        this.mostPopularProducerMovieCount = mostPopularProducerMovieCount;
    }

    public Integer getMostPopularDirectorMovieCount() {
        return mostPopularDirectorMovieCount;
    }

    public void setMostPopularDirectorMovieCount(Integer mostPopularDirectorMovieCount) {
        this.mostPopularDirectorMovieCount = mostPopularDirectorMovieCount;
    }

    public Integer getMostPopularProductionCompanyMovieCount() {
        return mostPopularProductionCompanyMovieCount;
    }

    public void setMostPopularProductionCompanyMovieCount(Integer mostPopularProductionCompanyMovieCount) {
        this.mostPopularProductionCompanyMovieCount = mostPopularProductionCompanyMovieCount;
    }

    public Integer getMostPopularProductionCountryMovieCount() {
        return mostPopularProductionCountryMovieCount;
    }

    public void setMostPopularProductionCountryMovieCount(Integer mostPopularProductionCountryMovieCount) {
        this.mostPopularProductionCountryMovieCount = mostPopularProductionCountryMovieCount;
    }

    public Long getHighestRatedMovieId() {
        return highestRatedMovieId;
    }

    public void setHighestRatedMovieId(Long movieId) {
        this.highestRatedMovieId = movieId;
    }

    public Long getLowestRatedMovieId() {
        return lowestRatedMovieId;
    }

    public void setLowestRatedMovieId(Long movieId) {
        this.lowestRatedMovieId = movieId;
    }

    public Long getHighestBudgetMovieId() {
        return highestBudgetMovieId;
    }

    public void setHighestBudgetMovieId(Long movieId) {
        this.highestBudgetMovieId = movieId;
    }

    public Long getLowestBudgetMovieId() {
        return lowestBudgetMovieId;
    }

    public void setLowestBudgetMovieId(Long movieId) {
        this.lowestBudgetMovieId = movieId;
    }

    public Long getHighestRevenueMovieId() {
        return highestRevenueMovieId;
    }

    public void setHighestRevenueMovieId(Long movieId) {
        this.highestRevenueMovieId = movieId;
    }

    public Long getLowestRevenueMovieId() {
        return lowestRevenueMovieId;
    }

    public void setLowestRevenueMovieId(Long movieId) {
        this.lowestRevenueMovieId = movieId;
    }

    public Long getMostPopularGenreId() {
        return mostPopularGenreId;
    }

    public void setMostPopularGenreId(Long genreId) {
        this.mostPopularGenreId = genreId;
    }

    public Long getMostPopularActorId() {
        return mostPopularActorId;
    }

    public void setMostPopularActorId(Long personId) {
        this.mostPopularActorId = personId;
    }

    public Long getMostPopularProducerId() {
        return mostPopularProducerId;
    }

    public void setMostPopularProducerId(Long personId) {
        this.mostPopularProducerId = personId;
    }

    public Long getMostPopularWriterId() {
        return mostPopularWriterId;
    }

    public void setMostPopularWriterId(Long personId) {
        this.mostPopularWriterId = personId;
    }

    public Long getMostPopularDirectorId() {
        return mostPopularDirectorId;
    }

    public void setMostPopularDirectorId(Long personId) {
        this.mostPopularDirectorId = personId;
    }

    public Long getMostPopularProductionCountryId() {
        return mostPopularProductionCountryId;
    }

    public void setMostPopularProductionCountryId(Long productionCountryId) {
        this.mostPopularProductionCountryId = productionCountryId;
    }

    public Long getMostPopularProductionCompanyId() {
        return mostPopularProductionCompanyId;
    }

    public void setMostPopularProductionCompanyId(Long productionCompanyId) {
        this.mostPopularProductionCompanyId = productionCompanyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieInsightsDTO)) {
            return false;
        }

        return id != null && id.equals(((MovieInsightsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieInsightsDTO{" +
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
            ", highestRatedMovieId=" + getHighestRatedMovieId() +
            ", lowestRatedMovieId=" + getLowestRatedMovieId() +
            ", highestBudgetMovieId=" + getHighestBudgetMovieId() +
            ", lowestBudgetMovieId=" + getLowestBudgetMovieId() +
            ", highestRevenueMovieId=" + getHighestRevenueMovieId() +
            ", lowestRevenueMovieId=" + getLowestRevenueMovieId() +
            ", mostPopularGenreId=" + getMostPopularGenreId() +
            ", mostPopularActorId=" + getMostPopularActorId() +
            ", mostPopularProducerId=" + getMostPopularProducerId() +
            ", mostPopularWriterId=" + getMostPopularWriterId() +
            ", mostPopularDirectorId=" + getMostPopularDirectorId() +
            ", mostPopularProductionCountryId=" + getMostPopularProductionCountryId() +
            ", mostPopularProductionCompanyId=" + getMostPopularProductionCompanyId() +
            "}";
    }
}
