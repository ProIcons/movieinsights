package gr.movieinsights.service;

import gr.movieinsights.domain.MovieInsightsPerGenre;
import gr.movieinsights.repository.MovieInsightsPerGenreRepository;
import gr.movieinsights.repository.search.MovieInsightsPerGenreSearchRepository;
import gr.movieinsights.service.dto.MovieInsightsPerGenreDTO;
import gr.movieinsights.service.mapper.MovieInsightsPerGenreMapper;
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
 * Service Implementation for managing {@link MovieInsightsPerGenre}.
 */
@Service
@Transactional
public class MovieInsightsPerGenreService {

    private final Logger log = LoggerFactory.getLogger(MovieInsightsPerGenreService.class);

    private final MovieInsightsPerGenreRepository movieInsightsPerGenreRepository;

    private final MovieInsightsPerGenreMapper movieInsightsPerGenreMapper;

    private final MovieInsightsPerGenreSearchRepository movieInsightsPerGenreSearchRepository;

    public MovieInsightsPerGenreService(MovieInsightsPerGenreRepository movieInsightsPerGenreRepository, MovieInsightsPerGenreMapper movieInsightsPerGenreMapper, MovieInsightsPerGenreSearchRepository movieInsightsPerGenreSearchRepository) {
        this.movieInsightsPerGenreRepository = movieInsightsPerGenreRepository;
        this.movieInsightsPerGenreMapper = movieInsightsPerGenreMapper;
        this.movieInsightsPerGenreSearchRepository = movieInsightsPerGenreSearchRepository;
    }

    /**
     * Save a movieInsightsPerGenre.
     *
     * @param movieInsightsPerGenreDTO the entity to save.
     * @return the persisted entity.
     */
    public MovieInsightsPerGenreDTO save(MovieInsightsPerGenreDTO movieInsightsPerGenreDTO) {
        log.debug("Request to save MovieInsightsPerGenre : {}", movieInsightsPerGenreDTO);
        MovieInsightsPerGenre movieInsightsPerGenre = movieInsightsPerGenreMapper.toEntity(movieInsightsPerGenreDTO);
        movieInsightsPerGenre = movieInsightsPerGenreRepository.save(movieInsightsPerGenre);
        MovieInsightsPerGenreDTO result = movieInsightsPerGenreMapper.toDto(movieInsightsPerGenre);
        movieInsightsPerGenreSearchRepository.save(movieInsightsPerGenre);
        return result;
    }

    /**
     * Get all the movieInsightsPerGenres.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MovieInsightsPerGenreDTO> findAll() {
        log.debug("Request to get all MovieInsightsPerGenres");
        return movieInsightsPerGenreRepository.findAll().stream()
            .map(movieInsightsPerGenreMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one movieInsightsPerGenre by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieInsightsPerGenreDTO> findOne(Long id) {
        log.debug("Request to get MovieInsightsPerGenre : {}", id);
        return movieInsightsPerGenreRepository.findById(id)
            .map(movieInsightsPerGenreMapper::toDto);
    }

    /**
     * Delete the movieInsightsPerGenre by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MovieInsightsPerGenre : {}", id);
        movieInsightsPerGenreRepository.deleteById(id);
        movieInsightsPerGenreSearchRepository.deleteById(id);
    }

    /**
     * Search for the movieInsightsPerGenre corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MovieInsightsPerGenreDTO> search(String query) {
        log.debug("Request to search MovieInsightsPerGenres for query {}", query);
        return StreamSupport
            .stream(movieInsightsPerGenreSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(movieInsightsPerGenreMapper::toDto)
        .collect(Collectors.toList());
    }
}
