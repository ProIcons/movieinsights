package gr.movieinsights.service.mapper.movieinsights.genre;

import gr.movieinsights.domain.MovieInsightsPerGenre;
import gr.movieinsights.service.dto.movieinsights.genre.MovieInsightsPerGenreBasicDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.genre.BasicGenreMapper;
import gr.movieinsights.service.mapper.movieinsights.MovieInsightsMapper;
import gr.movieinsights.service.mapper.movieinsights.year.MovieInsightsPerYearMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MovieInsightsMapper.class, MovieInsightsPerYearMapper.class, BasicGenreMapper.class})
public interface MovieInsightsPerGenreBasicMapper extends EntityMapper<MovieInsightsPerGenreBasicDTO, MovieInsightsPerGenre> {
    @Override
    @Mapping(source = "entity", target = "genre")
    @Mapping(target = "removeMovieInsightsPerYear", ignore = true)
    MovieInsightsPerGenre toEntity(MovieInsightsPerGenreBasicDTO dto);

    @Override
    @Mapping(source = "genre", target = "entity")
    MovieInsightsPerGenreBasicDTO toDto(MovieInsightsPerGenre entity);
}
