package gr.movieinsights.service;

import gr.movieinsights.domain.MovieInsightsPerCountry;
import gr.movieinsights.repository.MovieInsightsPerCountryRepository;
import gr.movieinsights.repository.search.MovieInsightsPerCountrySearchRepository;
import gr.movieinsights.service.dto.MovieInsightsPerCountryDTO;
import gr.movieinsights.service.mapper.MovieInsightsPerCountryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MovieInsightsPerCountry}.
 */
@Service
@Transactional
public class MovieInsightsPerCountryService {

    private final Logger log = LoggerFactory.getLogger(MovieInsightsPerCountryService.class);

    private final MovieInsightsPerCountryRepository movieInsightsPerCountryRepository;

    private final MovieInsightsPerCountryMapper movieInsightsPerCountryMapper;

    private final MovieInsightsPerCountrySearchRepository movieInsightsPerCountrySearchRepository;

    public MovieInsightsPerCountryService(MovieInsightsPerCountryRepository movieInsightsPerCountryRepository, MovieInsightsPerCountryMapper movieInsightsPerCountryMapper, MovieInsightsPerCountrySearchRepository movieInsightsPerCountrySearchRepository) {
        this.movieInsightsPerCountryRepository = movieInsightsPerCountryRepository;
        this.movieInsightsPerCountryMapper = movieInsightsPerCountryMapper;
        this.movieInsightsPerCountrySearchRepository = movieInsightsPerCountrySearchRepository;
    }

    /**
     * Save a movieInsightsPerCountry.
     *
     * @param movieInsightsPerCountryDTO the entity to save.
     * @return the persisted entity.
     */
    public MovieInsightsPerCountryDTO save(MovieInsightsPerCountryDTO movieInsightsPerCountryDTO) {
        log.debug("Request to save MovieInsightsPerCountry : {}", movieInsightsPerCountryDTO);
        MovieInsightsPerCountry movieInsightsPerCountry = movieInsightsPerCountryMapper.toEntity(movieInsightsPerCountryDTO);
        movieInsightsPerCountry = movieInsightsPerCountryRepository.save(movieInsightsPerCountry);
        MovieInsightsPerCountryDTO result = movieInsightsPerCountryMapper.toDto(movieInsightsPerCountry);
        movieInsightsPerCountrySearchRepository.save(movieInsightsPerCountry);
        return result;
    }

    /**
     * Get all the movieInsightsPerCountries.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MovieInsightsPerCountryDTO> findAll() {
        log.debug("Request to get all MovieInsightsPerCountries");
        return movieInsightsPerCountryRepository.findAll().stream()
            .map(movieInsightsPerCountryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one movieInsightsPerCountry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieInsightsPerCountryDTO> findOne(Long id) {
        log.debug("Request to get MovieInsightsPerCountry : {}", id);
        return movieInsightsPerCountryRepository.findById(id)
            .map(movieInsightsPerCountryMapper::toDto);
    }

    /**
     * Delete the movieInsightsPerCountry by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MovieInsightsPerCountry : {}", id);
        movieInsightsPerCountryRepository.deleteById(id);
        movieInsightsPerCountrySearchRepository.deleteById(id);
    }

    /**
     * Search for the movieInsightsPerCountry corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MovieInsightsPerCountryDTO> search(String query) {
        log.debug("Request to search MovieInsightsPerCountries for query {}", query);
        return StreamSupport
            .stream(movieInsightsPerCountrySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(movieInsightsPerCountryMapper::toDto)
        .collect(Collectors.toList());
    }
}
