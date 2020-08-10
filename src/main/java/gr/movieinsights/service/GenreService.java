package gr.movieinsights.service;

import gr.movieinsights.domain.Genre;
import gr.movieinsights.repository.GenreRepository;
import gr.movieinsights.repository.search.GenreSearchRepository;
import gr.movieinsights.service.dto.GenreDTO;
import gr.movieinsights.service.mapper.GenreMapper;
import gr.movieinsights.service.util.TmdbIdentifiedBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Genre}.
 */
@Service
@Transactional
public class GenreService extends TmdbIdentifiedBaseService<Genre, GenreDTO, GenreRepository, GenreMapper> {

    private final Logger log = LoggerFactory.getLogger(GenreService.class);

    private final GenreSearchRepository genreSearchRepository;

    public GenreService(GenreRepository genreRepository, GenreMapper genreMapper, GenreSearchRepository genreSearchRepository) {
        super(genreRepository,genreMapper);
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
        Genre genre = mapper.toEntity(genreDTO);
        genre = repository.save(genre);
        GenreDTO result = mapper.toDto(genre);
        genreSearchRepository.save(genre);
        return result;
    }

    /**
     * Get all the genres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GenreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Genres");
        return repository.findAll(pageable)
            .map(mapper::toDto);
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
        return repository.findById(id)
            .map(mapper::toDto);
    }

    /**
     * Delete the genre by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Genre : {}", id);
        repository.deleteById(id);
        genreSearchRepository.deleteById(id);
    }

    /**
     * Search for the genre corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GenreDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Genres for query {}", query);
        return genreSearchRepository.search(queryStringQuery(query), pageable)
            .map(mapper::toDto);
    }
}
