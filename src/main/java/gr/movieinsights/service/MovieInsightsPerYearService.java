package gr.movieinsights.service;

import gr.movieinsights.domain.MovieInsightsPerYear;
import gr.movieinsights.repository.MovieInsightsPerYearRepository;
import gr.movieinsights.repository.search.MovieInsightsPerYearSearchRepository;
import gr.movieinsights.service.dto.MovieInsightsPerYearDTO;
import gr.movieinsights.service.mapper.MovieInsightsPerYearMapper;
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
 * Service Implementation for managing {@link MovieInsightsPerYear}.
 */
@Service
@Transactional
public class MovieInsightsPerYearService {

    private final Logger log = LoggerFactory.getLogger(MovieInsightsPerYearService.class);

    private final MovieInsightsPerYearRepository movieInsightsPerYearRepository;

    private final MovieInsightsPerYearMapper movieInsightsPerYearMapper;

    private final MovieInsightsPerYearSearchRepository movieInsightsPerYearSearchRepository;

    public MovieInsightsPerYearService(MovieInsightsPerYearRepository movieInsightsPerYearRepository, MovieInsightsPerYearMapper movieInsightsPerYearMapper, MovieInsightsPerYearSearchRepository movieInsightsPerYearSearchRepository) {
        this.movieInsightsPerYearRepository = movieInsightsPerYearRepository;
        this.movieInsightsPerYearMapper = movieInsightsPerYearMapper;
        this.movieInsightsPerYearSearchRepository = movieInsightsPerYearSearchRepository;
    }

    /**
     * Save a movieInsightsPerYear.
     *
     * @param movieInsightsPerYearDTO the entity to save.
     * @return the persisted entity.
     */
    public MovieInsightsPerYearDTO save(MovieInsightsPerYearDTO movieInsightsPerYearDTO) {
        log.debug("Request to save MovieInsightsPerYear : {}", movieInsightsPerYearDTO);
        MovieInsightsPerYear movieInsightsPerYear = movieInsightsPerYearMapper.toEntity(movieInsightsPerYearDTO);
        movieInsightsPerYear = movieInsightsPerYearRepository.save(movieInsightsPerYear);
        MovieInsightsPerYearDTO result = movieInsightsPerYearMapper.toDto(movieInsightsPerYear);
        movieInsightsPerYearSearchRepository.save(movieInsightsPerYear);
        return result;
    }

    /**
     * Get all the movieInsightsPerYears.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MovieInsightsPerYearDTO> findAll() {
        log.debug("Request to get all MovieInsightsPerYears");
        return movieInsightsPerYearRepository.findAll().stream()
            .map(movieInsightsPerYearMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one movieInsightsPerYear by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieInsightsPerYearDTO> findOne(Long id) {
        log.debug("Request to get MovieInsightsPerYear : {}", id);
        return movieInsightsPerYearRepository.findById(id)
            .map(movieInsightsPerYearMapper::toDto);
    }

    /**
     * Delete the movieInsightsPerYear by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MovieInsightsPerYear : {}", id);
        movieInsightsPerYearRepository.deleteById(id);
        movieInsightsPerYearSearchRepository.deleteById(id);
    }

    /**
     * Search for the movieInsightsPerYear corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MovieInsightsPerYearDTO> search(String query) {
        log.debug("Request to search MovieInsightsPerYears for query {}", query);
        return StreamSupport
            .stream(movieInsightsPerYearSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(movieInsightsPerYearMapper::toDto)
        .collect(Collectors.toList());
    }
}
