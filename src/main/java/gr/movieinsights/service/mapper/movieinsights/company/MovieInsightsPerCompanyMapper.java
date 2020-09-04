package gr.movieinsights.service.mapper.movieinsights.company;


import gr.movieinsights.domain.MovieInsightsPerCompany;
import gr.movieinsights.service.dto.movieinsights.company.MovieInsightsPerCompanyDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.company.BasicProductionCompanyMapper;
import gr.movieinsights.service.mapper.movieinsights.MovieInsightsMapper;
import gr.movieinsights.service.mapper.movieinsights.year.MovieInsightsPerYearMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MovieInsightsPerCompany} and its DTO {@link MovieInsightsPerCompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = {MovieInsightsMapper.class, MovieInsightsPerYearMapper.class, BasicProductionCompanyMapper.class})
public interface MovieInsightsPerCompanyMapper extends EntityMapper<MovieInsightsPerCompanyDTO, MovieInsightsPerCompany> {
    @Mapping(source = "company", target = "entity")
    MovieInsightsPerCompanyDTO toDto(MovieInsightsPerCompany movieInsightsPerCompany);

    @Mapping(source = "entity", target = "company")
    @Mapping(target = "removeMovieInsightsPerYear",ignore = true)
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
