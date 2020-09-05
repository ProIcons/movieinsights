package gr.movieinsights.service;

import gr.movieinsights.domain.Movie;
import gr.movieinsights.domain.MovieInsights;
import gr.movieinsights.domain.MovieInsightsContainer;
import gr.movieinsights.domain.MovieInsightsGeneral;
import gr.movieinsights.domain.MovieInsightsPerCompany;
import gr.movieinsights.domain.MovieInsightsPerCountry;
import gr.movieinsights.domain.MovieInsightsPerGenre;
import gr.movieinsights.domain.MovieInsightsPerPerson;
import gr.movieinsights.domain.MovieInsightsPerYear;
import gr.movieinsights.models.MovieInsightsContainerWithYears;
import gr.movieinsights.repository.CreditRepository;
import gr.movieinsights.repository.GenreRepository;
import gr.movieinsights.repository.MovieInsightsRepository;
import gr.movieinsights.repository.MovieRepository;
import gr.movieinsights.repository.PersonRepository;
import gr.movieinsights.repository.ProductionCompanyRepository;
import gr.movieinsights.repository.ProductionCountryRepository;
import gr.movieinsights.service.dto.movieinsights.MovieInsightsDTO;
import gr.movieinsights.service.mapper.movieinsights.MovieInsightsMapper;
import gr.movieinsights.service.util.BaseService;
import gr.movieinsights.service.util.MovieInsightsProcessor;
import gr.movieinsights.service.util.ProcessResult;
import gr.movieinsights.util.DurationUtils;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link MovieInsights}.
 */
@Service
@Transactional
public class MovieInsightsService extends BaseService<MovieInsights, MovieInsightsDTO, MovieInsightsRepository, MovieInsightsMapper> {
    private final MovieInsightsPerCountryService movieInsightsPerCountryService;
    private final MovieInsightsPerCompanyService movieInsightsPerCompanyService;
    private final MovieInsightsGeneralService movieInsightsGeneralService;
    private final MovieInsightsPerPersonService movieInsightsPerPersonService;
    private final MovieInsightsPerGenreService movieInsightsPerGenreService;
    private final MovieInsightsPerYearService movieInsightsPerYearService;

    private final MovieRepository movieRepository;
    private final PersonRepository personRepository;
    private final GenreRepository genreRepository;

    private final ProductionCompanyRepository productionCompanyRepository;
    private final ProductionCountryRepository productionCountryRepository;
    private final CreditRepository creditRepository;
    private final CacheManager cacheManager;

    public MovieInsightsService(MovieInsightsRepository movieInsightsRepository, MovieInsightsMapper movieInsightsMapper, MovieInsightsPerCountryService movieInsightsPerCountryService, MovieInsightsPerCompanyService movieInsightsPerCompanyService, MovieInsightsGeneralService movieInsightsGeneralService, MovieInsightsPerPersonService movieInsightsPerPersonService, MovieInsightsPerGenreService movieInsightsPerGenreService, MovieInsightsPerYearService movieInsightsPerYearService, MovieRepository movieRepository, PersonRepository personRepository, GenreRepository genreRepository, ProductionCompanyRepository productionCompanyRepository, ProductionCountryRepository productionCountryRepository, CreditRepository creditRepository, CacheManager cacheManager) {
        super(movieInsightsRepository, movieInsightsMapper);
        this.movieInsightsPerCountryService = movieInsightsPerCountryService;
        this.movieInsightsPerCompanyService = movieInsightsPerCompanyService;
        this.movieInsightsGeneralService = movieInsightsGeneralService;
        this.movieInsightsPerPersonService = movieInsightsPerPersonService;
        this.movieInsightsPerGenreService = movieInsightsPerGenreService;
        this.movieInsightsPerYearService = movieInsightsPerYearService;
        this.movieRepository = movieRepository;
        this.personRepository = personRepository;
        this.genreRepository = genreRepository;
        this.productionCompanyRepository = productionCompanyRepository;
        this.productionCountryRepository = productionCountryRepository;
        this.creditRepository = creditRepository;
        this.cacheManager = cacheManager;

    }

    @Override
    public void clear() {
        super.clear();
        movieInsightsPerYearService.clear();
        movieInsightsPerPersonService.clear();
        movieInsightsPerGenreService.clear();
        movieInsightsPerCompanyService.clear();
        movieInsightsPerCountryService.clear();
        movieInsightsGeneralService.clear();
    }

    @Transactional
    DTO fetchMovies() {
        log.info("\tLoading Database into memory...");
        Instant fetchMoviesMetric = Instant.now();
        List<Movie> movies = movieRepository.findAllWithEagerRelationships();
        log.debug("\t\tFetched Movies in {}", DurationUtils.getDurationInTimeFormat(fetchMoviesMetric));
        Instant fetchGenreMetric = Instant.now();
        Map<Long, Long> genres = genreRepository.findAllWithMovieCounts();
        log.debug("\t\tFetched Genres in {}", DurationUtils.getDurationInTimeFormat(fetchGenreMetric));
        Instant fetchCompanyMetric = Instant.now();
        Map<Long, Long> companies = productionCompanyRepository.findAllWithMovieCounts();
        log.debug("\t\tFetched Companies in {}", DurationUtils.getDurationInTimeFormat(fetchCompanyMetric));
        Instant fetchCountryMetric = Instant.now();
        Map<Long, Long> countries = productionCountryRepository.findAllWithMovieCounts();
        log.debug("\t\tFetched Countries in {}", DurationUtils.getDurationInTimeFormat(fetchCountryMetric));

        int personCount = (int) creditRepository.findMaxId() + 1;
        int companyCount = (int) productionCompanyRepository.findMaxId() + 1;
        int countryCount = (int) productionCountryRepository.findMaxId() + 1;
        int genreCount = (int) genreRepository.findMaxId() + 1;
        log.debug("\tFetched Everything in {}", DurationUtils.getDurationInTimeFormat(fetchMoviesMetric));

        return new DTO(movies, genres, companies, countries, personCount, companyCount, countryCount, genreCount);
    }

    public static class DTO {
        public final List<Movie> movies;
        public final Map<Long, Long> genreCountMap;
        public final Map<Long, Long> productionCompanyCountMap;
        public final Map<Long, Long> productionCountryCountMap;
        public final int maxPersonId;
        public final int maxCompanyId;
        public final int maxCountryId;
        public final int maxGenreId;

        public DTO(List<Movie> movies, Map<Long, Long> genreCountMap, Map<Long, Long> productionCompanyCountMap, Map<Long, Long> productionCountryCountMap, int maxPersonId, int maxCompanyId, int maxCountryId, int maxGenreId) {
            this.movies = movies;
            this.genreCountMap = genreCountMap;
            this.productionCompanyCountMap = productionCompanyCountMap;
            this.productionCountryCountMap = productionCountryCountMap;
            this.maxPersonId = maxPersonId;
            this.maxCompanyId = maxCompanyId;
            this.maxCountryId = maxCountryId;
            this.maxGenreId = maxGenreId;
        }
    }

    public void generateAndSaveMovieInsights() {
        DTO dto = fetchMovies();
        MovieInsightsProcessor movieInsightsProcessor = new MovieInsightsProcessor(dto);

        ProcessResult processResult = movieInsightsProcessor.calculateMovieInsights();

        log.info("\tSaving data to database...");
        Instant saveInstant = Instant.now();

        save(processResult);
        log.debug("\tSaved All MovieInsights in {}", DurationUtils.getDurationInTimeFormat(saveInstant));

        log.debug("\tTrying to free up memory...");
        log.debug("\t\tClearing cache...");
        log.debug("\t\tRunning Garbage Collector...");
        System.gc();
    }


    protected void save(ProcessResult processResult) {
        repository.saveAll(processResult.getMovieInsights());
        movieInsightsPerYearService.getRepository().saveAll(processResult.getMovieInsightsPerYear());
        movieInsightsPerCompanyService.getRepository().saveAll(processResult.getMovieInsightsPerCompany());
        movieInsightsPerGenreService.getRepository().saveAll(processResult.getMovieInsightsPerGenre());
        movieInsightsPerCountryService.getRepository().saveAll(processResult.getMovieInsightsPerCountry());
        movieInsightsPerPersonService.getRepository().saveAll(processResult.getMovieInsightsPerPerson());
        movieInsightsGeneralService.getRepository().save(processResult.getMovieInsightsGeneral());
    }

    /*public BaseRepository<?,Long> getRepository(Class<? extends BaseRepository<?,Long>> repoClass) {
        if (repoClass == MovieInsightsRepository.class) {
            return repository;
        } else if (repoClass == MovieInsightsGeneralRepository.class) {
            return (movieInsightsGeneralRepository;
        } else if (repoClass == MovieInsightsPerGenreRepository.class) {
            return movieInsightsPerGenreRepository;
        } else if (repoClass == MovieInsightsPerCompanyRepository.class) {
            return movieInsightsPerCompanyRepository;
        } else if (repoClass == MovieInsightsPerCountryRepository.class) {
            return movieInsightsPerCountryRepository;
        } else if (repoClass == MovieInsightsPerPersonRepository.class) {
            return movieInsightsPerPersonRepository;
        } else if (repoClass == MovieInsightsPerYearRepository.class) {
            return  movieInsightsPerYearRepository;
        }
        return null;
    }*/

   /* @Transactional
    protected void save3(Class<? extends BaseRepository<?,Long>> repoClass, List<?> entities) {
        getRepository(repoClass).saveAll(entities);
    }*/

    protected void save(List<MovieInsightsContainer> result) {
        /*Instant innerInstant = Instant.now();
        log.debug("\t\tPushing inner Movie Insight objects...");*/
        repository.saveAll(result.parallelStream().map(MovieInsightsContainer::getMovieInsights).collect(Collectors.toList()));
        repository.saveAll(result.parallelStream()
            .filter(movieInsightContainer -> movieInsightContainer instanceof MovieInsightsContainerWithYears)
            .map(movieInsightContainer -> ((MovieInsightsContainerWithYears) movieInsightContainer).getMovieInsightsPerYears())
            .flatMap(Collection::parallelStream)
            .map(MovieInsightsPerYear::getMovieInsights)
            .collect(Collectors.toList())
        );


        /*log.debug("\t\t\tSaved inner Movie Insight Object in {}ms", DurationUtils.getDurationInMillis(innerInstant));

        log.debug("\t\tPushing MovieInsightsPerCompanies...");*/
        /*Instant companyInstant = Instant.now();*/
        movieInsightsPerCompanyService.getRepository().saveAll(result.parallelStream()
            .filter(movieInsightsContainer -> movieInsightsContainer instanceof MovieInsightsPerCompany)
            .map(movieInsightsContainer -> (MovieInsightsPerCompany) movieInsightsContainer)
            .collect(Collectors.toList())
        );

        /*log.debug("\t\t\tSaved MovieInsightsPerCompanies in {}ms", DurationUtils.getDurationInMillis(companyInstant));

        log.info("\t\tPushing MovieInsightsPerGenres...");
        Instant genreInstant = Instant.now();*/
        movieInsightsPerGenreService.getRepository().saveAll(result.parallelStream()
            .filter(movieInsightsContainer -> movieInsightsContainer instanceof MovieInsightsPerGenre)
            .map(movieInsightsContainer -> (MovieInsightsPerGenre) movieInsightsContainer)
            .collect(Collectors.toList()));


        /*log.debug("\t\t\tSaved MovieInsightsPerGenres in {}ms", DurationUtils.getDurationInMillis(genreInstant));

        log.debug("\t\tPushing MovieInsightsPerCountries...");
        Instant countryInstant = Instant.now();*/
        movieInsightsPerCountryService.getRepository().saveAll(result.parallelStream()
            .filter(movieInsightsContainer -> movieInsightsContainer instanceof MovieInsightsPerCountry)
            .map(movieInsightsContainer -> (MovieInsightsPerCountry) movieInsightsContainer)
            .collect(Collectors.toList()));


        /*log.debug("\t\t\tSaved MovieInsightsPerCountries in {}ms", DurationUtils.getDurationInMillis(countryInstant));


        log.debug("\t\tPushing MovieInsightsPerPeople...");
        Instant creditsInstant = Instant.now();*/
        movieInsightsPerPersonService.getRepository().saveAll(result.parallelStream()
            .filter(movieInsightsContainer -> movieInsightsContainer instanceof MovieInsightsPerPerson)
            .map(movieInsightsContainer -> (MovieInsightsPerPerson) movieInsightsContainer)
            .collect(Collectors.toList()));


        /*log.debug("\t\t\tSaved MovieInsightsPerPeople in {}ms", DurationUtils.getDurationInMillis(creditsInstant));

        log.debug("\t\tPushing MovieInsightsGenerals...");
        Instant generalsInstant = Instant.now();*/
        movieInsightsGeneralService.getRepository().saveAll(result.parallelStream()
            .filter(movieInsightsContainer -> movieInsightsContainer instanceof MovieInsightsGeneral)
            .map(movieInsightsContainer -> (MovieInsightsGeneral) movieInsightsContainer)
            .collect(Collectors.toList()));

        /*log.debug("\t\t\tSaved MovieInsightsGenerals in {}ms", DurationUtils.getDurationInMillis(generalsInstant));


        log.debug("\t\tPushing MovieInsightsPerYears...");
        Instant yearsInstant = Instant.now();*/
        movieInsightsPerYearService.getRepository().saveAll(result.parallelStream()
            .filter(movieInsightContainer -> movieInsightContainer instanceof MovieInsightsContainerWithYears)
            .map(movieInsightContainer -> ((MovieInsightsContainerWithYears) movieInsightContainer).getMovieInsightsPerYears())
            .flatMap(Collection::parallelStream)
            .collect(Collectors.toList())
        );

        repository.flush();
        movieInsightsPerCompanyService.getRepository().flush();
        movieInsightsPerGenreService.getRepository().flush();
        movieInsightsPerCountryService.getRepository().flush();
        movieInsightsPerPersonService.getRepository().flush();
        movieInsightsGeneralService.getRepository().flush();
        movieInsightsPerYearService.getRepository().flush();
//        log.debug("\t\t\tSaved MovieInsightsPerYears in {}ms", Duration.between(yearsInstant, Instant.now()).toMillis());
    }
}
