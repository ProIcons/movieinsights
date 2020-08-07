package gr.movieinsights.service.mapper;


import gr.movieinsights.domain.*;
import gr.movieinsights.service.dto.MovieInsightsPerYearDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MovieInsightsPerYear} and its DTO {@link MovieInsightsPerYearDTO}.
 */
@Mapper(componentModel = "spring", uses = {MovieInsightsMapper.class, MovieInsightsPerCountryMapper.class, MovieInsightsPerCompanyMapper.class, MovieInsightsPerPersonMapper.class, MovieInsightsPerGenreMapper.class})
public interface MovieInsightsPerYearMapper extends EntityMapper<MovieInsightsPerYearDTO, MovieInsightsPerYear> {

    @Mapping(source = "movieInsights.id", target = "movieInsightsId")
    @Mapping(source = "movieInsightsPerCountry.id", target = "movieInsightsPerCountryId")
    @Mapping(source = "movieInsightsPerCompany.id", target = "movieInsightsPerCompanyId")
    @Mapping(source = "movieInsightsPerPerson.id", target = "movieInsightsPerPersonId")
    @Mapping(source = "movieInsightsPerGenre.id", target = "movieInsightsPerGenreId")
    MovieInsightsPerYearDTO toDto(MovieInsightsPerYear movieInsightsPerYear);

    @Mapping(source = "movieInsightsId", target = "movieInsights")
    @Mapping(source = "movieInsightsPerCountryId", target = "movieInsightsPerCountry")
    @Mapping(source = "movieInsightsPerCompanyId", target = "movieInsightsPerCompany")
    @Mapping(source = "movieInsightsPerPersonId", target = "movieInsightsPerPerson")
    @Mapping(source = "movieInsightsPerGenreId", target = "movieInsightsPerGenre")
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
