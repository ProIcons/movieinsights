package gr.movieinsights.service;

import gr.movieinsights.domain.MovieInsightsPerPerson;
import gr.movieinsights.repository.MovieInsightsPerPersonRepository;
import gr.movieinsights.repository.search.MovieInsightsPerPersonSearchRepository;
import gr.movieinsights.service.dto.MovieInsightsPerPersonDTO;
import gr.movieinsights.service.mapper.MovieInsightsPerPersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link MovieInsightsPerPerson}.
 */
@Service
@Transactional
public class MovieInsightsPerPersonService {

    private final Logger log = LoggerFactory.getLogger(MovieInsightsPerPersonService.class);

    private final MovieInsightsPerPersonRepository movieInsightsPerPersonRepository;

    private final MovieInsightsPerPersonMapper movieInsightsPerPersonMapper;

    private final MovieInsightsPerPersonSearchRepository movieInsightsPerPersonSearchRepository;

    public MovieInsightsPerPersonService(MovieInsightsPerPersonRepository movieInsightsPerPersonRepository, MovieInsightsPerPersonMapper movieInsightsPerPersonMapper, MovieInsightsPerPersonSearchRepository movieInsightsPerPersonSearchRepository) {
        this.movieInsightsPerPersonRepository = movieInsightsPerPersonRepository;
        this.movieInsightsPerPersonMapper = movieInsightsPerPersonMapper;
        this.movieInsightsPerPersonSearchRepository = movieInsightsPerPersonSearchRepository;
    }

    /**
     * Save a movieInsightsPerPerson.
     *
     * @param movieInsightsPerPersonDTO the entity to save.
     * @return the persisted entity.
     */
    public MovieInsightsPerPersonDTO save(MovieInsightsPerPersonDTO movieInsightsPerPersonDTO) {
        log.debug("Request to save MovieInsightsPerPerson : {}", movieInsightsPerPersonDTO);
        MovieInsightsPerPerson movieInsightsPerPerson = movieInsightsPerPersonMapper.toEntity(movieInsightsPerPersonDTO);
        movieInsightsPerPerson = movieInsightsPerPersonRepository.save(movieInsightsPerPerson);
        MovieInsightsPerPersonDTO result = movieInsightsPerPersonMapper.toDto(movieInsightsPerPerson);
        movieInsightsPerPersonSearchRepository.save(movieInsightsPerPerson);
        return result;
    }

    /**
     * Get all the movieInsightsPerPeople.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MovieInsightsPerPersonDTO> findAll() {
        log.debug("Request to get all MovieInsightsPerPeople");
        return movieInsightsPerPersonRepository.findAll().stream()
            .map(movieInsightsPerPersonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one movieInsightsPerPerson by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieInsightsPerPersonDTO> findOne(Long id) {
        log.debug("Request to get MovieInsightsPerPerson : {}", id);
        return movieInsightsPerPersonRepository.findById(id)
            .map(movieInsightsPerPersonMapper::toDto);
    }

    /**
     * Delete the movieInsightsPerPerson by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MovieInsightsPerPerson : {}", id);
        movieInsightsPerPersonRepository.deleteById(id);
        movieInsightsPerPersonSearchRepository.deleteById(id);
    }

    /**
     * Search for the movieInsightsPerPerson corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MovieInsightsPerPersonDTO> search(String query) {
        log.debug("Request to search MovieInsightsPerPeople for query {}", query);
        return StreamSupport
            .stream(movieInsightsPerPersonSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(movieInsightsPerPersonMapper::toDto)
            .collect(Collectors.toList());
    }
}
