package gr.movieinsights.service;

import com.google.common.collect.Lists;
import com.uwetrottmann.tmdb2.entities.BaseCompany;
import com.uwetrottmann.tmdb2.entities.BaseMember;
import com.uwetrottmann.tmdb2.entities.Country;
import com.uwetrottmann.tmdb2.entities.CrewMember;
import com.uwetrottmann.tmdb2.entities.Movie;
import gr.movieinsights.config.Constants;
import gr.movieinsights.config.tmdb.MovieInsightsTmdb;
import gr.movieinsights.domain.BannedEntity;
import gr.movieinsights.domain.Credit;
import gr.movieinsights.domain.Genre;
import gr.movieinsights.domain.Person;
import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.domain.Vote;
import gr.movieinsights.domain.enumeration.BanReason;
import gr.movieinsights.domain.enumeration.CreditField;
import gr.movieinsights.domain.enumeration.CreditRole;
import gr.movieinsights.domain.enumeration.TmdbEntityType;
import gr.movieinsights.models.CallWrapper;
import gr.movieinsights.models.ImdbEntity;
import gr.movieinsights.models.ImdbImportMovie;
import gr.movieinsights.models.ImdbImportRating;
import gr.movieinsights.models.TmdbEntity;
import gr.movieinsights.models.TmdbImportEntity;
import gr.movieinsights.models.enumeration.ResponseResult;
import gr.movieinsights.repository.BannedEntityRepository;
import gr.movieinsights.repository.CreditRepository;
import gr.movieinsights.repository.GenreRepository;
import gr.movieinsights.repository.MovieRepository;
import gr.movieinsights.repository.PersonRepository;
import gr.movieinsights.repository.ProductionCompanyRepository;
import gr.movieinsights.repository.ProductionCountryRepository;
import gr.movieinsights.repository.VoteRepository;
import gr.movieinsights.service.util.wrappers.dataimport.MovieImportWrapper;
import gr.movieinsights.service.util.wrappers.dataimport.ResponseWrapper;
import gr.movieinsights.util.DurationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class ImportService {
    private final Logger log = LoggerFactory.getLogger(ImportService.class);

    private final MovieInsightsTmdb tmdb;

    private final ImdbService imdbService;
    private final TmdbService tmdbService;

    private final ProductionCountryService productionCountryService;
    private final ProductionCompanyRepository companyRepository;
    private final ProductionCountryRepository countryRepository;
    private final PersonRepository personRepository;
    private final MovieRepository movieRepository;
    private final CreditRepository creditRepository;
    private final VoteRepository voteRepository;
    private final GenreRepository genreRepository;
    private final BannedEntityRepository bannedEntityRepository;

    public ImportService(MovieInsightsTmdb tmdb, ImdbService imdbService, TmdbService tmdbService, ProductionCountryService productionCountryService, ProductionCompanyRepository companyRepository, ProductionCountryRepository countryRepository, PersonRepository personRepository, MovieRepository movieRepository, CreditRepository creditRepository, VoteRepository voteRepository, GenreRepository genreRepository, BannedEntityRepository bannedEntityRepository) {
        this.tmdb = tmdb;
        this.tmdbService = tmdbService;
        this.imdbService = imdbService;
        this.productionCountryService = productionCountryService;
        this.companyRepository = companyRepository;
        this.countryRepository = countryRepository;
        this.personRepository = personRepository;
        this.movieRepository = movieRepository;
        this.creditRepository = creditRepository;
        this.voteRepository = voteRepository;
        this.genreRepository = genreRepository;
        this.bannedEntityRepository = bannedEntityRepository;
    }

    private void fetchData(boolean initialFetch) {
        TmdbImportEntity tmdbImportEntity = tmdbService.getTmdbDailyExportFile(TmdbEntityType.MOVIE);

        CopyOnWriteArrayList<CallWrapper<Movie>> movieCalls = new CopyOnWriteArrayList<>();
        tmdbImportEntity.getResults().parallelStream().forEach(r -> movieCalls.add(CallWrapper.of(tmdbService.getMovieCallByTmdbId(r.getId()), TmdbEntityType.MOVIE, r.getId().intValue())));

        DataImporter dataImporter = new DataImporter(initialFetch, movieCalls, imdbService::getVoteForMovie);
        dataImporter.fetchData();
    }

    private void fetchPartialData(boolean initialFetch) {
        Map<String, ImdbImportRating> ratings = imdbService.getRatings();
        Map<String, ImdbImportMovie> titles = imdbService
            .getTitles()
            .entrySet()
            .parallelStream()
            .filter(m -> m.getValue().getTitleType().equals("movie"))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        CopyOnWriteArrayList<CallWrapper<Movie>> movieCalls = new CopyOnWriteArrayList<>();

        ratings
            .values()
            .parallelStream()
            .filter(f -> titles.containsKey(f.getTconst()) && f.getNumVotes() >= Constants.MINIMUM_VOTES_THRESHOLD)
            .sorted(Comparator.comparing(ImdbImportRating::getTconst).reversed())
            .forEach(r -> movieCalls.add(CallWrapper.of(tmdbService.getMovieCallByImdbId(r.getTconst()), TmdbEntityType.MOVIE, r.getTconst())));

        DataImporter dataImporter = new DataImporter(initialFetch, movieCalls, imdbService::getVoteForMovie);
        dataImporter.fetchData();
    }

    public void initializeDemoDatabase() {
        initializeDemoDatabase(false);
    }

    public void initializeDemoDatabase(boolean force) {
        if (force) {
        } else if (movieRepository.count() > 0) {
            log.warn("Initialize Demo Database: Database has already been initialized -- ignoring.");
            return;
        }

        fetchPartialData(true);
    }

    public void updateDemoDatabase() {
        if (movieRepository.count() == 0) {
            log.warn("Update Demo Database: Database hasn't been initialized -- ignoring");
            return;
        }
        fetchPartialData(false);
    }

    public void initializeDatabase() {
        initializeDatabase(false);
    }

    public void initializeDatabase(boolean force) {
        if (force) {
            //TODO: clear database
        } else if (movieRepository.count() > 0) {
            log.warn("Initialize Database: Database has already been initialized -- ignoring.");
            return;
        }

        fetchData(true);
    }

    public void updateDatabase() {
        if (movieRepository.count() == 0) {
            log.warn("Update Database: Database hasn't been initialized -- ignoring");
            return;
        }
        fetchData(false);
    }


    private class DataImporter {
        List<Long> movieTmdbIdList;
        List<String> movieImdbIdList;
        List<Long> companyIdList;
        List<String> countryIdList;
        List<Long> personTmdbIdList;
        List<String> personImdbIdList;
        List<Long> genreIdList;

        List<Person> persistentPeople;
        List<Person> pendingPeople;
        List<Genre> persistentGenres;
        List<ProductionCompany> persistentCompanies;
        List<ProductionCountry> persistentCountries;

        List<gr.movieinsights.domain.Movie> pendingMovies;
        List<Vote> pendingVotes;
        List<Credit> pendingCredits;
        Map<Long, ProductionCompany> pendingCompanies;
        Map<String, ProductionCountry> pendingCountries;
        Map<Long, Genre> pendingGenres;

        List<BannedEntity> bannedEntities;

        Function<com.uwetrottmann.tmdb2.entities.Movie, Vote> voteSupplier;

        List<CallWrapper<com.uwetrottmann.tmdb2.entities.Movie>> movieCalls;
        List<CallWrapper<com.uwetrottmann.tmdb2.entities.Person>> personCalls;

        DataImporter(Boolean initialFetch, CopyOnWriteArrayList<CallWrapper<com.uwetrottmann.tmdb2.entities.Movie>> movieCalls, Function<com.uwetrottmann.tmdb2.entities.Movie, Vote> voteSupplier) {
            this.movieCalls = movieCalls;
            this.voteSupplier = voteSupplier;
            this.personCalls = new CopyOnWriteArrayList<>();

            this.pendingMovies = new CopyOnWriteArrayList<>();
            this.pendingCredits = new CopyOnWriteArrayList<>();
            this.pendingCompanies = new ConcurrentHashMap<>();
            this.pendingGenres = new ConcurrentHashMap<>();
            this.pendingPeople = new CopyOnWriteArrayList<>();
            this.pendingCountries = new ConcurrentHashMap<>();
            this.pendingVotes = new CopyOnWriteArrayList<>();
            if (initialFetch) {
                movieTmdbIdList = new ArrayList<>();
                movieImdbIdList = new ArrayList<>();
                persistentCompanies = new ArrayList<>();
                companyIdList = new ArrayList<>();
                persistentCountries = new ArrayList<>();
                countryIdList = new ArrayList<>();
                persistentPeople = new ArrayList<>();
                personTmdbIdList = new ArrayList<>();
                persistentGenres = new ArrayList<>();
                genreIdList = new ArrayList<>();

                bannedEntities = new CopyOnWriteArrayList<>();
            } else {
                movieTmdbIdList = movieRepository.getAllTmdbIds();
                movieImdbIdList = movieRepository.getAllImdbIds();
                persistentCompanies = companyRepository.findAll();
                companyIdList = persistentCompanies.parallelStream().map(ProductionCompany::getTmdbId).collect(Collectors.toList());
                persistentCountries = countryRepository.findAll();
                countryIdList = persistentCountries.parallelStream().map(ProductionCountry::getIso31661).collect(Collectors.toList());
                persistentPeople = personRepository.findAll();
                personTmdbIdList = persistentPeople.parallelStream().map(Person::getTmdbId).collect(Collectors.toList());
                personImdbIdList = persistentPeople.parallelStream().map(Person::getImdbId).filter(Objects::nonNull).collect(Collectors.toList());
                persistentGenres = genreRepository.findAll();
                genreIdList = persistentGenres.parallelStream().map(Genre::getTmdbId).collect(Collectors.toList());
                bannedEntities = new CopyOnWriteArrayList<>(bannedEntityRepository.findAll());
            }

        }

        @Transactional
        public void fetchData() {
            log.info("\tImporting Data for {} Movies", movieCalls.size());

            log.debug("\tFetching & Processing {} Movies...", movieCalls.size());
            Instant fetchMoviesStart = Instant.now();
            fetchMovies(Lists.partition(movieCalls, tmdb.getMaximumRequestsPerCycle()));

            log.debug("\t\tMovies Fetched in {}", DurationUtils.getDurationInTimeFormat(fetchMoviesStart));
            movieCalls.clear();
            log.debug("\tFetching & Processing {} People...", personCalls.size());
            Instant fetchPeopleStart = Instant.now();
            fetchPeople(
                Lists.partition(
                    personCalls
                        .parallelStream()
                        .collect(Collectors.toMap(CallWrapper<com.uwetrottmann.tmdb2.entities.Person>::getIntKey, CallWrapper<com.uwetrottmann.tmdb2.entities.Person>::getCall, (p1, p2) -> p1))
                        .entrySet()
                        .parallelStream()
                        .map(m -> new CallWrapper<>(m.getValue(), TmdbEntityType.PERSON, m.getKey()))
                        .collect(Collectors.toList()),
                    tmdb.getMaximumRequestsPerCycle()));

            log.debug("\t\tPeople Fetched in {}", DurationUtils.getDurationInTimeFormat(fetchPeopleStart));
            personCalls.clear();


            Instant saveInstant;
            Instant saveEntityInstant = saveInstant = Instant.now();
            log.debug("\tSaving {} Votes...", pendingVotes.size());
            voteRepository.saveAll(pendingVotes);
            log.debug("\t\tSaved in {}ms", DurationUtils.getDurationInMillis(saveEntityInstant));
            saveEntityInstant = Instant.now();

            log.debug("\tSaving {} People...", pendingPeople.size());
            personRepository.saveAll(pendingPeople);
            log.debug("\t\tSaved in {}ms", DurationUtils.getDurationInMillis(saveEntityInstant));
            saveEntityInstant = Instant.now();

            log.debug("\tSaving {} Companies...", pendingCompanies.size());
            companyRepository.saveAll(pendingCompanies.values());
            log.debug("\t\tSaved in {}ms", DurationUtils.getDurationInMillis(saveEntityInstant));
            saveEntityInstant = Instant.now();

            log.debug("\tSaving {} Countries...", pendingCountries.size());
            countryRepository.saveAll(pendingCountries.values());
            log.debug("\t\tSaved in {}ms", DurationUtils.getDurationInMillis(saveEntityInstant));
            saveEntityInstant = Instant.now();

            log.debug("\tSaving {} Genres...", pendingCountries.size());
            genreRepository.saveAll(pendingGenres.values());
            log.debug("\t\tSaved in {}ms", DurationUtils.getDurationInMillis(saveEntityInstant));
            saveEntityInstant = Instant.now();

            log.debug("\tSaving {} Movies...", pendingMovies.size());
            movieRepository.saveAll(pendingMovies);
            log.debug("\t\tSaved in {}ms", DurationUtils.getDurationInMillis(saveEntityInstant));
            saveEntityInstant = Instant.now();

            log.debug("\tSaving {} Credits...", pendingCredits.size());
            creditRepository.saveAll(pendingCredits);
            log.debug("\t\tSaved in {}ms", DurationUtils.getDurationInMillis(saveEntityInstant));
            saveEntityInstant = Instant.now();

            log.debug("\tSaving Banned Entities...");
            bannedEntityRepository.saveAll(bannedEntities);
            log.debug("\t\tSaved in {}ms", DurationUtils.getDurationInMillis(saveEntityInstant));

            log.debug("\tSaved all entities in {}", DurationUtils.getDurationInTimeFormat(saveInstant));


            log.info("\tImport finished in {}", DurationUtils.getDurationInTimeFormat(fetchMoviesStart));

            productionCountryService.clearCache();
        }

        private void fetchMovies(List<List<CallWrapper<com.uwetrottmann.tmdb2.entities.Movie>>> movieCallsPartitionedLists) {
            List<MovieImportWrapper> movieWrapperList = new ArrayList<>();
            for (List<CallWrapper<com.uwetrottmann.tmdb2.entities.Movie>> movieCallList : movieCallsPartitionedLists) {
                movieWrapperList.addAll(movieCallList
                    .parallelStream()
                    .map(call -> {
                        ResponseWrapper<com.uwetrottmann.tmdb2.entities.Movie> tmdbMovieResponse = movieResponseProcessor(call);
                        if (tmdbMovieResponse.getResult() == ResponseResult.SUCCESS && tmdbMovieResponse.getResponse().isSuccessful()) {
                            com.uwetrottmann.tmdb2.entities.Movie tmdbMovie = tmdbMovieResponse.getResponse().body();
                            return processTmdbMovie(tmdbMovie);
                        } else if (tmdbMovieResponse.getResult() == ResponseResult.SUCCESS && !tmdbMovieResponse.getResponse().isSuccessful()) {
                            banEntity(call, BanReason.NOTFOUND);

                        } else if (tmdbMovieResponse.getResult() == ResponseResult.EXCEPTION) {
                            banEntity(call, BanReason.UNDEFINED);
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
            }
            pendingMovies.addAll(movieWrapperList.parallelStream().map(MovieImportWrapper::build).collect(Collectors.toList()));
            movieWrapperList.clear();
        }

        private ResponseWrapper<Movie> movieResponseProcessor(CallWrapper<com.uwetrottmann.tmdb2.entities.Movie> c) {
            try {
                if (c.stringKey != null) {
                    if (movieImdbIdList.contains(c.stringKey)) {
                        return ResponseWrapper.Of(null, ResponseResult.ENTITY_ALREADY_EXISTS);
                    }
                    if (isBanned(c.stringKey, c.type)) {
                        return ResponseWrapper.Of(null, ResponseResult.ENTITY_IS_BANNED);
                    }
                } else if (c.intKey != null) {
                    if (movieTmdbIdList.contains(c.intKey.longValue())) {
                        return ResponseWrapper.Of(null, ResponseResult.ENTITY_ALREADY_EXISTS);
                    }
                    if (isBanned(c.intKey.longValue(), c.type)) {
                        return ResponseWrapper.Of(null, ResponseResult.ENTITY_IS_BANNED);
                    }
                }

                return ResponseWrapper.Of(c.call.execute(), ResponseResult.SUCCESS);
            } catch (IOException e) {
                return ResponseWrapper.Of(null, ResponseResult.EXCEPTION);
            }
        }

        private MovieImportWrapper processTmdbMovie(com.uwetrottmann.tmdb2.entities.Movie tmdbMovie) {
            if (tmdbMovie == null || tmdbMovie.id == null)
                return null;


            //TODO: Review - This is not necessary since we can still calculate credit's appearances on these movies. After all that's how popularity is described atm.

            /*int failures = 0;

            if (tmdbMovie.budget == null || tmdbMovie.budget == 0) {
                failures++;
            }
            if (tmdbMovie.release_date == null) {
                failures++;
            }
            if (tmdbMovie.revenue == null || tmdbMovie.revenue == 0) {
                failures++;
            }
            if (tmdbMovie.vote_average == null || tmdbMovie.vote_average == 0) {
                failures++;
            }

            if (failures == 4) {
                banMovie(tmdbMovie.imdb_id, tmdbMovie.id.longValue(), BanReason.NOUSABLEDATA);
                return null;
            }*/

            Vote vote = voteSupplier.apply(tmdbMovie);
            pendingVotes.add(vote);

            gr.movieinsights.domain.Movie miMovie = new gr.movieinsights.domain.Movie();
            MovieImportWrapper movieWrapper = new MovieImportWrapper(pendingGenres, pendingCompanies, pendingCountries, miMovie);
            miMovie.setBackdropPath(tmdbMovie.backdrop_path);
            miMovie.setBudget(tmdbMovie.budget == null ? 0 : tmdbMovie.budget);
            miMovie.setDescription(tmdbMovie.overview);
            miMovie.setImdbId(tmdbMovie.imdb_id);
            miMovie.setRevenue(tmdbMovie.revenue == null ? 0 : tmdbMovie.revenue);
            miMovie.setRuntime(tmdbMovie.runtime);
            miMovie.setPosterPath(tmdbMovie.poster_path);
            miMovie.setTagline(tmdbMovie.tagline);
            miMovie.setTmdbId(Long.valueOf(tmdbMovie.id));
            miMovie.setPopularity(tmdbMovie.popularity);
            if (tmdbMovie.release_date != null)
                miMovie.setReleaseDate(tmdbMovie.release_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            miMovie.setName(tmdbMovie.title);
            miMovie.setVote(vote);

            if (tmdbMovie.credits != null) {

                List<BaseMember> credits = new ArrayList<>();
                if (tmdbMovie.credits.crew != null)
                    credits.addAll(tmdbMovie.credits.crew);
                if (tmdbMovie.credits.cast != null)
                    credits.addAll(tmdbMovie.credits.cast);

                credits.parallelStream().forEach(tmdbCredit -> {
                    if (tmdbCredit == null || tmdbCredit.id == null)
                        return;

                    Credit credit = createCredit(miMovie, tmdbMovie, tmdbCredit);
                    if (credit != null) {
                        //miMovie.addCredits(credit);
                        personCalls.add(new CallWrapper<>(tmdbService.getPersonCallByTmdbId(tmdbCredit.id), TmdbEntityType.PERSON, tmdbCredit.id));
                    }
                });
            }

            if (tmdbMovie.genres != null) {
                for (com.uwetrottmann.tmdb2.entities.Genre tmdbGenre : tmdbMovie.genres) {
                    if (tmdbGenre == null || tmdbGenre.id == null)
                        continue;

                    Genre genre = getOrCreateGenre(tmdbGenre);
                    movieWrapper.addGenre(genre);
                }
            }

            if (tmdbMovie.production_companies != null) {
                for (BaseCompany tmdbCompany : tmdbMovie.production_companies) {
                    if (tmdbCompany == null || tmdbCompany.id == null)
                        continue;
                    ProductionCompany company = getOrCreateCompany(tmdbCompany);
                    movieWrapper.addCompany(company);
                }
            }

            if (tmdbMovie.production_countries != null) {
                for (Country tmdbCountry : tmdbMovie.production_countries) {
                    if (tmdbCountry == null || tmdbCountry.iso_3166_1 == null)
                        continue;
                    ProductionCountry country = getOrCreateCountry(tmdbCountry);
                    movieWrapper.addCountry(country);
                }
            }
            return movieWrapper;
        }

        private void fetchPeople(List<List<CallWrapper<com.uwetrottmann.tmdb2.entities.Person>>> peopleCallsPartitionedLists) {
            for (List<CallWrapper<com.uwetrottmann.tmdb2.entities.Person>> peopleCallList : peopleCallsPartitionedLists) {
                pendingPeople.addAll(peopleCallList
                    .parallelStream()
                    .map(call -> {

                        ResponseWrapper<com.uwetrottmann.tmdb2.entities.Person> tmdbPersonResponse = personResponseProcessor(call);

                        if (tmdbPersonResponse.getResult() == ResponseResult.SUCCESS && tmdbPersonResponse.getResponse().isSuccessful()) {
                            com.uwetrottmann.tmdb2.entities.Person tmdbPerson = tmdbPersonResponse.getResponse().body();
                            return processTmdbPerson(tmdbPerson);
                        } else if (tmdbPersonResponse.getResult() == ResponseResult.SUCCESS && !tmdbPersonResponse.getResponse().isSuccessful()) {
                            banEntity(call, BanReason.NOTFOUND);

                        } else if (tmdbPersonResponse.getResult() == ResponseResult.EXCEPTION) {
                            banEntity(call, BanReason.UNDEFINED);
                        }

                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
            }
        }

        private ResponseWrapper<com.uwetrottmann.tmdb2.entities.Person> personResponseProcessor(CallWrapper<com.uwetrottmann.tmdb2.entities.Person> c) {
            try {
                if (c.stringKey != null) {
                    if (personImdbIdList.contains(c.stringKey)) {
                        injectPersonToCredits(persistentPeople.parallelStream().filter(p -> p.getImdbId().equals(c.stringKey)).findFirst().get());
                        return ResponseWrapper.Of(null, ResponseResult.ENTITY_ALREADY_EXISTS);
                    }
                    if (isBanned(c.stringKey, c.type)) {
                        return ResponseWrapper.Of(null, ResponseResult.ENTITY_IS_BANNED);
                    }
                }
                if (c.intKey != null) {
                    if (personTmdbIdList.contains(c.intKey.longValue())) {
                        injectPersonToCredits(persistentPeople.parallelStream().filter(p -> p.getTmdbId().equals(c.intKey.longValue())).findFirst().get());
                        return ResponseWrapper.Of(null, ResponseResult.ENTITY_ALREADY_EXISTS);
                    }
                    if (isBanned(c.intKey.longValue(), c.type)) {
                        return ResponseWrapper.Of(null, ResponseResult.ENTITY_IS_BANNED);
                    }
                }
                return ResponseWrapper.Of(c.call.execute(), ResponseResult.SUCCESS);
            } catch (
                IOException e) {
                return ResponseWrapper.Of(null, ResponseResult.EXCEPTION);
            }
        }

        private Person processTmdbPerson(com.uwetrottmann.tmdb2.entities.Person tmdbPerson) {
            if (tmdbPerson == null || tmdbPerson.id == null)
                return null;

            Person miPerson = new Person();
            miPerson.setProfilePath(tmdbPerson.profile_path);
            miPerson.setImdbId(tmdbPerson.imdb_id);
            if (tmdbPerson.birthday != null)
                miPerson.setBirthDay(Instant.ofEpochMilli(tmdbPerson.birthday.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
            miPerson.setPopularity(tmdbPerson.popularity);
            miPerson.setTmdbId(Long.valueOf(tmdbPerson.id));
            miPerson.setName(tmdbPerson.name);
            miPerson.setBiography(tmdbPerson.biography);

            injectPersonToCredits(miPerson);
            return miPerson;
        }

        private boolean isBanned(String imdbId, TmdbEntityType type) {
            return bannedEntities.parallelStream().anyMatch(b -> b.getImdbId() != null && b.getImdbId().equals(imdbId) && b.getType().equals(type));
        }

        private boolean isBanned(Long tmdbId, TmdbEntityType type) {
            return bannedEntities.parallelStream().anyMatch(b -> b.getTmdbId() != null && b.getTmdbId().equals(tmdbId) && b.getType().equals(type));
        }

        public <T> void banEntity(CallWrapper<T> callWrapper, BanReason banReason) {
            banEntity(null, callWrapper.stringKey, (callWrapper.intKey != null ? callWrapper.intKey.longValue() : null), callWrapper.type, banReason);
        }

        private void banMovie(String imdbId, Long tmdbId, BanReason banReason) {
            banEntity(null, imdbId, tmdbId, TmdbEntityType.MOVIE, banReason);
        }

        private <T extends ImdbEntity & TmdbEntity> void banEntity(T entity, String imdbId, Long tmdbId, TmdbEntityType type, BanReason reason) {

            BannedEntity bannedEntity = new BannedEntity();
            bannedEntity
                .imdbId(imdbId)
                .tmdbId(tmdbId)
                .type(type)
                .reason(reason)
                .timestamp(LocalDate.now());

            bannedEntities.add(bannedEntity);

        }

        private void injectPersonToCredits(Person miPerson) {
            pendingCredits.parallelStream().filter(c -> c.getPersonTmdbId().equals(miPerson.getTmdbId())).forEach(c -> {
                c.setPerson(miPerson);
                c.setPersonTmdbId(miPerson.getTmdbId());
            });
        }

        private Genre getOrCreateGenre(com.uwetrottmann.tmdb2.entities.Genre tmdbGenre) {
            assert tmdbGenre.id != null;

            if (genreIdList.contains(tmdbGenre.id.longValue())) {
                return persistentGenres.parallelStream().filter(c -> c.getTmdbId().equals(tmdbGenre.id.longValue())).findFirst().get();
            }

            Genre genre;

            genre = new Genre();
            genre.setName(tmdbGenre.name);
            genre.setTmdbId(tmdbGenre.id.longValue());

            pendingGenres.put(genre.getTmdbId(), genre);

            return genre;

        }

        private ProductionCompany getOrCreateCompany(BaseCompany tmdbCompany) {
            assert tmdbCompany.id != null;

            if (companyIdList.contains(tmdbCompany.id.longValue())) {
                return persistentCompanies.parallelStream().filter(c -> c.getTmdbId().equals(tmdbCompany.id.longValue())).findFirst().get();
            }

            ProductionCompany company;

            company = new ProductionCompany();
            company.setName(tmdbCompany.name);
            company.setTmdbId(tmdbCompany.id.longValue());
            company.setLogoPath(tmdbCompany.logo_path);

            pendingCompanies.put(company.getTmdbId(), company);

            return company;
        }

        private ProductionCountry getOrCreateCountry(Country tmdbCountry) {
            if (countryIdList.contains(tmdbCountry.iso_3166_1)) {
                return persistentCountries.parallelStream().filter(c -> c.getIso31661().equals(tmdbCountry.iso_3166_1)).findFirst().get();
            }

            ProductionCountry country;

            country = new ProductionCountry();

            String name;
            name = Objects.equals(tmdbCountry.iso_3166_1, "MK") ? "North Macedonia" : tmdbCountry.name;

            country.setName(name);
            country.setIso31661(tmdbCountry.iso_3166_1);
            pendingCountries.put(country.getIso31661(), country);


            return country;

        }

        private Credit createCredit(gr.movieinsights.domain.Movie miMovie, com.uwetrottmann.tmdb2.entities.Movie tmdbMovie, BaseMember member) {
            assert tmdbMovie.id != null;
            assert member.id != null;

            CreditRole creditRole;
            CreditField creditField = member instanceof CrewMember ? CreditField.CREW : CreditField.CAST;

            if ((creditRole = (creditField == CreditField.CAST ? CreditRole.ACTOR : CreditRole.getCreditRoleByValue(((CrewMember) member).job))) == null) {
                return null;
            }

            Credit credit = new Credit();
            credit.setCreditId(member.credit_id);
            credit.setMovieTmdbId(tmdbMovie.id.longValue());
            credit.setPersonTmdbId(member.id.longValue());
            credit.setMovie(miMovie);
            credit.setRole(creditRole);

            pendingCredits.add(credit);

            return credit;

        }
    }


}
