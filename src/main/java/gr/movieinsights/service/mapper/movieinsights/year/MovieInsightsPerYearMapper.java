package gr.movieinsights.service.mapper.movieinsights.year;


import gr.movieinsights.domain.MovieInsightsPerYear;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.movieinsights.MovieInsightsMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MovieInsightsPerYear} and its DTO {@link MovieInsightsPerYearDTO}.
 */
@Mapper(componentModel = "spring", uses = {MovieInsightsMapper.class})
public interface MovieInsightsPerYearMapper extends EntityMapper<MovieInsightsPerYearDTO, MovieInsightsPerYear> {
    @Mapping(source = "year", target = "entity")
    MovieInsightsPerYearDTO toDto(MovieInsightsPerYear movieInsightsPerYear);

    @Mapping(source = "entity", target = "year")
    @Mapping(target = "movieInsightsPerCountry",ignore = true)
    @Mapping(target = "movieInsightsPerCompany",ignore = true)
    @Mapping(target = "movieInsightsPerGenre",ignore = true)
    @Mapping(target = "movieInsightsPerPerson",ignore = true)
    @Mapping(target = "movieInsightsGeneral",ignore = true)
    MovieInsightsPerYear toEntity(MovieInsightsPerYearDTO movieInsightsPerYearDTO);

    default MovieInsightsPerYear fromId(Long id) {
        if (id == null) {
            return null;
        }
        MovieInsightsPerYear movieInsightsPerYear = new MovieInsightsPerYear();
        movieInsightsPerYear.setId(id);
        return movieInsightsPerYear;
    }
}
