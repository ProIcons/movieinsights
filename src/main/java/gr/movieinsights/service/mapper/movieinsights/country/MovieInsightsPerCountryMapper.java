package gr.movieinsights.service.mapper.movieinsights.country;


import gr.movieinsights.domain.MovieInsightsPerCountry;
import gr.movieinsights.service.dto.movieinsights.country.MovieInsightsPerCountryDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.country.BasicProductionCountryMapper;
import gr.movieinsights.service.mapper.movieinsights.MovieInsightsMapper;
import gr.movieinsights.service.mapper.movieinsights.year.MovieInsightsPerYearMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MovieInsightsPerCountry} and its DTO {@link MovieInsightsPerCountryDTO}.
 */
@Mapper(componentModel = "spring", uses = {MovieInsightsMapper.class, MovieInsightsPerYearMapper.class, BasicProductionCountryMapper.class})
public interface MovieInsightsPerCountryMapper extends EntityMapper<MovieInsightsPerCountryDTO, MovieInsightsPerCountry> {

   @Mapping(source = "country", target = "entity")
    MovieInsightsPerCountryDTO toDto(MovieInsightsPerCountry movieInsightsPerCountry);

   @Mapping(source = "entity", target = "country")
   @Mapping(target = "removeMovieInsightsPerYear",ignore = true)
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
