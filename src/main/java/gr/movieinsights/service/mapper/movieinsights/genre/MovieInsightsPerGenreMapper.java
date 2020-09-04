package gr.movieinsights.service.mapper.movieinsights.genre;


import gr.movieinsights.domain.MovieInsightsPerGenre;
import gr.movieinsights.service.dto.movieinsights.genre.MovieInsightsPerGenreDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.genre.BasicGenreMapper;
import gr.movieinsights.service.mapper.movieinsights.MovieInsightsMapper;
import gr.movieinsights.service.mapper.movieinsights.year.MovieInsightsPerYearMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MovieInsightsPerGenre} and its DTO {@link MovieInsightsPerGenreDTO}.
 */
@Mapper(componentModel = "spring", uses = {MovieInsightsMapper.class, MovieInsightsPerYearMapper.class, BasicGenreMapper.class})
public interface MovieInsightsPerGenreMapper extends EntityMapper<MovieInsightsPerGenreDTO, MovieInsightsPerGenre> {
    @Mapping(source = "genre", target = "entity")
    MovieInsightsPerGenreDTO toDto(MovieInsightsPerGenre movieInsightsPerGenre);

    @Mapping(source = "entity", target = "genre")
    @Mapping(target = "removeMovieInsightsPerYear",ignore = true)
    MovieInsightsPerGenre toEntity(MovieInsightsPerGenreDTO movieInsightsPerGenreDTO);

    default MovieInsightsPerGenre fromId(Long id) {
        if (id == null) {
            return null;
        }
        MovieInsightsPerGenre movieInsightsPerGenre = new MovieInsightsPerGenre();
        movieInsightsPerGenre.setId(id);
        return movieInsightsPerGenre;
    }
}
