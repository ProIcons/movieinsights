package gr.movieinsights.service;

import gr.movieinsights.domain.MovieInsightsPerGenre;
import gr.movieinsights.repository.MovieInsightsPerGenreRepository;
import gr.movieinsights.service.dto.movieinsights.genre.MovieInsightsPerGenreBasicDTO;
import gr.movieinsights.service.dto.movieinsights.genre.MovieInsightsPerGenreDTO;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import gr.movieinsights.service.mapper.movieinsights.genre.MovieInsightsPerGenreBasicMapper;
import gr.movieinsights.service.mapper.movieinsights.genre.MovieInsightsPerGenreMapper;
import gr.movieinsights.service.util.BaseMovieInsightsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MovieInsightsPerGenre}.
 */
@Service
@Transactional
public class MovieInsightsPerGenreService
    extends BaseMovieInsightsService<MovieInsightsPerGenre, MovieInsightsPerGenreDTO, MovieInsightsPerGenreBasicDTO, MovieInsightsPerGenreRepository, MovieInsightsPerGenreMapper, MovieInsightsPerGenreBasicMapper> {

    private final MovieInsightsPerGenreBasicMapper basicMovieInsightsPerGenreMapper;

    private final MovieInsightsPerYearService movieInsightsPerYearService;

    public MovieInsightsPerGenreService(MovieInsightsPerGenreRepository movieInsightsPerGenreRepository, MovieInsightsPerGenreMapper movieInsightsPerGenreMapper, MovieInsightsPerGenreBasicMapper basicMovieInsightsPerGenreMapper, MovieInsightsPerYearService movieInsightsPerYearService) {
        super(movieInsightsPerGenreRepository, movieInsightsPerGenreMapper);
        this.basicMovieInsightsPerGenreMapper = basicMovieInsightsPerGenreMapper;
        this.movieInsightsPerYearService = movieInsightsPerYearService;
    }

    @Override
    public MovieInsightsPerGenreBasicMapper getBasicMapper() {
        return basicMovieInsightsPerGenreMapper;
    }

    /**
     * Get one MovieInsightsPerGenre by genre.
     *
     * @param id
     *     the id of the genre of the entity.
     *
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieInsightsPerGenreDTO> findByGenreId(Long id) {
        log.debug("Request to get MovieInsightsPerGenre By GenreId : {}", id);
        return repository.findByGenre_Id(id).map(getMapper()::toDto);
    }

    /**
     * Get one MovieInsightsPerGenre by genre.
     *
     * @param id
     *     the id of the genre of the entity.
     *
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieInsightsPerGenreBasicDTO> findByGenreIdBasic(Long id) {
        log.debug("Request to get MovieInsightsPerGenre by GenreId : {}", id);
        return repository.findByGenre_Id(id).map(getBasicMapper()::toDto);
    }

    /**
     * Get one MovieInsightsPerGenre by genre.
     *
     * @param name
     *     the name of the genre of the entity.
     *
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieInsightsPerGenreDTO> findByGenreName(String name) {
        log.debug("Request to get MovieInsightsPerGenre By Genre name : {}", name);
        return repository.findByGenre_NameIgnoreCase(name).map(getMapper()::toDto);
    }

    /**
     * Get one MovieInsightsPerGenre by genre.
     *
     * @param name
     *     the name of the genre of the entity.
     *
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieInsightsPerGenreBasicDTO> findByGenreNameBasic(String name) {
        log.debug("Request to get MovieInsightsPerGenre by Genre name : {}", name);
        return repository.findByGenre_NameIgnoreCase(name).map(getBasicMapper()::toDto);
    }

    @Override
    public Optional<MovieInsightsPerYearDTO> findByYear(long genreId, int year) {
        return movieInsightsPerYearService.findByGenre(genreId, year);
    }

    public Optional<MovieInsightsPerYearDTO> findByYear(String genreName, int year) {
        return movieInsightsPerYearService.findByGenre(genreName, year);
    }
}
