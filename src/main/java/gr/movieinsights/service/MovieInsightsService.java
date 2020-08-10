package gr.movieinsights.service;

import gr.movieinsights.config.Constants;
import gr.movieinsights.domain.Credit;
import gr.movieinsights.domain.Genre;
import gr.movieinsights.domain.Movie;
import gr.movieinsights.domain.MovieInsights;
import gr.movieinsights.domain.MovieInsightsPerCompany;
import gr.movieinsights.domain.MovieInsightsPerCountry;
import gr.movieinsights.domain.MovieInsightsPerGenre;
import gr.movieinsights.domain.MovieInsightsPerPerson;
import gr.movieinsights.domain.MovieInsightsPerYear;
import gr.movieinsights.domain.Person;
import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.repository.CreditRepository;
import gr.movieinsights.repository.GenreRepository;
import gr.movieinsights.repository.MovieInsightsPerCompanyRepository;
import gr.movieinsights.repository.MovieInsightsPerCountryRepository;
import gr.movieinsights.repository.MovieInsightsPerGenreRepository;
import gr.movieinsights.repository.MovieInsightsPerPersonRepository;
import gr.movieinsights.repository.MovieInsightsPerYearRepository;
import gr.movieinsights.repository.MovieInsightsRepository;
import gr.movieinsights.repository.MovieRepository;
import gr.movieinsights.repository.PersonRepository;
import gr.movieinsights.repository.ProductionCompanyRepository;
import gr.movieinsights.repository.ProductionCountryRepository;
import gr.movieinsights.repository.search.MovieInsightsSearchRepository;
import gr.movieinsights.service.dto.MovieInsightsDTO;
import gr.movieinsights.service.enumeration.MovieInsightsCategory;
import gr.movieinsights.service.mapper.MovieInsightsMapper;
import gr.movieinsights.service.util.wrappers.movieinsights.BaseMovieInsightsWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.BaseWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.CompanyWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.CountryWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.GenreWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.MovieInsightsWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.PersonWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.YearWrapper;
import gr.movieinsights.util.DurationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link MovieInsights}.
 */
@Service
@Transactional
public class MovieInsightsService {

    private final Logger log = LoggerFactory.getLogger(MovieInsightsService.class);

    private final MovieInsightsRepository movieInsightsRepository;

    private final MovieInsightsMapper movieInsightsMapper;

    private final MovieInsightsSearchRepository movieInsightsSearchRepository;

    private final MovieInsightsPerCountryRepository movieInsightsPerCountryRepository;
    private final MovieInsightsPerCompanyRepository movieInsightsPerCompanyRepository;
    private final MovieInsightsPerYearRepository movieInsightsPerYearRepository;
    private final MovieInsightsPerPersonRepository movieInsightsPerPersonRepository;
    private final MovieInsightsPerGenreRepository movieInsightsPerGenreRepository;

    private final MovieRepository movieRepository;
    private final PersonRepository personRepository;
    private final GenreRepository genreRepository;

    private final ProductionCompanyRepository productionCompanyRepository;
    private final ProductionCountryRepository productionCountryRepository;
    private final CreditRepository creditRepository;

    private MovieInsightsWrapper[] creditsArray;
    private MovieInsightsWrapper[] companiesArray;
    private MovieInsightsWrapper[] countriesArray;
    private MovieInsightsWrapper[] genresArray;
    private MovieInsightsWrapper[] yearsArray;

    public MovieInsightsService(MovieInsightsRepository movieInsightsRepository, MovieInsightsMapper movieInsightsMapper, MovieInsightsSearchRepository movieInsightsSearchRepository, MovieInsightsPerCountryRepository movieInsightsPerCountryRepository, MovieInsightsPerCompanyRepository movieInsightsPerCompanyRepository, MovieInsightsPerYearRepository movieInsightsPerYearRepository, MovieInsightsPerPersonRepository movieInsightsPerPersonRepository, MovieInsightsPerGenreRepository movieInsightsPerGenreRepository, MovieRepository movieRepository, PersonRepository personRepository, GenreRepository genreRepository, ProductionCompanyRepository productionCompanyRepository, ProductionCountryRepository productionCountryRepository, CreditRepository creditRepository) {
        this.movieInsightsRepository = movieInsightsRepository;
        this.movieInsightsMapper = movieInsightsMapper;
        this.movieInsightsSearchRepository = movieInsightsSearchRepository;
        this.movieInsightsPerCountryRepository = movieInsightsPerCountryRepository;
        this.movieInsightsPerCompanyRepository = movieInsightsPerCompanyRepository;
        this.movieInsightsPerYearRepository = movieInsightsPerYearRepository;
        this.movieInsightsPerPersonRepository = movieInsightsPerPersonRepository;
        this.movieInsightsPerGenreRepository = movieInsightsPerGenreRepository;
        this.movieRepository = movieRepository;
        this.personRepository = personRepository;
        this.genreRepository = genreRepository;
        this.productionCompanyRepository = productionCompanyRepository;
        this.productionCountryRepository = productionCountryRepository;
        this.creditRepository = creditRepository;
    }

    /**
     * Save a movieInsights.
     *
     * @param movieInsightsDTO the entity to save.
     * @return the persisted entity.
     */
    public MovieInsightsDTO save(MovieInsightsDTO movieInsightsDTO) {
        log.debug("Request to save MovieInsights : {}", movieInsightsDTO);
        MovieInsights movieInsights = movieInsightsMapper.toEntity(movieInsightsDTO);
        movieInsights = movieInsightsRepository.save(movieInsights);
        MovieInsightsDTO result = movieInsightsMapper.toDto(movieInsights);
        movieInsightsSearchRepository.save(movieInsights);
        return result;
    }

    /**
     * Get all the movieInsights.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MovieInsightsDTO> findAll() {
        log.debug("Request to get all MovieInsights");
        return movieInsightsRepository.findAll().stream()
            .map(movieInsightsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one movieInsights by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieInsightsDTO> findOne(Long id) {
        log.debug("Request to get MovieInsights : {}", id);
        return movieInsightsRepository.findById(id)
            .map(movieInsightsMapper::toDto);
    }

    /**
     * Delete the movieInsights by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MovieInsights : {}", id);
        movieInsightsRepository.deleteById(id);
        movieInsightsSearchRepository.deleteById(id);
    }

    /**
     * Search for the movieInsights corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MovieInsightsDTO> search(String query) {
        log.debug("Request to search MovieInsights for query {}", query);
        return StreamSupport
            .stream(movieInsightsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(movieInsightsMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public void calculateMovieInsights() {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        int personCount = (int) creditRepository.findMaxId() + 1;
        int companyCount = (int) productionCompanyRepository.findMaxId() + 1;
        int countryCount = (int) productionCountryRepository.findMaxId() + 1;
        int genreCount = (int) genreRepository.findMaxId() + 1;

        creditsArray = new MovieInsightsWrapper[personCount];
        companiesArray = new MovieInsightsWrapper[companyCount];
        countriesArray = new MovieInsightsWrapper[countryCount];
        genresArray = new MovieInsightsWrapper[genreCount];
        yearsArray = new MovieInsightsWrapper[2200];

        log.info("\tLoading Database into memory...");
        Instant fetchMoviesMetric = Instant.now();
        List<Movie> movies = movieRepository.findAllWithEagerRelationships();
        log.debug("\tFetched Movies in {}", DurationUtils.getDurationInTimeFormat(fetchMoviesMetric));
        Instant fetchPeopleMetric = Instant.now();
        List<Person> people = personRepository.findAllWithEagerRelationships();
        log.debug("\tFetched People in {}", DurationUtils.getDurationInTimeFormat(fetchPeopleMetric));
        Instant fetchCreditMetric = Instant.now();
        List<Credit> credits = creditRepository.findAllWithEagerRelationships();
        log.debug("\tFetched Credits in {}", DurationUtils.getDurationInTimeFormat(fetchCreditMetric));
        Instant fetchGenreMetric = Instant.now();
        List<Genre> genres = genreRepository.findAllWithEagerRelationships();
        log.debug("\tFetched Genres in {}", DurationUtils.getDurationInTimeFormat(fetchGenreMetric));
        Instant fetchCompanyMetric = Instant.now();
        List<ProductionCompany> companies = productionCompanyRepository.findAllWithEagerRelationships();
        log.debug("\tFetched Movies in {}", DurationUtils.getDurationInTimeFormat(fetchCompanyMetric));
        Instant fetchCountryMetric = Instant.now();
        List<ProductionCountry> countries = productionCountryRepository.findAllWithEagerRelationships();
        log.debug("\tFetched Countries in {}", DurationUtils.getDurationInTimeFormat(fetchCountryMetric));
        log.debug("Fetched Everything in {}", DurationUtils.getDurationInTimeFormat(fetchMoviesMetric));
        /*int movieCount = movies.size();*/
        log.info("\tStarting Movie Processing...");
        Instant movieProcessing = Instant.now();

        movies.parallelStream().forEach(this::calculateInsights);

        log.debug("Movie Processing finished in {}", DurationUtils.getDurationInTimeFormat(movieProcessing));
        Instant movieInsightsProcessing = Instant.now();
        log.info("\tCalculating Movie Insights...");

        final List<MovieInsightsPerYear> movieInsightsPerYear = new CopyOnWriteArrayList<>();
        final List<MovieInsightsPerCompany> movieInsightsPerCompanies = new CopyOnWriteArrayList<>();
        final List<MovieInsightsPerCountry> movieInsightsPerCountries = new CopyOnWriteArrayList<>();
        final List<MovieInsightsPerPerson> movieInsightsPerPeople = new CopyOnWriteArrayList<>();
        final List<MovieInsightsPerGenre> movieInsightsPerGenres = new CopyOnWriteArrayList<>();

        List<BaseMovieInsightsWrapper> movieInsightsWrappers = Arrays
            .asList(yearsArray, genresArray, creditsArray, companiesArray, countriesArray)
            .parallelStream()
            .flatMap(Arrays::stream)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        yearsArray = null;
        genresArray = null;
        creditsArray = null;
        companiesArray = null;
        countriesArray = null;


        movieInsightsWrappers.parallelStream().forEach(movieInsight -> {
            summarizeMovieInsightsData(movieInsight, movieInsightsPerYear,
                movieInsightsPerCompanies,
                movieInsightsPerCountries,
                movieInsightsPerPeople,
                movieInsightsPerGenres
            );
        });


        log.debug("Movie Insights Calculation finished in {}", DurationUtils.getDurationInTimeFormat(movieInsightsProcessing));

        movies.clear();

        log.debug("Gathering inner Movie Insights...");
        Instant movieInsightsGathering = Instant.now();
        List<MovieInsights> mi = movieInsightsWrappers.parallelStream().flatMap(p -> {
            List<MovieInsights> as = p.getDependentMovieInsightWrappers().values().parallelStream().map(BaseMovieInsightsWrapper::getMovieInsights).collect(Collectors.toList());
            as.add(p.getMovieInsights());
            return as.stream();
        }).collect(Collectors.toList());

        log.debug("Movie Insights Gathering finished in {}",DurationUtils.getDurationInTimeFormat(movieInsightsGathering));
        movieInsightsWrappers.clear();

        log.info("\tSaving data to database...");
        Instant saveInstant = Instant.now();
        Instant innerInstant = Instant.now();
        log.debug("\tSaving inner Movie Insight objects...");
        movieInsightsRepository.saveAll(mi);
        log.debug("\t\tSaved inner Movie Insight Object in {}ms", Duration.between(innerInstant, Instant.now()).toMillis());

        log.debug("\tSaving MovieInsightsPerCompanies...");
        Instant companyInstant = Instant.now();
        movieInsightsPerCompanyRepository.saveAll(movieInsightsPerCompanies);
        log.debug("\t\tSaved MovieInsightsPerCompanies in {}ms", Duration.between(companyInstant, Instant.now()).toMillis());

        log.info("\tSaving MovieInsightsPerGenres...");
        Instant genreInstant = Instant.now();
        movieInsightsPerGenreRepository.saveAll(movieInsightsPerGenres);
        log.debug("\t\tSaved MovieInsightsPerGenres in {}ms", Duration.between(genreInstant, Instant.now()).toMillis());

        log.debug("\tSaving MovieInsightsPerCountries...");
        Instant countryInstant = Instant.now();
        movieInsightsPerCountryRepository.saveAll(movieInsightsPerCountries);
        log.debug("\t\tSaved MovieInsightsPerCountries in {}ms", Duration.between(countryInstant, Instant.now()).toMillis());


        log.debug("\tSaving MovieInsightsPerPeople...");
        Instant creditsInstant = Instant.now();
        movieInsightsPerPersonRepository.saveAll(movieInsightsPerPeople);
        log.debug("\t\tSaved MovieInsightsPerPeople in {}ms", Duration.between(creditsInstant, Instant.now()).toMillis());

        log.debug("\tSaving MovieInsightsPerYears...");
        Instant yearsInstant = Instant.now();
        movieInsightsPerYearRepository.saveAll(movieInsightsPerYear);
        log.debug("\t\tSaved MovieInsightsPerYears in {}ms", Duration.between(yearsInstant, Instant.now()).toMillis());

        log.debug("Saved All MovieInsights in {}ms", formatter.format(new Date(Duration.between(saveInstant, Instant.now()).toMillis())));
    }



    private BaseMovieInsightsWrapper getMovieInsights(long id, BaseWrapper<?> o, MovieInsightsCategory category, BaseMovieInsightsWrapper[] array) {
        synchronized (array) {
            BaseMovieInsightsWrapper wrapper;
            if ((wrapper = array[(int) id]) == null) {
                array[(int) id] = wrapper = new MovieInsightsWrapper(o, category);
            }
            return wrapper;
        }
    }

    private Set<BaseMovieInsightsWrapper> getMovieInsightObjectsOptimized(Movie movie) {
        List<BaseMovieInsightsWrapper> miSet = new CopyOnWriteArrayList<>();
        CompletableFuture[] tasks = new CompletableFuture[]{
            (CompletableFuture.runAsync(() -> {
                if (movie.getReleaseDate() != null)
                    miSet.add(getMovieInsights(movie.getReleaseDate().getYear(), new YearWrapper(movie.getReleaseDate().getYear()), MovieInsightsCategory.PerYear, yearsArray));
            })),

            (CompletableFuture.runAsync(() -> {
                for (ProductionCompany company : movie.getCompanies()) {
                    if (company != null)
                        miSet.add(getMovieInsights(company.getId(), new CompanyWrapper(company), MovieInsightsCategory.PerCompany, companiesArray));
                }
            })),

            (CompletableFuture.runAsync(() -> {
                for (ProductionCountry country : movie.getCountries()) {
                    if (country != null)
                        miSet.add(getMovieInsights(country.getId(), new CountryWrapper(country), MovieInsightsCategory.PerCountry, countriesArray));
                }
            })),

            (CompletableFuture.runAsync(() -> {
                for (Credit credit : movie.getCredits()) {
                    if (credit != null) {
                        miSet.add(getMovieInsights(credit.getId(), new PersonWrapper(credit.getPerson(), credit.getRole()), MovieInsightsCategory.PerPerson, creditsArray));
                    }
                }
            })),

            (CompletableFuture.runAsync(() -> {
                for (Genre genre : movie.getGenres()) {
                    if (genre != null)
                        miSet.add(getMovieInsights(genre.getId(), new GenreWrapper(genre), MovieInsightsCategory.PerGenre, genresArray));
                }
            }))
        };

        try {
            CompletableFuture.allOf(tasks).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return new HashSet<>(miSet);
    }


    private MovieInsightsPerYear createDependendMovieInsightPerYear(BaseMovieInsightsWrapper wrapper) {
        MovieInsightsPerYear miyer = new MovieInsightsPerYear();
        miyer.setMovieInsights(wrapper.getMovieInsights());
        miyer.setYear((Integer) wrapper.getSource().object);
        return miyer;
    }

    private void summarizeMovieInsightsData(BaseMovieInsightsWrapper miw,
                                            List<MovieInsightsPerYear> q1,
                                            List<MovieInsightsPerCompany> q2,
                                            List<MovieInsightsPerCountry> q3,
                                            List<MovieInsightsPerPerson> q4,
                                            List<MovieInsightsPerGenre> q5) {
        miw.calculateInsightsForChildren();
        switch (miw.getCategory()) {
            case PerYear:
                MovieInsightsPerYear miyer = new MovieInsightsPerYear();
                miyer.setMovieInsights(miw.getMovieInsights());
                miyer.setYear((Integer) miw.getSource().object);
                q1.add(miyer);
                break;
            case PerPerson:
                MovieInsightsPerPerson miper = new MovieInsightsPerPerson();
                miper.setMovieInsights(miw.getMovieInsights());
                miper.setPerson((Person) miw.getSource().object);
                miper.setAs(((PersonWrapper) miw.getSource()).role);
                for (BaseMovieInsightsWrapper miwyer : miw.getDependentMovieInsightWrappers().values()) {
                    MovieInsightsPerYear dmipyer = createDependendMovieInsightPerYear(miwyer);
                    miper.addMovieInsightsPerYear(dmipyer);
                    q1.add(dmipyer);
                }
                q4.add(miper);
                break;
            case PerCompany:
                MovieInsightsPerCompany micmp = new MovieInsightsPerCompany();
                micmp.setMovieInsights(miw.getMovieInsights());
                micmp.setCompany((ProductionCompany) miw.getSource().object);
                for (BaseMovieInsightsWrapper miwyer : miw.getDependentMovieInsightWrappers().values()) {
                    MovieInsightsPerYear dmipyer = createDependendMovieInsightPerYear(miwyer);
                    micmp.addMovieInsightsPerYear(dmipyer);
                    q1.add(dmipyer);
                }
                q2.add(micmp);
                break;
            case PerCountry:
                MovieInsightsPerCountry micou = new MovieInsightsPerCountry();
                micou.setMovieInsights(miw.getMovieInsights());
                micou.setCountry((ProductionCountry) miw.getSource().object);
                for (BaseMovieInsightsWrapper miwyer : miw.getDependentMovieInsightWrappers().values()) {
                    MovieInsightsPerYear dmipyer = createDependendMovieInsightPerYear(miwyer);
                    micou.addMovieInsightsPerYear(dmipyer);
                    q1.add(dmipyer);
                }
                q3.add(micou);
                break;
            case PerGenre:
                MovieInsightsPerGenre migen = new MovieInsightsPerGenre();
                migen.setMovieInsights(miw.getMovieInsights());
                migen.setGenre((Genre) miw.getSource().object);
                for (BaseMovieInsightsWrapper miwyer : miw.getDependentMovieInsightWrappers().values()) {
                    MovieInsightsPerYear dmipyer = createDependendMovieInsightPerYear(miwyer);
                    migen.addMovieInsightsPerYear(dmipyer);
                    q1.add(dmipyer);
                }
                q5.add(migen);
                break;
        }
    }

    private void calculateSlaveInsights(BaseMovieInsightsWrapper wrapper, Movie movie, BiConsumer<BaseMovieInsightsWrapper, Movie> fn) {
        BaseMovieInsightsWrapper slaveWrapper;
        if (movie.getReleaseDate() != null && (slaveWrapper = wrapper.getMovieInsightsWrapperByYear(movie.getReleaseDate().getYear())) != null) {
            fn.accept(slaveWrapper, movie);
        }
    }

    private void calculateRevenueInsights(BaseMovieInsightsWrapper wrapper, Movie movie) {
        if (movie.getRevenue() >= Constants.MINIMUM_REVENUE_THRESHOLD) {
            wrapper.addTotalRevenue(movie.getRevenue());
            wrapper.increaseTotalMoviesWithRevenue();

            if (wrapper.getHighestRevenue() < movie.getRevenue()) {
                wrapper.setHighestRevenue(movie.getRevenue());
                wrapper.getMovieInsights().setHighestRevenueMovie(movie);
            } else if (wrapper.getHighestRevenue().equals(movie.getRevenue())) {
                if (movie.getPopularity() > wrapper.getMovieInsights().getHighestRevenueMovie().getPopularity()) {
                    wrapper.getMovieInsights().setHighestRevenueMovie(movie);
                }
            }
            if (wrapper.getLowestRevenue() > movie.getRevenue()) {
                wrapper.setLowestRevenue(movie.getRevenue());
                wrapper.getMovieInsights().setLowestRevenueMovie(movie);
            } else if (wrapper.getLowestRevenue().equals(movie.getRevenue())) {
                if (movie.getPopularity() > wrapper.getMovieInsights().getLowestRevenueMovie().getPopularity()) {
                    wrapper.getMovieInsights().setLowestRevenueMovie(movie);
                }
            }
        }
        calculateSlaveInsights(wrapper, movie, this::calculateRevenueInsights);
    }

    private void calculateBudgetInsights(BaseMovieInsightsWrapper wrapper, Movie movie) {
        if (movie.getBudget() >= Constants.MINIMUM_BUDGET_THRESHOLD) {
            wrapper.addTotalBudget(movie.getBudget());
            wrapper.increaseTotalMoviesWithBudget();

            if (wrapper.getHighestBudget() < movie.getBudget()) {
                wrapper.setHighestBudget(movie.getBudget());
                wrapper.getMovieInsights().setHighestBudgetMovie(movie);
            } else if (wrapper.getHighestBudget().equals(movie.getBudget())) {
                if (movie.getPopularity() > wrapper.getMovieInsights().getHighestBudgetMovie().getPopularity()) {
                    wrapper.getMovieInsights().setHighestBudgetMovie(movie);
                }
            }
            if (wrapper.getLowestBudget() > movie.getBudget()) {
                wrapper.setLowestBudget(movie.getBudget());
                wrapper.getMovieInsights().setLowestBudgetMovie(movie);
            } else if (wrapper.getLowestBudget() == movie.getBudget()) {
                if (movie.getPopularity() > wrapper.getMovieInsights().getLowestBudgetMovie().getPopularity()) {
                    wrapper.getMovieInsights().setLowestBudgetMovie(movie);
                }
            }
        }
        calculateSlaveInsights(wrapper, movie, this::calculateBudgetInsights);
    }

    private void calculateRatingInsights(BaseMovieInsightsWrapper wrapper, Movie movie) {
        if (movie.getVote().getVotes() >= Constants.MINIMUM_VOTES_THRESHOLD) {
            wrapper.addTotalVoteAverage(movie.getVote().getAverage());
            wrapper.increaseTotalMoviesWithVoteAverage();

            if (wrapper.getHighestRating() < movie.getVote().getAverage()) {
                wrapper.setHighestRating(movie.getVote().getAverage());
                wrapper.setHighestRatingVotes(movie.getVote().getVotes().longValue());
                wrapper.getMovieInsights().setHighestRatedMovie(movie);
            } else if (wrapper.getHighestRating().equals(movie.getVote().getAverage())) {
                if (movie.getVote().getVotes() > wrapper.getHighestRatingVotes()) {
                    wrapper.setHighestRatingVotes(movie.getVote().getVotes().longValue());
                    wrapper.getMovieInsights().setHighestRatedMovie(movie);
                } else if (movie.getVote().getVotes().longValue() == wrapper.getHighestRatingVotes() && movie.getPopularity() > wrapper.getMovieInsights().getHighestRatedMovie().getPopularity()) {
                    wrapper.getMovieInsights().setHighestRatedMovie(movie);
                }
            }

            if (wrapper.getLowestRating() > movie.getVote().getAverage()) {
                wrapper.setLowestRating(movie.getVote().getAverage());
                wrapper.getMovieInsights().setLowestRatedMovie(movie);
            } else if (wrapper.getLowestRating().equals(movie.getVote().getAverage())) {
                if (movie.getVote().getVotes() > wrapper.getLowestRatingVotes()) {
                    wrapper.setLowestRatingVotes(movie.getVote().getVotes().longValue());
                    wrapper.getMovieInsights().setLowestRatedMovie(movie);
                } else if (movie.getVote().getVotes().longValue() == wrapper.getLowestRatingVotes() && movie.getPopularity() > wrapper.getMovieInsights().getLowestRatedMovie().getPopularity()) {
                    wrapper.getMovieInsights().setLowestRatedMovie(movie);
                }
            }

        }
        calculateSlaveInsights(wrapper, movie, this::calculateRatingInsights);
    }

    private void calculateCreditInsights(BaseMovieInsightsWrapper wrapper, Movie movie) {
        movie.getCredits().parallelStream().forEach(credit -> {
            if (credit != null) {
                switch (credit.getRole()) {
                    case ACTOR:
                        wrapper.submitActor(credit.getPerson(), movie);
                        break;
                    case DIRECTOR:
                        wrapper.submitDirector(credit.getPerson(), movie);
                        break;
                    case WRITER:
                        wrapper.submitWriter(credit.getPerson(), movie);
                        break;
                    case PRODUCER:
                        wrapper.submitProducer(credit.getPerson(), movie);
                        break;
                }
            }
        });
        calculateSlaveInsights(wrapper, movie, this::calculateCreditInsights);
    }

    private void calculateCompanyInsights(BaseMovieInsightsWrapper wrapper, Movie movie) {
        movie.getCompanies().parallelStream().forEach(company -> {
            if (company != null) {
                wrapper.submitCompany(company, movie);
                //metric.addLoop();
            }
        });
        calculateSlaveInsights(wrapper, movie, this::calculateCompanyInsights);
    }

    private void calculateCountryInsights(BaseMovieInsightsWrapper wrapper, Movie movie) {
        movie.getCountries().parallelStream().forEach(country -> {
            if (country != null) {
                wrapper.submitCountry(country, movie);
            }
        });
        calculateSlaveInsights(wrapper, movie, this::calculateCountryInsights);
    }

    private void calculateGenreInsights(BaseMovieInsightsWrapper wrapper, Movie movie) {
        movie.getGenres().parallelStream().forEach(genre -> {
            if (genre != null) {
                wrapper.submitGenre(genre, movie);
            }
        });
        calculateSlaveInsights(wrapper, movie, this::calculateCompanyInsights);
    }

    private void calculateInsightsPerWrapper(BaseMovieInsightsWrapper wrapper, Movie movie) {
        wrapper.getMovieInsights().setTotalMovies(wrapper.getMovieInsights().getTotalMovies() + 1);

        CompletableFuture<Void> calculateRevenueFuture = CompletableFuture.runAsync(() -> calculateRevenueInsights(wrapper, movie));
        CompletableFuture<Void> calculateBudgetFuture = CompletableFuture.runAsync(() -> calculateBudgetInsights(wrapper, movie));
        CompletableFuture<Void> calculateRatingFuture = CompletableFuture.runAsync(() -> calculateRatingInsights(wrapper, movie));
        CompletableFuture<Void> calculateCreditFuture = CompletableFuture.runAsync(() -> calculateCreditInsights(wrapper, movie));
        CompletableFuture<Void> calculateCompanyFuture = CompletableFuture.runAsync(() -> calculateCompanyInsights(wrapper, movie));
        CompletableFuture<Void> calculateCountryFuture = CompletableFuture.runAsync(() -> calculateCountryInsights(wrapper, movie));
        CompletableFuture<Void> calculateGenreFuture = CompletableFuture.runAsync(() -> calculateGenreInsights(wrapper, movie));

        CompletableFuture<Void> calculateEverything = CompletableFuture.allOf(calculateRevenueFuture, calculateBudgetFuture, calculateCompanyFuture, calculateCountryFuture, calculateCreditFuture, calculateGenreFuture, calculateRatingFuture);
        try {
            calculateEverything.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private Boolean calculateInsights(Movie movie) {
        Set<BaseMovieInsightsWrapper> miSet = getMovieInsightObjectsOptimized(movie);
        miSet.parallelStream().forEach(wrapper -> {
            calculateInsightsPerWrapper(wrapper, movie);
        });
        return true;
    }

}
