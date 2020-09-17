package gr.movieinsights.service;

import gr.movieinsights.domain.Genre;
import gr.movieinsights.models.SearchableEntityMovieCountMap;
import gr.movieinsights.repository.GenreRepository;
import gr.movieinsights.repository.search.GenreSearchRepository;
import gr.movieinsights.service.dto.genre.BasicGenreDTO;
import gr.movieinsights.service.dto.genre.GenreDTO;
import gr.movieinsights.service.mapper.genre.BasicGenreMapper;
import gr.movieinsights.service.mapper.genre.GenreMapper;
import gr.movieinsights.service.util.BaseSearchableService;
import gr.movieinsights.service.util.IBasicDataProviderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Genre}.
 */
@Service
@Transactional
public class GenreService extends BaseSearchableService<Genre, gr.movieinsights.domain.elasticsearch.Genre, GenreDTO, BasicGenreDTO, GenreRepository, GenreSearchRepository, GenreMapper, BasicGenreMapper> implements IBasicDataProviderService<Genre,GenreDTO, BasicGenreDTO, GenreRepository, GenreMapper, BasicGenreMapper> {
    public GenreService(GenreRepository genreRepository, GenreMapper genreMapper, GenreSearchRepository genreSearchRepository, BasicGenreMapper basicGenreMapper, SearchableEntityMovieCountMap searchableEntityMovieCountMap) {
        super(genreRepository,genreSearchRepository,genreMapper, basicGenreMapper,searchableEntityMovieCountMap);

    }
}
