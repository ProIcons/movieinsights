package gr.movieinsights.service.mapper.movieinsights.country;

import gr.movieinsights.domain.MovieInsightsPerCountry;
import gr.movieinsights.service.dto.movieinsights.country.MovieInsightsPerCountryBasicDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.country.BasicProductionCountryMapper;
import gr.movieinsights.service.mapper.movieinsights.MovieInsightsMapper;
import gr.movieinsights.service.mapper.movieinsights.year.MovieInsightsPerYearMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MovieInsightsMapper.class, MovieInsightsPerYearMapper.class, BasicProductionCountryMapper.class})
public interface MovieInsightsPerCountryBasicMapper extends EntityMapper<MovieInsightsPerCountryBasicDTO, MovieInsightsPerCountry> {
    @Override
    @Mapping(source = "entity", target = "country")
    @Mapping(target = "removeMovieInsightsPerYear",ignore = true)
    @Mapping(target = "movieInsightsPerYears", ignore = true)
    MovieInsightsPerCountry toEntity(MovieInsightsPerCountryBasicDTO dto);

    @Override
    @Mapping(source = "country", target = "entity")
    MovieInsightsPerCountryBasicDTO toDto(MovieInsightsPerCountry entity);
}
