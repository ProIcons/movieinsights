package gr.movieinsights.service.mapper;


import gr.movieinsights.domain.Genre;
import gr.movieinsights.service.dto.GenreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Genre} and its DTO {@link GenreDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GenreMapper extends EntityMapper<GenreDTO, Genre> {


    @Mapping(target = "movies", ignore = true)
    @Mapping(target = "removeMovies", ignore = true)
    Genre toEntity(GenreDTO genreDTO);

    default Genre fromId(Long id) {
        if (id == null) {
            return null;
        }
        Genre genre = new Genre();
        genre.setId(id);
        return genre;
    }
}
