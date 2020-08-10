package gr.movieinsights.service.util.wrappers.movieinsights;

import gr.movieinsights.domain.Genre;
import gr.movieinsights.domain.Movie;
import gr.movieinsights.domain.MovieInsights;
import gr.movieinsights.domain.Person;
import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.service.enumeration.MovieInsightsCategory;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class BaseMovieInsightsWrapper {

    protected final BaseMovieInsightsWrapper master;
    protected final BaseWrapper<?> source;
    protected final MovieInsightsCategory category;
    protected final MovieInsights movieInsights;

    protected final boolean slave;

    protected long totalBudget = 0L;
    protected long highestBudget = 0L;
    protected long lowestBudget = Long.MAX_VALUE;
    protected int totalMoviesWithBudget = 0;

    protected long totalRevenue = 0L;
    protected long highestRevenue = 0L;
    protected long lowestRevenue = Long.MAX_VALUE;
    protected int totalMoviesWithRevenue = 0;

    protected long highestRatingVotes = 0L;
    protected double highestRating = 0D;
    protected long lowestRatingVotes = 0L;
    protected double lowestRating = Double.MAX_VALUE;
    protected double totalVoteAverage = 0D;
    protected int totalMoviesWithVoteAverage = 0;

    BaseMovieInsightsWrapper[] movieInsightsPerYear;
    ConcurrentMap<Integer, BaseMovieInsightsWrapper> movieInsightsWrapperConcurrentMap;
    Set<BaseMovieInsightsWrapper> movieInsightsWrapperSet;

    BaseMovieInsightsWrapper(BaseWrapper<?> source, MovieInsightsCategory category, BaseMovieInsightsWrapper master) {
        this.category = category;
        this.source = source;
        if ((this.master = master) != null) {
            this.slave = true;
        } else {
            this.slave = false;
            //movieInsightsPerYear = new MovieInsightsWrapper[3000];
            movieInsightsWrapperConcurrentMap = new ConcurrentHashMap<>();
        }
        movieInsights = createMovieInsights();
    }

    public BaseMovieInsightsWrapper(BaseWrapper<?> source, MovieInsightsCategory category) {
        this(source, category, null);
    }

    private MovieInsights createMovieInsights() {
        MovieInsights movieInsights = new MovieInsights();
        movieInsights.setAverageRating(0D);
        movieInsights.setAverageBudget(0D);
        movieInsights.setAverageRevenue(0D);
        movieInsights.setTotalMovies(0);
        movieInsights.setMostPopularGenreMovieCount(0);
        movieInsights.setMostPopularActorMovieCount(0);
        movieInsights.setMostPopularDirectorMovieCount(0);
        movieInsights.setMostPopularProducerMovieCount(0);
        movieInsights.setMostPopularWriterMovieCount(0);
        movieInsights.setMostPopularProductionCountryMovieCount(0);
        movieInsights.setMostPopularProductionCompanyMovieCount(0);
        return movieInsights;
    }

    public void addTotalBudget(Long budgetToAdd) {
        totalBudget+=budgetToAdd;
    }
    public void increaseTotalMoviesWithBudget() {
        totalMoviesWithBudget++;
    }

    public void addTotalRevenue(Long revenueToAdd) {
        totalRevenue+=revenueToAdd;
    }

    public void increaseTotalMoviesWithRevenue() {
        totalMoviesWithRevenue++;
    }

    public void addTotalVoteAverage(Double voteAverageToAdd) {
        totalVoteAverage+=voteAverageToAdd;
    }

    public void increaseTotalMoviesWithVoteAverage() {
        totalMoviesWithVoteAverage++;
    }

    public BaseMovieInsightsWrapper getMaster() {
        return master;
    }

    public BaseWrapper<?> getSource() {
        return source;
    }

    public MovieInsightsCategory getCategory() {
        return category;
    }

    public MovieInsights getMovieInsights() {
        return movieInsights;
    }

    public boolean isSlave() {
        return slave;
    }

    public BaseMovieInsightsWrapper[] getMovieInsightsPerYear() {
        return movieInsightsPerYear;
    }

    public ConcurrentMap<Integer, BaseMovieInsightsWrapper> getDependentMovieInsightWrappers() {
        return movieInsightsWrapperConcurrentMap;
    }

    public Set<BaseMovieInsightsWrapper> getMovieInsightsWrapperSet() {
        return movieInsightsWrapperSet;
    }

    public abstract BaseMovieInsightsWrapper getMovieInsightsWrapperByYear(int year);

    public void addTotalBudget(long totalBudget) {
        this.totalBudget += totalBudget;
    }

    public void setTotalBudget(long totalBudget) {
        this.totalBudget = totalBudget;
    }

    public void setHighestBudget(long highestBudget) {
        this.highestBudget = highestBudget;
    }

    public void setLowestBudget(long lowestBudget) {
        this.lowestBudget = lowestBudget;
    }

    public void setTotalMoviesWithBudget(int totalMoviesWithBudget) {
        this.totalMoviesWithBudget = totalMoviesWithBudget;
    }

    public void setTotalRevenue(long totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public void setHighestRevenue(long highestRevenue) {
        this.highestRevenue = highestRevenue;
    }

    public void setLowestRevenue(long lowestRevenue) {
        this.lowestRevenue = lowestRevenue;
    }

    public void setTotalMoviesWithRevenue(int totalMoviesWithRevenue) {
        this.totalMoviesWithRevenue = totalMoviesWithRevenue;
    }

    public void setHighestRating(double highestRating) {
        this.highestRating = highestRating;
    }

    public void setLowestRating(double lowestRating) {
        this.lowestRating = lowestRating;
    }

    public void setTotalVoteAverage(double totalVoteAverage) {
        this.totalVoteAverage = totalVoteAverage;
    }

    public void setTotalMoviesWithVoteAverage(int totalMoviesWithVoteAverage) {
        this.totalMoviesWithVoteAverage = totalMoviesWithVoteAverage;
    }

    public void setHighestRatingVotes(long highestRatingVotes) {
        this.highestRatingVotes = highestRatingVotes;
    }

    public void setLowestRatingVotes(long lowestRatingVotes) {
        this.lowestRatingVotes = lowestRatingVotes;
    }

    public Long getTotalBudget() {
        return totalBudget;
    }

    public Long getHighestBudget() {
        return highestBudget;
    }

    public long getLowestBudget() {
        return lowestBudget;
    }

    public Integer getTotalMoviesWithBudget() {
        return totalMoviesWithBudget;
    }

    public Long getTotalRevenue() {
        return totalRevenue;
    }

    public Long getHighestRevenue() {
        return highestRevenue;
    }

    public Long getLowestRevenue() {
        return lowestRevenue;
    }

    public Integer getTotalMoviesWithRevenue() {
        return totalMoviesWithRevenue;
    }

    public Double getHighestRating() {
        return highestRating;
    }

    public Long getHighestRatingVotes() {
        return highestRatingVotes;
    }

    public Long getLowestRatingVotes() {
        return lowestRatingVotes;
    }

    public Double getLowestRating() {
        return lowestRating;
    }

    public Double getTotalVoteAverage() {
        return totalVoteAverage;
    }

    public Integer getTotalMoviesWithVoteAverage() {
        return totalMoviesWithVoteAverage;
    }

    public void calculateInsightsForChildren() {
        calculateGenreInsights();
        calculateCompanyInsights();
        calculateCountryInsights();
        calculateActorInsights();
        calculateProducerInsights();
        calculateWriterInsights();
        calculateDirectorInsights();

        if (!slave && category != MovieInsightsCategory.PerYear) {
               /* movieInsightsWrapperSet = Arrays.stream(movieInsightsPerYear).parallel().filter(Objects::nonNull).collect(Collectors.toSet());
                movieInsightsPerYear = null;

                movieInsightsWrapperSet.parallelStream().filter(Objects::nonNull).forEach(MovieInsightsWrapper::calculateInsightsForChildren);*/
            movieInsightsWrapperConcurrentMap.values().parallelStream().filter(Objects::nonNull).forEach(BaseMovieInsightsWrapper::calculateInsightsForChildren);
        }

        if (totalMoviesWithRevenue > 0)
            movieInsights.setAverageRevenue((double) totalRevenue / totalMoviesWithRevenue);
        if (totalMoviesWithBudget > 0)
            movieInsights.setAverageBudget((double) totalBudget / totalMoviesWithBudget);
        if (totalMoviesWithVoteAverage > 0)
            movieInsights.setAverageRating(totalVoteAverage / totalMoviesWithVoteAverage);
    }

    protected abstract <T, W extends BaseWrapper<T>> void submit(W wrapper, Map<T, W> objMap, Movie movie, T obj);

    public abstract void submitActor(Person person, Movie movie);

    public abstract void submitDirector(Person person, Movie movie);

    public abstract void submitWriter(Person person, Movie movie);

    public abstract void submitProducer(Person person, Movie movie);

    public abstract void submitGenre(Genre person, Movie movie);

    public abstract void submitCompany(ProductionCompany person, Movie movie);

    public abstract void submitCountry(ProductionCountry person, Movie movie);

    public abstract void submitYear(Integer year, Movie movie);

    protected abstract void calculateGenreInsights();

    protected abstract void calculateCompanyInsights();

    protected abstract void calculateCountryInsights();

    protected abstract void calculateActorInsights();

    protected abstract void calculateProducerInsights();

    protected abstract void calculateWriterInsights();

    protected abstract void calculateDirectorInsights();
}

