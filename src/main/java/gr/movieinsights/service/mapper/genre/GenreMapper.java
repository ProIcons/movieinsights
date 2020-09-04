package gr.movieinsights.service.mapper.genre;


import gr.movieinsights.domain.Genre;
import gr.movieinsights.service.dto.genre.GenreDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.movie.BasicMovieMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Genre} and its DTO {@link GenreDTO}.
 */
@Mapper(componentModel = "spring", uses = {BasicMovieMapper.class})
public interface GenreMapper extends EntityMapper<GenreDTO, Genre> {
    @Mapping(target = "removeMovies", ignore = true)
    Genre toEntity(GenreDTO genreDTO);

    @Override
    GenreDTO toDto(Genre entity);

    default Genre fromId(Long id) {
        if (id == null) {
            return null;
        }
        Genre genre = new Genre();
        genre.setId(id);
        return genre;
    }
}
