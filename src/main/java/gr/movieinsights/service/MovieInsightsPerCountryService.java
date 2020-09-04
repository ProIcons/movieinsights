package gr.movieinsights.service;

import gr.movieinsights.domain.MovieInsightsPerCountry;
import gr.movieinsights.repository.MovieInsightsPerCountryRepository;
import gr.movieinsights.service.dto.movieinsights.country.MovieInsightsPerCountryBasicDTO;
import gr.movieinsights.service.dto.movieinsights.country.MovieInsightsPerCountryDTO;
import gr.movieinsights.service.mapper.movieinsights.country.MovieInsightsPerCountryBasicMapper;
import gr.movieinsights.service.mapper.movieinsights.country.MovieInsightsPerCountryMapper;
import gr.movieinsights.service.util.BaseService;
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
    extends BaseService<MovieInsightsPerCountry, MovieInsightsPerCountryDTO, MovieInsightsPerCountryRepository, MovieInsightsPerCountryMapper>
    implements IBasicDataProviderService<MovieInsightsPerCountry, MovieInsightsPerCountryDTO, MovieInsightsPerCountryBasicDTO, MovieInsightsPerCountryRepository, MovieInsightsPerCountryMapper, MovieInsightsPerCountryBasicMapper> {

    private final MovieInsightsPerCountryBasicMapper basicMovieInsightsPerCountryMapper;

    public MovieInsightsPerCountryService(MovieInsightsPerCountryRepository movieInsightsPerCountryRepository, MovieInsightsPerCountryMapper movieInsightsPerCountryMapper, MovieInsightsPerCountryBasicMapper basicMovieInsightsPerCountryMapper) {
        super(movieInsightsPerCountryRepository, movieInsightsPerCountryMapper);
        this.basicMovieInsightsPerCountryMapper = basicMovieInsightsPerCountryMapper;
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
        return repository.findByCountry(iso).map(basicMovieInsightsPerCountryMapper::toDto);
    }
}
