package gr.movieinsights.service.mapper.movie;

import gr.movieinsights.domain.Movie;
import gr.movieinsights.service.dto.movie.BasicMovieDTO;
import gr.movieinsights.service.dto.movie.MovieDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.vote.BasicVoteMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Movie} and its DTO {@link MovieDTO}.
 */
@Mapper(componentModel = "spring", uses = {BasicVoteMapper.class})
public interface BasicMovieMapper extends EntityMapper<BasicMovieDTO, Movie> {
    BasicMovieDTO toDto(Movie movie);

    @Mapping(target = "description", ignore = true)
    @Mapping(target = "tagline", ignore = true)
    @Mapping(target = "popularity",ignore = true)
    @Mapping(target = "credits",ignore = true)
    @Mapping(target = "removeCredits",ignore = true)
    @Mapping(target = "companies",ignore = true)
    @Mapping(target = "removeCompanies",ignore = true)
    @Mapping(target = "countries",ignore = true)
    @Mapping(target = "removeCountries",ignore = true)
    @Mapping(target = "genres",ignore = true)
    @Mapping(target = "removeGenres",ignore = true)
    Movie toEntity(BasicMovieDTO basicMovieDTO);
}
