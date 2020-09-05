package gr.movieinsights.service;

import gr.movieinsights.domain.MovieInsightsPerCountry;
import gr.movieinsights.domain.MovieInsightsPerYear;
import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.repository.MovieInsightsPerCountryRepository;
import gr.movieinsights.service.dto.movieinsights.country.MovieInsightsPerCountryBasicDTO;
import gr.movieinsights.service.dto.movieinsights.country.MovieInsightsPerCountryDTO;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import gr.movieinsights.service.mapper.movieinsights.country.MovieInsightsPerCountryBasicMapper;
import gr.movieinsights.service.mapper.movieinsights.country.MovieInsightsPerCountryMapper;
import gr.movieinsights.service.util.BaseMovieInsightsService;
import gr.movieinsights.service.util.IBasicDataProviderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MovieInsightsPerCountry}.
 */
@Service
@Transactional
public class MovieInsightsPerCountryService
    extends BaseMovieInsightsService<MovieInsightsPerCountry, MovieInsightsPerCountryDTO, MovieInsightsPerCountryBasicDTO, MovieInsightsPerCountryRepository, MovieInsightsPerCountryMapper, MovieInsightsPerCountryBasicMapper> {

    private final MovieInsightsPerCountryBasicMapper basicMovieInsightsPerCountryMapper;

    private final MovieInsightsPerYearService movieInsightsPerYearService;

    public MovieInsightsPerCountryService(MovieInsightsPerCountryRepository movieInsightsPerCountryRepository, MovieInsightsPerCountryMapper movieInsightsPerCountryMapper, MovieInsightsPerCountryBasicMapper basicMovieInsightsPerCountryMapper, MovieInsightsPerYearService movieInsightsPerYearService) {
        super(movieInsightsPerCountryRepository, movieInsightsPerCountryMapper);
        this.basicMovieInsightsPerCountryMapper = basicMovieInsightsPerCountryMapper;
        this.movieInsightsPerYearService = movieInsightsPerYearService;
    }

    @Override
    public MovieInsightsPerCountryBasicMapper getBasicMapper() {
        return basicMovieInsightsPerCountryMapper;
    }

    /**
     * Get one movieInsightsPerCountry by iso.
     *
     * @param iso
     *     the iso of the entity.
     *
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieInsightsPerCountryDTO> findByCountryIso(String iso) {
        log.debug("Request to get MovieInsightsPerCountry : {}", iso);
        return repository.findByCountry(iso).map(mapper::toDto);
    }

    /**
     * Get one movieInsightsPerCountry by iso.
     *
     * @param iso
     *     the iso of the entity.
     *
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieInsightsPerCountryBasicDTO> findByCountryIsoBasic(String iso) {
        log.debug("Request to get MovieInsightsPerCountry : {}", iso);
        Optional<MovieInsightsPerCountryBasicDTO> movieInsightsPerCountryBasicDTO = repository.findByCountry(iso).map(basicMovieInsightsPerCountryMapper::toDto);
        movieInsightsPerCountryBasicDTO.ifPresent(m->m.setYears(getYears(m.getId())));
        return movieInsightsPerCountryBasicDTO;
    }

    public Optional<MovieInsightsPerYearDTO> findByYear(String countryIso, int year) {
        return movieInsightsPerYearService.findByCountry(countryIso, year);
    }

    @Override
    public Optional<MovieInsightsPerYearDTO> findByYear(long id, int year) {
        return movieInsightsPerYearService.findByCountryId(id, year);
    }

}
