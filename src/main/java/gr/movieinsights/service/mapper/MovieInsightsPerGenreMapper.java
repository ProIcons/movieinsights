package gr.movieinsights.service.mapper;


import gr.movieinsights.domain.MovieInsightsPerGenre;
import gr.movieinsights.service.dto.MovieInsightsPerGenreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MovieInsightsPerGenre} and its DTO {@link MovieInsightsPerGenreDTO}.
 */
@Mapper(componentModel = "spring", uses = {MovieInsightsMapper.class, GenreMapper.class})
public interface MovieInsightsPerGenreMapper extends EntityMapper<MovieInsightsPerGenreDTO, MovieInsightsPerGenre> {

    @Mapping(source = "movieInsights.id", target = "movieInsightsId")
    @Mapping(source = "genre.id", target = "genreId")
    MovieInsightsPerGenreDTO toDto(MovieInsightsPerGenre movieInsightsPerGenre);

    @Mapping(source = "movieInsightsId", target = "movieInsights")
    @Mapping(target = "movieInsightsPerYears", ignore = true)
    @Mapping(target = "removeMovieInsightsPerYear", ignore = true)
    @Mapping(source = "genreId", target = "genre")
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
