package gr.movieinsights.service.mapper;


import gr.movieinsights.domain.*;
import gr.movieinsights.service.dto.MovieInsightsPerCompanyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MovieInsightsPerCompany} and its DTO {@link MovieInsightsPerCompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = {MovieInsightsMapper.class, ProductionCompanyMapper.class})
public interface MovieInsightsPerCompanyMapper extends EntityMapper<MovieInsightsPerCompanyDTO, MovieInsightsPerCompany> {

    @Mapping(source = "movieInsights.id", target = "movieInsightsId")
    @Mapping(source = "company.id", target = "companyId")
    MovieInsightsPerCompanyDTO toDto(MovieInsightsPerCompany movieInsightsPerCompany);

    @Mapping(source = "movieInsightsId", target = "movieInsights")
    @Mapping(target = "movieInsightsPerYears", ignore = true)
    @Mapping(target = "removeMovieInsightsPerYear", ignore = true)
    @Mapping(source = "companyId", target = "company")
    MovieInsightsPerCompany toEntity(MovieInsightsPerCompanyDTO movieInsightsPerCompanyDTO);

    default MovieInsightsPerCompany fromId(Long id) {
        if (id == null) {
            return null;
        }
        MovieInsightsPerCompany movieInsightsPerCompany = new MovieInsightsPerCompany();
        movieInsightsPerCompany.setId(id);
        return movieInsightsPerCompany;
    }
}