package gr.movieinsights.service;

import gr.movieinsights.domain.Movie;
import gr.movieinsights.repository.MovieRepository;
import gr.movieinsights.repository.search.MovieSearchRepository;
import gr.movieinsights.service.dto.MovieDTO;
import gr.movieinsights.service.mapper.MovieMapper;
import gr.movieinsights.service.util.ImdbIdentifiedBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Movie}.
 */
@Service
@Transactional
public class MovieService extends ImdbIdentifiedBaseService<Movie, MovieDTO, MovieRepository, MovieMapper> {

    private final Logger log = LoggerFactory.getLogger(MovieService.class);

    private final MovieSearchRepository movieSearchRepository;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper, MovieSearchRepository movieSearchRepository) {
        super(movieRepository, movieMapper);
        this.movieSearchRepository = movieSearchRepository;
    }

    /**
     * Save a movie.
     *
     * @param movieDTO the entity to save.
     * @return the persisted entity.
     */
    public MovieDTO save(MovieDTO movieDTO) {
        log.debug("Request to save Movie : {}", movieDTO);
        Movie movie = mapper.toEntity(movieDTO);
        movie = repository.save(movie);
        MovieDTO result = mapper.toDto(movie);
        movieSearchRepository.save(movie);
        return result;
    }

    /**
     * Get all the movies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MovieDTO> findAll() {
        log.debug("Request to get all Movies");
        return repository.findAllWithEagerRelationships().stream()
            .map(mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     * Get all the movies with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<MovieDTO> findAllWithEagerRelationships(Pageable pageable) {
        return repository.findAllWithEagerRelationships(pageable).map(mapper::toDto);
    }

    /**
     * Get one movie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieDTO> findOne(Long id) {
        log.debug("Request to get Movie : {}", id);
        return repository.findOneWithEagerRelationships(id)
            .map(mapper::toDto);
    }

    /**
     * Delete the movie by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Movie : {}", id);
        repository.deleteById(id);
        movieSearchRepository.deleteById(id);
    }

    /**
     * Search for the movie corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MovieDTO> search(String query) {
        log.debug("Request to search Movies for query {}", query);
        return StreamSupport
            .stream(movieSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }
}
