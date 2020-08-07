package gr.movieinsights.service.mapper;


import gr.movieinsights.domain.*;
import gr.movieinsights.service.dto.MovieInsightsPerCountryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MovieInsightsPerCountry} and its DTO {@link MovieInsightsPerCountryDTO}.
 */
@Mapper(componentModel = "spring", uses = {MovieInsightsMapper.class, ProductionCountryMapper.class})
public interface MovieInsightsPerCountryMapper extends EntityMapper<MovieInsightsPerCountryDTO, MovieInsightsPerCountry> {

    @Mapping(source = "movieInsights.id", target = "movieInsightsId")
    @Mapping(source = "country.id", target = "countryId")
    MovieInsightsPerCountryDTO toDto(MovieInsightsPerCountry movieInsightsPerCountry);

    @Mapping(source = "movieInsightsId", target = "movieInsights")
    @Mapping(target = "movieInsightsPerYears", ignore = true)
    @Mapping(target = "removeMovieInsightsPerYear", ignore = true)
    @Mapping(source = "countryId", target = "country")
    MovieInsightsPerCountry toEntity(MovieInsightsPerCountryDTO movieInsightsPerCountryDTO);

    default MovieInsightsPerCountry fromId(Long id) {
        if (id == null) {
            return null;
        }
        MovieInsightsPerCountry movieInsightsPerCountry = new MovieInsightsPerCountry();
        movieInsightsPerCountry.setId(id);
        return movieInsightsPerCountry;
    }
}
