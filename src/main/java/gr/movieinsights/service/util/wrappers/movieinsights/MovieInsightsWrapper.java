package gr.movieinsights.service.util.wrappers.movieinsights;

import gr.movieinsights.config.Constants;
import gr.movieinsights.domain.Genre;
import gr.movieinsights.domain.IdentifiedEntity;
import gr.movieinsights.domain.Movie;
import gr.movieinsights.domain.MovieInsights;
import gr.movieinsights.domain.MovieInsightsContainer;
import gr.movieinsights.domain.Person;
import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.domain.Vote;
import gr.movieinsights.models.MovieInsightsContainerWithYears;
import gr.movieinsights.service.enumeration.MovieInsightsCategory;
import gr.movieinsights.service.util.MovieInsightsProcessor;
import gr.movieinsights.service.util.Total;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.ActorWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.BaseWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.CompanyWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.CountryWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.DirectorWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.GenreWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.PersonWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.ProducerWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.WriterWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.YearWrapper;
import gr.movieinsights.util.CalculationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiPredicate;

@Entity
@DiscriminatorValue("00")
public abstract class MovieInsightsWrapper<C extends MovieInsightsContainer, B extends BaseWrapper<E>, E> extends MovieInsights implements IParameterizedMovieInsightsWrapper<C, B, E> {

    private transient final MovieInsightsProcessor processor;
    private transient final IParameterizedMovieInsightsWrapper<? extends MovieInsightsContainer, ? extends BaseWrapper<?>, ?> master;
    private transient final B source;

    private transient final boolean slave;
    private transient boolean isCalculated;

    private transient final HashMap<Long, ActorWrapper> actors;
    private transient final HashMap<Long, ProducerWrapper> producers;
    private transient final HashMap<Long, WriterWrapper> writers;
    private transient final HashMap<Long, DirectorWrapper> directors;
    private transient final HashMap<Long, CompanyWrapper> companies;
    private transient final HashMap<Long, CountryWrapper> countries;
    private transient final HashMap<Long, GenreWrapper> genres;

    private transient Total<Person> actorTotals = new Total<>();
    private transient Total<Person> directorTotals = new Total<>();
    private transient Total<Person> producerTotals = new Total<>();
    private transient Total<Person> writerTotals = new Total<>();
    private transient Total<ProductionCompany> companyTotals = new Total<>();
    private transient Total<ProductionCountry> countryTotals = new Total<>();
    private transient Total<Genre> genreTotals = new Total<>();

    private transient final HashMap<Integer, MovieInsightsYearWrapper> movieInsightsPerYearWrappers;

    private transient final Object budgetLock = new Serializable() {
    };
    private transient final Object revenueLock = new Serializable() {
    };
    private transient final Object voteLock = new Serializable() {
    };

    private transient double totalVoteAverage = 0D;

    public MovieInsightsWrapper(MovieInsightsProcessor processor, B source, IParameterizedMovieInsightsWrapper<? extends MovieInsightsContainer, ? extends BaseWrapper<?>, ?> master) {
        this.processor = processor;
        this.source = source;
        this.isCalculated = false;
        if ((this.master = master) != null) {
            this.slave = true;
            movieInsightsPerYearWrappers = null;
        } else {
            this.slave = false;
            movieInsightsPerYearWrappers = new HashMap<>();
        }
        this.actors = new HashMap<>();
        this.producers = new HashMap<>();
        this.directors = new HashMap<>();
        this.writers = new HashMap<>();
        this.companies = new HashMap<>();
        this.countries = new HashMap<>();
        this.genres = new HashMap<>();
    }

    protected MovieInsightsWrapper() {
        this.processor = null;
        this.source = null;
        this.isCalculated = true;
        this.master = null;
        this.slave = false;
        this.movieInsightsPerYearWrappers = null;
        this.actors = null;
        this.producers = null;
        this.directors = null;
        this.writers = null;
        this.companies = null;
        this.countries = null;
        this.genres = null;
    }

    public MovieInsightsWrapper(MovieInsightsProcessor processor, B source) {
        this(processor, source, null);
    }

    @Override
    public IParameterizedMovieInsightsWrapper<? extends MovieInsightsContainer, ? extends BaseWrapper<?>, ?> getMaster() {
        return master;
    }

    @Override
    public B getSource() {
        return source;
    }

    @Override
    public MovieInsightsCategory getCategory() {
        return source.category;
    }

    @Override
    public boolean isSlave() {
        return slave;
    }

    protected transient final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public MovieInsightsYearWrapper getMovieInsightsWrapperByYear(int year) {
        if (!slave && getCategory() != MovieInsightsCategory.PerYear && movieInsightsPerYearWrappers != null) {
            MovieInsightsYearWrapper movieInsights = new MovieInsightsYearWrapper(processor, new YearWrapper(year), this);
            MovieInsightsYearWrapper movieInsights2;
            synchronized (movieInsightsPerYearWrappers) {
                if ((movieInsights2 = movieInsightsPerYearWrappers.putIfAbsent(year, movieInsights)) == null) {
                    movieInsights2 = movieInsights;
                }
            }
            return movieInsights2;
        }
        return null;
    }

    private <W extends BaseWrapper<EW>, EW> Optional<W> getWrapperBasedOnComparator(Map<Long, W> wrapperMap, Comparator<? super W> comparator) {
        return wrapperMap
            .values()
            .parallelStream()
            .sorted(comparator)
            .filter(e -> (slave ? master.getCategory() : getCategory()) != e.category || e.object != (slave ? master.getSource().object : source.object))
            .findFirst();
    }

    @Override
    public Collection<MovieInsightsYearWrapper> getMovieInsightsPerYearWrappers() {
        if (movieInsightsPerYearWrappers != null)
            return movieInsightsPerYearWrappers.values();
        return null;
    }


    private void calculateInsights() {
        if (isCalculated)
            throw new UnsupportedOperationException("This Wrapper has been calculated and finalized.");

        CompletableFuture<Void> calculateGenresFuture = CompletableFuture.runAsync(() -> calculateChildInsights(genres, GenreWrapper.getComparator(), genreTotals));
        CompletableFuture<Void> calculateCompaniesFuture = CompletableFuture.runAsync(() -> calculateChildInsights(companies, CompanyWrapper.getComparator(), companyTotals));
        CompletableFuture<Void> calculateCountriesFuture = CompletableFuture.runAsync(() -> calculateChildInsights(countries, CountryWrapper.getComparator(), countryTotals));
        CompletableFuture<Void> calculateActorsFuture = CompletableFuture.runAsync(() -> calculateChildInsights(actors, PersonWrapper.getComparator(), actorTotals));
        CompletableFuture<Void> calculateProducersFuture = CompletableFuture.runAsync(() -> calculateChildInsights(producers, PersonWrapper.getComparator(), producerTotals));
        CompletableFuture<Void> calculateWritersFuture = CompletableFuture.runAsync(() -> calculateChildInsights(writers, PersonWrapper.getComparator(), writerTotals));
        CompletableFuture<Void> calculateDirectorsFuture = CompletableFuture.runAsync(() -> calculateChildInsights(directors, PersonWrapper.getComparator(), directorTotals));
        CompletableFuture<Void> calculateEverything = CompletableFuture.allOf(
            calculateGenresFuture,
            calculateCompaniesFuture,
            calculateCountriesFuture,
            calculateActorsFuture,
            calculateProducersFuture,
            calculateWritersFuture,
            calculateDirectorsFuture
        );
        try {
            calculateEverything.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        clearWrapperMap(genres);
        clearWrapperMap(companies);
        clearWrapperMap(countries);
        clearWrapperMap(actors);
        clearWrapperMap(producers);
        clearWrapperMap(writers);
        clearWrapperMap(directors);
    }

    private <T extends IdentifiedEntity, W extends BaseWrapper<T>> void submit(W wrapper, Map<Long, W> objMap, Movie movie, T obj) {
        W wrapper2;

        synchronized (objMap) {
            if ((wrapper2 = objMap.putIfAbsent(obj.getId(), wrapper)) == null) {
                wrapper2 = wrapper;
            }
        }
        wrapper2.movies.add(movie);
        wrapper2.count++;
    }

    private void submitBudget(Movie movie) {
        if (movie.getBudget() >= Constants.MINIMUM_BUDGET_THRESHOLD) {
            synchronized (budgetLock) {
                setTotalBudget(getTotalBudget() + movie.getBudget());
                setTotalBudgetMovies(getTotalBudgetMovies() + 1);

                if (getHighestBudgetMovie() == null) {
                    setHighestBudgetMovie(movie);
                } else {
                    if (getHighestBudgetMovie().getBudget() < movie.getBudget()) {
                        setHighestBudgetMovie(movie);
                    } else if (getHighestBudgetMovie().getBudget().equals(movie.getBudget()) && movie.getPopularity() > getHighestBudgetMovie().getPopularity()) {
                        setHighestBudgetMovie(movie);
                    }
                }

                if (getLowestBudgetMovie() == null) {
                    setLowestBudgetMovie(movie);
                } else {
                    if (getLowestBudgetMovie().getBudget() > movie.getBudget()) {
                        setLowestBudgetMovie(movie);
                    } else if (getLowestBudgetMovie().getBudget().equals(movie.getBudget()) && movie.getPopularity() > getLowestBudgetMovie().getPopularity()) {
                        setLowestBudgetMovie(movie);
                    }
                }
            }
        }
    }

    private void submitRevenue(Movie movie) {
        if (movie.getRevenue() >= Constants.MINIMUM_REVENUE_THRESHOLD) {
            synchronized (revenueLock) {
                setTotalRevenue(getTotalRevenue() + movie.getRevenue());
                setTotalRevenueMovies(getTotalRevenueMovies() + 1);

                if (getHighestRevenueMovie() == null) {
                    setHighestRevenueMovie(movie);
                } else {
                    if (getHighestRevenueMovie().getRevenue() < movie.getRevenue()) {
                        setHighestRevenueMovie(movie);
                    } else if (getHighestRevenueMovie().getRevenue().equals(movie.getRevenue()) && movie.getPopularity() > getHighestRevenueMovie().getPopularity()) {
                        setHighestRevenueMovie(movie);
                    }
                }

                if (getLowestRevenueMovie() == null) {
                    setLowestRevenueMovie(movie);
                } else {
                    if (getLowestRevenueMovie().getRevenue() > movie.getRevenue()) {
                        setLowestRevenueMovie(movie);
                    } else if (getLowestRevenueMovie().getRevenue().equals(movie.getRevenue()) && movie.getPopularity() > getLowestRevenueMovie().getPopularity()) {
                        setLowestRevenueMovie(movie);
                    }
                }
            }
        }
    }

    private void submitRating(Movie movie) {
        if (movie.getVote().getVotes() >= Constants.MINIMUM_VOTES_THRESHOLD) {
            synchronized (voteLock) {
                Vote vote = movie.getVote();
                totalVoteAverage += vote.getAverage();
                setTotalRatedMovies(getTotalRatedMovies() + 1);

                if (getHighestRatedMovie() == null) {
                    setHighestRatedMovie(movie);
                } else {
                    setHighestRatedMovie(
                        calculateVote(
                            getHighestRatedMovie().getVote().getAverage(),
                            getHighestRatedMovie(),
                            movie.getVote().getAverage(),
                            movie,
                            (current, challenger) -> current < challenger
                        )
                    );
                }

                if (getLowestRatedMovie() == null) {
                    setLowestRatedMovie(movie);
                } else {
                    setLowestRatedMovie(
                        calculateVote(
                            getLowestRatedMovie().getVote().getAverage(),
                            getLowestRatedMovie(),
                            movie.getVote().getAverage(),
                            movie,
                            (current, challenger) -> current > challenger
                        )
                    );
                }

                if (getHighestRatedLogarithmicMovie() == null) {
                    setHighestRatedLogarithmicMovie(movie);
                } else {
                    setHighestRatedLogarithmicMovie(
                        calculateVote(
                            CalculationUtils.calculateVoteLogarithmicScore(getHighestRatedLogarithmicMovie().getVote(), true),
                            getHighestRatedLogarithmicMovie(),
                            CalculationUtils.calculateVoteLogarithmicScore(movie.getVote(), true),
                            movie,
                            (current, challenger) -> current < challenger
                        )
                    );
                }
                setLowestRatedLogarithmicMovie(getLowestRatedMovie());
            }
        }
    }

    private void submitCompanies(Movie movie) {
        AtomicBoolean hasIncreased = new AtomicBoolean();
        movie.getCompanies().parallelStream().filter(Objects::nonNull).forEach(company -> {
            calculateTotals(companyTotals, hasIncreased);
            submit(new CompanyWrapper(company, processor.getMovieCount(company)), companies, movie, company);
        });
    }

    private void submitCountries(Movie movie) {
        AtomicBoolean hasIncreased = new AtomicBoolean();
        movie.getCountries().parallelStream().filter(Objects::nonNull).forEach(country -> {
            calculateTotals(countryTotals, hasIncreased);
            submit(new CountryWrapper(country, processor.getMovieCount(country)), countries, movie, country);
        });
    }

    private void submitCredits(Movie movie) {
        AtomicBoolean actorMoviesIncreased = new AtomicBoolean(false);
        AtomicBoolean directorMoviesIncreased = new AtomicBoolean(false);
        AtomicBoolean producerMoviesIncreased = new AtomicBoolean(false);
        AtomicBoolean writerMoviesIncreased = new AtomicBoolean(false);
        movie.getCredits().parallelStream().filter(Objects::nonNull).forEach(credit -> {
            Person person = credit.getPerson();
            switch (credit.getRole()) {
                case ACTOR -> {
                    calculateTotals(actorTotals, actorMoviesIncreased);
                    submit(new ActorWrapper(person), actors, movie, person);
                }
                case DIRECTOR -> {
                    calculateTotals(directorTotals, directorMoviesIncreased);
                    submit(new DirectorWrapper(person), directors, movie, person);
                }
                case WRITER -> {
                    calculateTotals(writerTotals, writerMoviesIncreased);
                    submit(new WriterWrapper(person), writers, movie, person);
                }
                case PRODUCER -> {
                    calculateTotals(producerTotals, producerMoviesIncreased);
                    submit(new ProducerWrapper(person), producers, movie, person);
                }
            }
        });
    }

    private void submitGenres(Movie movie) {
        AtomicBoolean hasIncreased = new AtomicBoolean(false);
        movie.getGenres().parallelStream().filter(Objects::nonNull).forEach(genre -> {
            calculateTotals(genreTotals, hasIncreased);
            submit(new GenreWrapper(genre, processor.getMovieCount(genre)), genres, movie, genre);
        });
    }

    @Override
    public void submitMovie(Movie movie) {
        if (isCalculated)
            throw new UnsupportedOperationException("This Wrapper has been calculated and finalized.");
        setTotalMovies(getTotalMovies() + 1);

        CompletableFuture<Void> submitRevenueFuture = CompletableFuture.runAsync(() -> submitRevenue(movie));
        CompletableFuture<Void> submitBudgetFuture = CompletableFuture.runAsync(() -> submitBudget(movie));
        CompletableFuture<Void> submitRatingFuture = CompletableFuture.runAsync(() -> submitRating(movie));
        CompletableFuture<Void> submitCreditFuture = CompletableFuture.runAsync(() -> submitCompanies(movie));
        CompletableFuture<Void> submitCompanyFuture = CompletableFuture.runAsync(() -> submitCountries(movie));
        CompletableFuture<Void> submitCountryFuture = CompletableFuture.runAsync(() -> submitGenres(movie));
        CompletableFuture<Void> submitGenreFuture = CompletableFuture.runAsync(() -> submitCredits(movie));
        CompletableFuture<Void> submitChildren = CompletableFuture.runAsync(() -> {
            IMovieInsightsWrapper dependentWrapper;
            if (!slave && movie.getReleaseDate() != null && (dependentWrapper = getMovieInsightsWrapperByYear(movie.getReleaseDate().getYear())) != null)
                dependentWrapper.submitMovie(movie);
        });
        CompletableFuture<Void> calculateEverything = CompletableFuture.allOf(
            submitRevenueFuture,
            submitBudgetFuture,
            submitCompanyFuture,
            submitCountryFuture,
            submitCreditFuture,
            submitGenreFuture,
            submitRatingFuture,
            submitChildren
        );
        try {
            calculateEverything.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void calculateTotals(final Total<?> total, final AtomicBoolean haveIncreased) {
        synchronized (total) {
            if (!haveIncreased.getAndSet(true)) {
                total.increaseMoviesWithEntities();
            }
            total.increaseEntitiesPerMovie();
        }
    }

    private <W extends BaseWrapper<WE>, WE> void calculateChildInsights(Map<Long, W> map, Comparator<? super W> comparator, Total<WE> totals) {

        Optional<W> mostPopularEntryResult = getWrapperBasedOnComparator(map, comparator.reversed());
        mostPopularEntryResult.ifPresent(totals::submitMostPopular);

        Optional<W> leastPopularEntryResult = getWrapperBasedOnComparator(map, comparator);
        leastPopularEntryResult.ifPresent(totals::submitLeastPopular);

        totals.setTotalEntities(map.size());
    }

    private Movie calculateVote(double scoreCurrent, Movie current, double scoreChallenging, Movie challenging, BiPredicate<Double, Double> scoreEvaluation) {
        if (scoreEvaluation.test(scoreCurrent, scoreChallenging)) {
            return challenging;
        } else if (scoreCurrent == scoreChallenging) {
            if (challenging.getVote().getVotes() > current.getVote().getVotes()) {
                return challenging;
            } else if (challenging.getVote().getVotes().equals(current.getVote().getVotes()) && challenging.getPopularity() > current.getPopularity()) {
                return challenging;
            }
        }
        return current;
    }

    protected abstract C getContainer();

    @Override
    public C build() {
        if (isCalculated)
            throw new UnsupportedOperationException("This Wrapper has been calculated and finalized.");
        calculateInsights();

        if (getTotalRevenueMovies() > 0) {
            setAverageRevenue((double) getTotalRevenue() / getTotalRevenueMovies());
        } else {
            setAverageRevenue(0D);
        }

        if (getTotalBudgetMovies() > 0) {
            setAverageBudget((double) getTotalBudget() / getTotalBudgetMovies());
        } else {
            setAverageBudget(0D);
        }

        if (getTotalRatedMovies() > 0) {
            setAverageRating(totalVoteAverage / getTotalRatedMovies());
        } else {
            setAverageRating(0D);
        }

        setTotalActors(actorTotals.getTotalEntities());
        setAverageActorCount(actorTotals.getAverageCount());
        setMostPopularActor(actorTotals.getMostPopularEntity());
        setMostPopularActorMovieCount(actorTotals.getMostPopularEntityMovieCount());
        setLeastPopularActor(actorTotals.getLeastPopularEntity());
        setLeastPopularActorMovieCount(actorTotals.getLeastPopularEntityMovieCount());
        actorTotals.clear();
        actorTotals = null;

        setTotalGenres(genreTotals.getTotalEntities());
        setAverageGenreCount(genreTotals.getAverageCount());
        setMostPopularGenre(genreTotals.getMostPopularEntity());
        setMostPopularGenreMovieCount(genreTotals.getMostPopularEntityMovieCount());
        setLeastPopularGenre(genreTotals.getLeastPopularEntity());
        setLeastPopularGenreMovieCount(genreTotals.getLeastPopularEntityMovieCount());
        genreTotals.clear();
        genreTotals = null;

        setTotalDirectors(directorTotals.getTotalEntities());
        setAverageDirectorCount(directorTotals.getAverageCount());
        setMostPopularDirector(directorTotals.getMostPopularEntity());
        setMostPopularDirectorMovieCount(directorTotals.getMostPopularEntityMovieCount());
        setLeastPopularDirector(directorTotals.getLeastPopularEntity());
        setLeastPopularDirectorMovieCount(directorTotals.getLeastPopularEntityMovieCount());
        directorTotals.clear();
        directorTotals = null;

        setTotalWriters(writerTotals.getTotalEntities());
        setAverageWriterCount(writerTotals.getAverageCount());
        setMostPopularWriter(writerTotals.getMostPopularEntity());
        setMostPopularWriterMovieCount(writerTotals.getMostPopularEntityMovieCount());
        setLeastPopularWriter(writerTotals.getLeastPopularEntity());
        setLeastPopularWriterMovieCount(writerTotals.getLeastPopularEntityMovieCount());
        writerTotals.clear();
        writerTotals = null;

        setTotalProducers(producerTotals.getTotalEntities());
        setAverageProducerCount(producerTotals.getAverageCount());
        setMostPopularProducer(producerTotals.getMostPopularEntity());
        setMostPopularProducerMovieCount(producerTotals.getMostPopularEntityMovieCount());
        setLeastPopularProducer(producerTotals.getLeastPopularEntity());
        setLeastPopularProducerMovieCount(producerTotals.getLeastPopularEntityMovieCount());
        producerTotals.clear();
        producerTotals = null;

        setTotalProductionCompanies(companyTotals.getTotalEntities());
        setAverageProductionCompanyCount(companyTotals.getAverageCount());
        setMostPopularProductionCompany(companyTotals.getMostPopularEntity());
        setMostPopularProductionCompanyMovieCount(companyTotals.getMostPopularEntityMovieCount());
        setLeastPopularProductionCompany(companyTotals.getLeastPopularEntity());
        setLeastPopularProductionCompanyMovieCount(companyTotals.getLeastPopularEntityMovieCount());
        companyTotals.clear();
        companyTotals = null;

        setTotalProductionCountries(countryTotals.getTotalEntities());
        setAverageProductionCountryCount(countryTotals.getAverageCount());
        setMostPopularProductionCountry(countryTotals.getMostPopularEntity());
        setMostPopularProductionCountryMovieCount(countryTotals.getMostPopularEntityMovieCount());
        setLeastPopularProductionCountry(countryTotals.getLeastPopularEntity());
        setLeastPopularProductionCountryMovieCount(countryTotals.getLeastPopularEntityMovieCount());
        countryTotals.clear();
        countryTotals = null;


        isCalculated = true;
        totalVoteAverage = 0;

        C movieInsightsContainer = getContainer();

        movieInsightsContainer.setMovieInsights(this);

        if (!slave && movieInsightsContainer instanceof MovieInsightsContainerWithYears) {
            CopyOnWriteArrayList<MovieInsightsYearWrapper> list = new CopyOnWriteArrayList<>(getMovieInsightsPerYearWrappers());
            list.stream()
                .map(MovieInsightsYearWrapper::build)
                .forEach(((MovieInsightsContainerWithYears) movieInsightsContainer)::addMovieInsightsPerYear);
        }
        return movieInsightsContainer;
    }

    private void clearWrapperMap(Map<Long, ? extends BaseWrapper<?>> map) {
        map.clear();
    }

}

