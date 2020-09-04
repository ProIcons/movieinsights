package gr.movieinsights.service;

import gr.movieinsights.domain.Movie;
import gr.movieinsights.repository.MovieRepository;
import gr.movieinsights.service.dto.movie.BasicMovieDTO;
import gr.movieinsights.service.dto.movie.MovieDTO;
import gr.movieinsights.service.mapper.movie.BasicMovieMapper;
import gr.movieinsights.service.mapper.movie.MovieMapper;
import gr.movieinsights.service.util.BaseService;
import gr.movieinsights.service.util.IBasicDataProviderService;
import gr.movieinsights.service.util.ImdbIdentifiedService;
import gr.movieinsights.service.util.TmdbIdentifiedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Movie}.
 */
@Service
@Transactional
public class MovieService
    extends BaseService<Movie, MovieDTO, MovieRepository, MovieMapper> implements
    IBasicDataProviderService<Movie, MovieDTO, BasicMovieDTO, MovieRepository, MovieMapper, BasicMovieMapper>,
    TmdbIdentifiedService<Movie, MovieDTO, MovieRepository, MovieMapper>,
    ImdbIdentifiedService<Movie, MovieDTO, MovieRepository, MovieMapper> {

    private final BasicMovieMapper basicMovieMapper;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper, BasicMovieMapper basicMovieMapper) {
        super(movieRepository, movieMapper);
        this.basicMovieMapper = basicMovieMapper;
    }

    @Override
    public BasicMovieMapper getBasicMapper() {
        return basicMovieMapper;
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
     * Get all the movies with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<BasicMovieDTO> findAllWithEagerRelationshipsBasic(Pageable pageable) {
        return repository.findAllWithEagerRelationships(pageable).map(basicMovieMapper::toDto);
    }

}
