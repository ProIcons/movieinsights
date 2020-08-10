package gr.movieinsights.service.util.wrappers.movieinsights;

import gr.movieinsights.domain.Genre;
import gr.movieinsights.domain.Movie;
import gr.movieinsights.domain.Person;
import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.service.enumeration.MovieInsightsCategory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MovieInsightsWrapper extends BaseMovieInsightsWrapper {
    public final ConcurrentMap<Person, ActorWrapper> actors;
    public final ConcurrentMap<Person, ProducerWrapper> producers;
    public final ConcurrentMap<Person, WriterWrapper> writers;
    public final ConcurrentMap<Person, DirectorWrapper> directors;
    public final ConcurrentMap<ProductionCompany, CompanyWrapper> companies;
    public final ConcurrentMap<ProductionCountry, CountryWrapper> countries;
    public final ConcurrentMap<Genre, GenreWrapper> genres;
    public final ConcurrentMap<Integer, YearWrapper> years;


    MovieInsightsWrapper(BaseWrapper<?> source, MovieInsightsCategory category, BaseMovieInsightsWrapper master) {
        super(source, category, master);
        this.actors = new ConcurrentHashMap<>();
        this.producers = new ConcurrentHashMap<>();
        this.directors = new ConcurrentHashMap<>();
        this.writers = new ConcurrentHashMap<>();
        this.companies = new ConcurrentHashMap<>();
        this.countries = new ConcurrentHashMap<>();
        this.genres = new ConcurrentHashMap<>();
        this.years = new ConcurrentHashMap<>();
    }

    public MovieInsightsWrapper(BaseWrapper<?> source, MovieInsightsCategory category) {
        this(source, category, null);
    }

    @Override
    public BaseMovieInsightsWrapper getMovieInsightsWrapperByYear(int year) {
        if (!slave && category != MovieInsightsCategory.PerYear) {

            BaseMovieInsightsWrapper movieInsights;
            if ((movieInsights = movieInsightsWrapperConcurrentMap.getOrDefault(year, null)) == null) {
                movieInsights = new MovieInsightsWrapper(new YearWrapper(year), MovieInsightsCategory.PerYear, this);
                movieInsightsWrapperConcurrentMap.put(year, movieInsights);
            }
            return movieInsights;

        }
        return null;
    }

    protected <T, W extends BaseWrapper<T>> void submit(W wrapper, Map<T, W> objMap, Movie movie, T obj) {
        W wrapper2;
        if ((wrapper2 = objMap.getOrDefault(obj, null)) == null) {
            wrapper2 = wrapper;
            objMap.put(obj, wrapper2);
        }

        wrapper2.movies.add(movie);
        wrapper2.count++;
    }

    public void submitActor(Person person, Movie movie) {
        submit(new ActorWrapper(person), actors, movie, person);
        BaseMovieInsightsWrapper dependentWrapper;
        if (!slave && movie.getReleaseDate() != null && (dependentWrapper = getMovieInsightsWrapperByYear(movie.getReleaseDate().getYear())) != null)
            dependentWrapper.submitActor(person, movie);
    }

    public void submitProducer(Person person, Movie movie) {
        submit(new ProducerWrapper(person), producers, movie, person);
        BaseMovieInsightsWrapper dependentWrapper;
        if (!slave && movie.getReleaseDate() != null && (dependentWrapper = getMovieInsightsWrapperByYear(movie.getReleaseDate().getYear())) != null)
            dependentWrapper.submitProducer(person, movie);
    }

    public void submitWriter(Person person, Movie movie) {
        submit(new WriterWrapper(person), writers, movie, person);
        BaseMovieInsightsWrapper dependentWrapper;
        if (!slave && movie.getReleaseDate() != null && (dependentWrapper = getMovieInsightsWrapperByYear(movie.getReleaseDate().getYear())) != null)
            dependentWrapper.submitWriter(person, movie);
    }

    public void submitDirector(Person person, Movie movie) {
        submit(new DirectorWrapper(person), directors, movie, person);
        BaseMovieInsightsWrapper dependentWrapper;
        if (!slave && movie.getReleaseDate() != null && (dependentWrapper = getMovieInsightsWrapperByYear(movie.getReleaseDate().getYear())) != null)
            dependentWrapper.submitDirector(person, movie);
    }

    public void submitCompany(ProductionCompany company, Movie movie) {
        submit(new CompanyWrapper(company), companies, movie, company);
        BaseMovieInsightsWrapper dependentWrapper;
        if (!slave && movie.getReleaseDate() != null && (dependentWrapper = getMovieInsightsWrapperByYear(movie.getReleaseDate().getYear())) != null)
            dependentWrapper.submitCompany(company, movie);
    }

    public void submitCountry(ProductionCountry country, Movie movie) {
        submit(new CountryWrapper(country), countries, movie, country);
        BaseMovieInsightsWrapper dependentWrapper;
        if (!slave && movie.getReleaseDate() != null && (dependentWrapper = getMovieInsightsWrapperByYear(movie.getReleaseDate().getYear())) != null)
            dependentWrapper.submitCountry(country, movie);
    }

    public void submitGenre(Genre genre, Movie movie) {
        submit(new GenreWrapper(genre), genres, movie, genre);
        BaseMovieInsightsWrapper dependentWrapper;
        if (!slave && movie.getReleaseDate() != null && (dependentWrapper = getMovieInsightsWrapperByYear(movie.getReleaseDate().getYear())) != null)
            dependentWrapper.submitGenre(genre, movie);
    }

    public void submitYear(Integer year, Movie movie) {
        submit(new YearWrapper(year), years, movie, year);
    }

    protected void calculateGenreInsights() {
        Optional<GenreWrapper> mostPopularGenreEntryResult = genres
            .values()
            .parallelStream()
            .sorted(
                GenreWrapper.getComparator().reversed())
            .filter(e -> (slave ? master.category : category) != MovieInsightsCategory.PerGenre || e.object != (slave ? master.source.object : source.object))
            .findFirst();

        if (mostPopularGenreEntryResult.isPresent()) {
            GenreWrapper mostPopularGenreEntry = mostPopularGenreEntryResult.get();
            movieInsights.setMostPopularGenre(mostPopularGenreEntry.object);
            movieInsights.setMostPopularGenreMovieCount(mostPopularGenreEntry.count);
        }
    }

    protected void calculateCompanyInsights() {
        Optional<CompanyWrapper> mostPopularProductionCompanyEntryResult = companies
            .values()
            .parallelStream()
            .sorted(
                CompanyWrapper.getComparator().reversed())
            .filter(e -> (slave ? master.category : category) != MovieInsightsCategory.PerCompany || e.object != (slave ? master.source.object : source.object))
            .findFirst();

        if (mostPopularProductionCompanyEntryResult.isPresent()) {
            CompanyWrapper mostPopularProductionCompanyEntry = mostPopularProductionCompanyEntryResult.get();
            movieInsights.setMostPopularProductionCompany(mostPopularProductionCompanyEntry.object);
            movieInsights.setMostPopularProductionCompanyMovieCount(mostPopularProductionCompanyEntry.count);
        }
    }

    protected void calculateCountryInsights() {
        Optional<CountryWrapper> mostPopularProductionCountryEntryResult = countries
            .values()
            .parallelStream()
            .sorted(
                CountryWrapper.getComparator().reversed())
            .filter(e -> (slave ? master.category : category) != MovieInsightsCategory.PerCountry || e.object != (slave ? master.source.object : source.object))
            .findFirst();

        if (mostPopularProductionCountryEntryResult.isPresent()) {
            CountryWrapper mostPopularProductionCountryEntry = mostPopularProductionCountryEntryResult.get();
            movieInsights.setMostPopularProductionCountry(mostPopularProductionCountryEntry.object);
            movieInsights.setMostPopularProductionCountryMovieCount(mostPopularProductionCountryEntry.count);

        }
    }

    protected void calculateActorInsights() {
        Optional<ActorWrapper> mostPopularActorEntryResult = actors
            .values()
            .parallelStream()
            .sorted(
                PersonWrapper.getComparator().reversed())
            .filter(e -> (slave ? master.category : category) != MovieInsightsCategory.PerPerson || e.object != (slave ? master.source.object : source.object))
            .findFirst();

        if (mostPopularActorEntryResult.isPresent()) {
            ActorWrapper mostPopularActorEntry = mostPopularActorEntryResult.get();
            movieInsights.setMostPopularActor(mostPopularActorEntry.object);
            movieInsights.setMostPopularActorMovieCount(mostPopularActorEntry.count);
        }
    }

    protected void calculateProducerInsights() {
        Optional<ProducerWrapper> mostPopularProducerEntryResult = producers
            .values()
            .parallelStream()
            .sorted(
                PersonWrapper.getComparator().reversed())
            .filter(e -> (slave ? master.category : category) != MovieInsightsCategory.PerPerson || e.object != (slave ? master.source.object : source.object))
            .findFirst();

        if (mostPopularProducerEntryResult.isPresent()) {
            ProducerWrapper mostPopularProducerEntry = mostPopularProducerEntryResult.get();
            movieInsights.setMostPopularProducer(mostPopularProducerEntry.object);
            movieInsights.setMostPopularProducerMovieCount(mostPopularProducerEntry.count);
        }

    }

    protected void calculateWriterInsights() {
        Optional<WriterWrapper> mostPopularWriterEntryResult = writers
            .values()
            .parallelStream()
            .sorted(
                PersonWrapper.getComparator().reversed())
            .filter(e -> (slave ? master.category : category) != MovieInsightsCategory.PerPerson || e.object != (slave ? master.source.object : source.object))
            .findFirst();

        if (mostPopularWriterEntryResult.isPresent()) {
            WriterWrapper mostPopularWriterEntry = mostPopularWriterEntryResult.get();
            movieInsights.setMostPopularWriter(mostPopularWriterEntry.object);
            movieInsights.setMostPopularWriterMovieCount(mostPopularWriterEntry.count);

        }
    }

    protected void calculateDirectorInsights() {
        Optional<DirectorWrapper> mostPopularDirectorEntryResult = directors
            .values()
            .parallelStream()
            .sorted(
                PersonWrapper.getComparator().reversed())
            .filter(e -> (slave ? master.category : category) != MovieInsightsCategory.PerPerson || e.object != (slave ? master.source.object : source.object))
            .findFirst();

        if (mostPopularDirectorEntryResult.isPresent()) {
            DirectorWrapper mostPopularDirectorEntry = mostPopularDirectorEntryResult.get();
            movieInsights.setMostPopularDirector(mostPopularDirectorEntry.object);
            movieInsights.setMostPopularDirectorMovieCount(mostPopularDirectorEntry.count);
        }
    }


}
