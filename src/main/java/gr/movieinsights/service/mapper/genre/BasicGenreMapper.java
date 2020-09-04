package gr.movieinsights.service.mapper.genre;

import gr.movieinsights.domain.Genre;
import gr.movieinsights.service.dto.genre.BasicGenreDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface BasicGenreMapper extends EntityMapper<BasicGenreDTO, Genre> {
    @Mapping(target = "tmdbId",ignore = true)
    @Mapping(target = "movies",ignore = true)
    @Mapping(target = "removeMovies",ignore = true)
    Genre toEntity(BasicGenreDTO dto);
    BasicGenreDTO toDto(Genre entity);
}
