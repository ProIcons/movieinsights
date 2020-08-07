package gr.movieinsights.service;

import gr.movieinsights.domain.Genre;
import gr.movieinsights.repository.GenreRepository;
import gr.movieinsights.repository.search.GenreSearchRepository;
import gr.movieinsights.service.dto.GenreDTO;
import gr.movieinsights.service.mapper.GenreMapper;
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
 * Service Implementation for managing {@link Genre}.
 */
@Service
@Transactional
public class GenreService {

    private final Logger log = LoggerFactory.getLogger(GenreService.class);

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    private final GenreSearchRepository genreSearchRepository;

    public GenreService(GenreRepository genreRepository, GenreMapper genreMapper, GenreSearchRepository genreSearchRepository) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
        this.genreSearchRepository = genreSearchRepository;
    }

    /**
     * Save a genre.
     *
     * @param genreDTO the entity to save.
     * @return the persisted entity.
     */
    public GenreDTO save(GenreDTO genreDTO) {
        log.debug("Request to save Genre : {}", genreDTO);
        Genre genre = genreMapper.toEntity(genreDTO);
        genre = genreRepository.save(genre);
        GenreDTO result = genreMapper.toDto(genre);
        genreSearchRepository.save(genre);
        return result;
    }

    /**
     * Get all the genres.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GenreDTO> findAll() {
        log.debug("Request to get all Genres");
        return genreRepository.findAll().stream()
            .map(genreMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one genre by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GenreDTO> findOne(Long id) {
        log.debug("Request to get Genre : {}", id);
        return genreRepository.findById(id)
            .map(genreMapper::toDto);
    }

    /**
     * Delete the genre by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Genre : {}", id);
        genreRepository.deleteById(id);
        genreSearchRepository.deleteById(id);
    }

    /**
     * Search for the genre corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GenreDTO> search(String query) {
        log.debug("Request to search Genres for query {}", query);
        return StreamSupport
            .stream(genreSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(genreMapper::toDto)
        .collect(Collectors.toList());
    }
}
