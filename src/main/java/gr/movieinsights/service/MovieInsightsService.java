package gr.movieinsights.service;

import gr.movieinsights.domain.MovieInsights;
import gr.movieinsights.repository.MovieInsightsRepository;
import gr.movieinsights.repository.search.MovieInsightsSearchRepository;
import gr.movieinsights.service.dto.MovieInsightsDTO;
import gr.movieinsights.service.mapper.MovieInsightsMapper;
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
 * Service Implementation for managing {@link MovieInsights}.
 */
@Service
@Transactional
public class MovieInsightsService {

    private final Logger log = LoggerFactory.getLogger(MovieInsightsService.class);

    private final MovieInsightsRepository movieInsightsRepository;

    private final MovieInsightsMapper movieInsightsMapper;

    private final MovieInsightsSearchRepository movieInsightsSearchRepository;

    public MovieInsightsService(MovieInsightsRepository movieInsightsRepository, MovieInsightsMapper movieInsightsMapper, MovieInsightsSearchRepository movieInsightsSearchRepository) {
        this.movieInsightsRepository = movieInsightsRepository;
        this.movieInsightsMapper = movieInsightsMapper;
        this.movieInsightsSearchRepository = movieInsightsSearchRepository;
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
}
