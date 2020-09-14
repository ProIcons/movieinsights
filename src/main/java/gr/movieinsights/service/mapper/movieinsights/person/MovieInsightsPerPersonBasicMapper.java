package gr.movieinsights.service.mapper.movieinsights.person;

import gr.movieinsights.domain.MovieInsightsPerPerson;
import gr.movieinsights.service.dto.movieinsights.person.MovieInsightsPerPersonBasicDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.movieinsights.MovieInsightsMapper;
import gr.movieinsights.service.mapper.movieinsights.year.MovieInsightsPerYearMapper;
import gr.movieinsights.service.mapper.person.BasicPersonMapper;
import gr.movieinsights.service.mapper.util.MappingUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {MovieInsightsMapper.class, MovieInsightsPerYearMapper.class, BasicPersonMapper.class})
public interface MovieInsightsPerPersonBasicMapper extends EntityMapper<MovieInsightsPerPersonBasicDTO, MovieInsightsPerPerson> {
    @Override
    @Mapping(source = "entity", target = "person")
    @Mapping(target = "removeMovieInsightsPerYear",ignore = true)
    @Mapping(target = "movieInsightsPerYears",ignore = true)
    MovieInsightsPerPerson toEntity(MovieInsightsPerPersonBasicDTO dto);

    @Override
    @Mapping(source = "person", target = "entity")
    MovieInsightsPerPersonBasicDTO toDto(MovieInsightsPerPerson entity);


    @AfterMapping
    default void calculateTotals(MovieInsightsPerPerson movieInsightsPerPerson, @MappingTarget MovieInsightsPerPersonBasicDTO  movieInsightsPerPersonBasicDTO) {
        movieInsightsPerPersonBasicDTO.setYearData(MappingUtils.calculateMovieInsightsPerYearsToTotals(movieInsightsPerPerson));
    }
}
