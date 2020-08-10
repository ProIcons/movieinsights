package gr.movieinsights.models;

import gr.movieinsights.domain.Genre;
import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.domain.ProductionCountry;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class MovieWrapper {
    private final Map<Long, Genre> pendingGenres;
    private final Map<Long, ProductionCompany> pendingCompanies;
    private final Map<String, ProductionCountry> pendingCountries;
    private final gr.movieinsights.domain.Movie movie;
    private final Set<Long> companyIds;
    private final Set<String> countryIds;
    private final Set<Long> genreIds;

    public MovieWrapper(Map<Long, Genre> pendingGenres, Map<Long, ProductionCompany> pendingCompanies, Map<String, ProductionCountry> pendingCountries, gr.movieinsights.domain.Movie movie) {
        this.pendingGenres = pendingGenres;
        this.pendingCompanies = pendingCompanies;
        this.pendingCountries = pendingCountries;
        this.movie = movie;
        this.companyIds = new HashSet<>();
        this.countryIds = new HashSet<>();
        this.genreIds = new HashSet<>();
    }

    public boolean addCompany(ProductionCompany company) {
        return companyIds.add(company.getTmdbId());
    }

    public boolean addCountry(ProductionCountry country) {
        return countryIds.add(country.getIso31661());
    }

    public boolean addGenre(Genre genre) {
        return genreIds.add(genre.getTmdbId());
    }

    public gr.movieinsights.domain.Movie build() {
        Set<ProductionCompany> productionCompanyList = new CopyOnWriteArraySet<>();
        Set<ProductionCountry> productionCountryList = new CopyOnWriteArraySet<>();
        Set<Genre> genreList = new CopyOnWriteArraySet<>();

        companyIds.parallelStream().forEach(e -> {
            productionCompanyList.add(pendingCompanies.get(e));
        });

        countryIds.parallelStream().forEach(e -> {
            productionCountryList.add(pendingCountries.get(e));
        });

        genreIds.parallelStream().forEach(e -> {
            genreList.add(pendingGenres.get(e));
        });

        movie.setGenres(genreList);
        movie.setCompanies(productionCompanyList);
        movie.setCountries(productionCountryList);

        return movie;
    }
}
