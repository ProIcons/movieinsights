package gr.movieinsights.service.util;

import com.google.common.collect.Lists;
import gr.movieinsights.domain.Genre;
import gr.movieinsights.domain.Movie;
import gr.movieinsights.domain.MovieInsights;
import gr.movieinsights.domain.MovieInsightsContainer;
import gr.movieinsights.domain.MovieInsightsGeneral;
import gr.movieinsights.domain.MovieInsightsPerCompany;
import gr.movieinsights.domain.MovieInsightsPerCountry;
import gr.movieinsights.domain.MovieInsightsPerGenre;
import gr.movieinsights.domain.MovieInsightsPerPerson;
import gr.movieinsights.domain.MovieInsightsPerYear;
import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.domain.enumeration.CreditRole;
import gr.movieinsights.models.MovieInsightsContainerWithYears;
import gr.movieinsights.service.MovieInsightsService;
import gr.movieinsights.service.enumeration.MovieInsightsCategory;
import gr.movieinsights.service.util.wrappers.movieinsights.IMovieInsightsWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.IParameterizedMovieInsightsWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.MovieInsightsCompanyWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.MovieInsightsCountryWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.MovieInsightsGeneralWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.MovieInsightsGenreWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.MovieInsightsPersonWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.BaseWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.CompanyWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.CountryWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.GeneralWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.GenreWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.PersonWrapper;
import gr.movieinsights.util.DurationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static gr.movieinsights.service.enumeration.MovieInsightsCategory.*;

public class MovieInsightsProcessor {

    private Logger log = LoggerFactory.getLogger(MovieInsightsProcessor.class);

    private IMovieInsightsWrapper[][] creditsArray;
    private IMovieInsightsWrapper[] companiesArray;
    private IMovieInsightsWrapper[] countriesArray;
    private IMovieInsightsWrapper[] genresArray;
    private IMovieInsightsWrapper generalWrapper;

    private final MovieInsightsService.DTO dto;

    public long getMovieCount(Genre genre) {
        return dto.genreCountMap.get(genre.getId());
    }

    public long getMovieCount(ProductionCountry country) {
        return dto.productionCountryCountMap.get(country.getId());
    }

    public long getMovieCount(ProductionCompany company) {
        return dto.productionCompanyCountMap.get(company.getId());
    }

    public MovieInsightsProcessor(MovieInsightsService.DTO dto) {
        this.dto = dto;
    }

    private IMovieInsightsWrapper createMovieInsightsWrapper(BaseWrapper<?> wrapper) {
        if (wrapper instanceof PersonWrapper) {
            return new MovieInsightsPersonWrapper(this, (PersonWrapper) wrapper);
        } else if (wrapper instanceof GenreWrapper) {
            return new MovieInsightsGenreWrapper(this, (GenreWrapper) wrapper);
        } else if (wrapper instanceof CompanyWrapper) {
            return new MovieInsightsCompanyWrapper(this, (CompanyWrapper) wrapper);
        } else if (wrapper instanceof CountryWrapper) {
            return new MovieInsightsCountryWrapper(this, (CountryWrapper) wrapper);
        } else if (wrapper instanceof GeneralWrapper) {
            return new MovieInsightsGeneralWrapper(this, (GeneralWrapper) wrapper);
        }
        throw new RuntimeException("Invalid Wrapper");
    }

    public ProcessResult calculateMovieInsights() {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        creditsArray = new MovieInsightsPersonWrapper[dto.maxPersonId][CreditRole.getSize()];
        companiesArray = new MovieInsightsCompanyWrapper[dto.maxCompanyId];
        countriesArray = new MovieInsightsCountryWrapper[dto.maxCountryId];
        genresArray = new MovieInsightsGenreWrapper[dto.maxGenreId];
        generalWrapper = createMovieInsightsWrapper(new GeneralWrapper());


        log.info("\tStarting Movie Processing...");
        Instant movieProcessing = Instant.now();

        int moviesCount = dto.movies.size();
        AtomicInteger moviesProcessed = new AtomicInteger(0);
        Lists
            .partition(dto.movies, 1000)
            .forEach(Chunk -> {
                    Instant start = Instant.now();
                    Chunk
                        .parallelStream()
                        .forEach(this::calculateInsights);
                    int mp = moviesProcessed.addAndGet(Chunk.size());
                    log.debug("\t\tMovies Processed {}% -> {}", String.format("%6.2f", (float) 100 * mp / moviesCount), DurationUtils.getDurationInTimeFormat(start));
                }
            );

        dto.movies.clear();
        log.debug("\tMovie Processing finished in {}", DurationUtils.getDurationInTimeFormat(movieProcessing));

        dto.movies.clear();

        System.gc();
        return categorizeMovieInsightObjects();
    }

    private <C extends MovieInsightsContainer, B extends BaseWrapper<E>, E> C appendLists(IParameterizedMovieInsightsWrapper<C, B, E> wrapper, List<MovieInsights> movieInsights, List<MovieInsightsPerYear> movieInsightsPerYears) {
        C container = wrapper.build();
        movieInsights.add(container.getMovieInsights());
        if (container instanceof MovieInsightsContainerWithYears) {
            movieInsightsPerYears.addAll(((MovieInsightsContainerWithYears) container).getMovieInsightsPerYears());
            movieInsights.addAll(((MovieInsightsContainerWithYears) container).getMovieInsightsPerYears().stream().map(MovieInsightsPerYear::getMovieInsights).collect(Collectors.toList()));
        }
        return container;
    }

    private ProcessResult categorizeMovieInsightObjects() {
        log.info("\tCategorizing Data...");
        Instant startCategorizing = Instant.now();
        List<MovieInsights> movieInsights = new CopyOnWriteArrayList<>();
        List<MovieInsightsPerYear> movieInsightsPerYears = new CopyOnWriteArrayList<>();

        Instant entityCategorizing = Instant.now();
        List<MovieInsightsPerCompany> movieInsightsPerCompanies = Arrays
            .stream(companiesArray)
            .parallel()
            .filter(Objects::nonNull)
            .map((container) -> appendLists((MovieInsightsCompanyWrapper) container, movieInsights, movieInsightsPerYears))
            .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        log.debug("\t\tCategorized MovieInsightsPerCompanies in {}", DurationUtils.getDurationInTimeFormat(entityCategorizing));
        companiesArray = null;

        entityCategorizing = Instant.now();
        List<MovieInsightsPerCountry> movieInsightsPerCountries = Arrays
            .stream(countriesArray)
            .parallel()
            .filter(Objects::nonNull)
            .map((container) -> appendLists((MovieInsightsCountryWrapper) container, movieInsights, movieInsightsPerYears))
            .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        log.debug("\t\tCategorized MovieInsightsPerCountries in {}", DurationUtils.getDurationInTimeFormat(entityCategorizing));
        countriesArray = null;

        entityCategorizing = Instant.now();
        List<MovieInsightsPerGenre> movieInsightsPerGenres = Arrays
            .stream(genresArray)
            .parallel()
            .filter(Objects::nonNull)
            .map((container) -> appendLists((MovieInsightsGenreWrapper) container, movieInsights, movieInsightsPerYears))
            .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        log.debug("\t\tCategorized MovieInsightsPerGenre in {}", DurationUtils.getDurationInTimeFormat(entityCategorizing));
        genresArray = null;

        entityCategorizing = Instant.now();
        List<MovieInsightsPerPerson> movieInsightsPerPeople = Arrays
            .stream(creditsArray)
            .parallel()
            .flatMap(Arrays::stream)
            .filter(Objects::nonNull)
            .map((container) -> appendLists((MovieInsightsPersonWrapper) container, movieInsights, movieInsightsPerYears))
            .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        log.debug("\t\tCategorized MovieInsightsPerPeople in {}", DurationUtils.getDurationInTimeFormat(entityCategorizing));
        creditsArray = null;

        entityCategorizing = Instant.now();
        MovieInsightsGeneral movieInsightsGeneral = appendLists((MovieInsightsGeneralWrapper) generalWrapper, movieInsights, movieInsightsPerYears);
        log.debug("\t\tCategorized MovieInsightsGeneral in {}", DurationUtils.getDurationInTimeFormat(entityCategorizing));

        generalWrapper = null;
        log.info("\tCategorizing Finished in {}", DurationUtils.getDurationInTimeFormat(startCategorizing));
        return ProcessResult.of(movieInsightsPerYears, movieInsightsPerPeople, movieInsightsPerCompanies, movieInsightsPerCountries, movieInsightsPerGenres, movieInsightsGeneral, movieInsights);
    }


    private IMovieInsightsWrapper getMovieInsights(long id, BaseWrapper<?> o, MovieInsightsCategory category, IMovieInsightsWrapper[] array) {
        synchronized (array) {
            IMovieInsightsWrapper wrapper;
            if ((wrapper = array[(int) id]) == null) {
                array[(int) id] = wrapper = createMovieInsightsWrapper(o);
            }
            return wrapper;
        }
    }

    private IMovieInsightsWrapper getMovieInsights(long id, PersonWrapper o, IMovieInsightsWrapper[][] array) {
        synchronized (array) {
            IMovieInsightsWrapper wrapper;
            if ((wrapper = array[(int) id][o.role.getIndex()]) == null) {
                array[(int) id][o.role.getIndex()] = wrapper = createMovieInsightsWrapper(o);
            }
            return wrapper;
        }
    }

    private Set<IMovieInsightsWrapper> getMovieInsightObjectsOptimized(Movie movie) {
        List<IMovieInsightsWrapper> miSet = new CopyOnWriteArrayList<>();
        CompletableFuture[] tasks = new CompletableFuture[]{
            (CompletableFuture.runAsync(() -> movie
                .getCompanies()
                .parallelStream()
                .filter(Objects::nonNull)
                .forEach(company ->
                    miSet.add(
                        getMovieInsights(
                            company.getId(),
                            new CompanyWrapper(company, getMovieCount(company)),
                            PerCompany,
                            companiesArray
                        )
                    )
                ))
            ),

            (CompletableFuture.runAsync(() -> movie.getCountries().parallelStream().filter(Objects::nonNull).forEach(country ->
                miSet.add(getMovieInsights(country.getId(), new CountryWrapper(country, getMovieCount(country)), PerCountry, countriesArray))
            ))),

            (CompletableFuture.runAsync(() -> movie.getCredits().parallelStream().filter(Objects::nonNull).forEach((credit) ->
                miSet.add(getMovieInsights(credit.getPerson().getId(), new PersonWrapper(credit.getPerson(), credit.getRole()), creditsArray))
            ))),

            (CompletableFuture.runAsync(() -> movie.getGenres().parallelStream().filter(Objects::nonNull).forEach((genre) ->
                miSet.add(getMovieInsights(genre.getId(), new GenreWrapper(genre, getMovieCount(genre)), PerGenre, genresArray))
            )))
        };

        miSet.add(generalWrapper);
        try {
            CompletableFuture.allOf(tasks).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return new HashSet<>(miSet);
    }


    private void calculateInsights(Movie movie) {
        Set<IMovieInsightsWrapper> miSet = getMovieInsightObjectsOptimized(movie);
        miSet.parallelStream().forEach(wrapper -> {
            wrapper.submitMovie(movie);
        });
    }
}
