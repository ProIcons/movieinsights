package gr.movieinsights.service.mapper.movieinsights.company;

import gr.movieinsights.domain.MovieInsightsPerCompany;
import gr.movieinsights.service.dto.movieinsights.company.MovieInsightsPerCompanyBasicDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.company.BasicProductionCompanyMapper;
import gr.movieinsights.service.mapper.movieinsights.MovieInsightsMapper;
import gr.movieinsights.service.mapper.movieinsights.year.MovieInsightsPerYearMapper;
import gr.movieinsights.service.mapper.util.MappingUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {MovieInsightsMapper.class, MovieInsightsPerYearMapper.class, BasicProductionCompanyMapper.class})
public interface MovieInsightsPerCompanyBasicMapper extends EntityMapper<MovieInsightsPerCompanyBasicDTO, MovieInsightsPerCompany> {
    @Override
    @Mapping(source = "entity", target = "company")
    @Mapping(target = "removeMovieInsightsPerYear", ignore = true)
    MovieInsightsPerCompany toEntity(MovieInsightsPerCompanyBasicDTO dto);

    @Override
    @Mapping(source = "company", target = "entity")
    MovieInsightsPerCompanyBasicDTO toDto(MovieInsightsPerCompany entity);

    @AfterMapping
    default void calculateTotals(MovieInsightsPerCompany movieInsightsPerEntity, @MappingTarget MovieInsightsPerCompanyBasicDTO movieInsightsPerEntityBasicDTO) {
        movieInsightsPerEntityBasicDTO.setYearData(MappingUtils.calculateMovieInsightsPerYearsToTotals(movieInsightsPerEntity));
    }
}
