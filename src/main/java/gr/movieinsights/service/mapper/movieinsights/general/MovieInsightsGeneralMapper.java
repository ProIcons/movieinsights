package gr.movieinsights.service.mapper.movieinsights.general;

import gr.movieinsights.domain.MovieInsightsGeneral;
import gr.movieinsights.domain.MovieInsightsPerCountry;
import gr.movieinsights.service.dto.movieinsights.country.MovieInsightsPerCountryDTO;
import gr.movieinsights.service.dto.movieinsights.general.MovieInsightsGeneralDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.movieinsights.MovieInsightsMapper;
import gr.movieinsights.service.mapper.movieinsights.year.MovieInsightsPerYearMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MovieInsightsPerCountry} and its DTO {@link MovieInsightsPerCountryDTO}.
 */
@Mapper(componentModel = "spring", uses = {MovieInsightsMapper.class, MovieInsightsPerYearMapper.class})
public interface MovieInsightsGeneralMapper extends EntityMapper<MovieInsightsGeneralDTO, MovieInsightsGeneral> {
    @Override
    @Mapping(target = "removeMovieInsightsPerYear",ignore = true)
    MovieInsightsGeneral toEntity(MovieInsightsGeneralDTO dto);

    @Override
    MovieInsightsGeneralDTO toDto(MovieInsightsGeneral entity);

    default MovieInsightsGeneral fromId(Long id) {
        if (id == null) {
            return null;
        }
        MovieInsightsGeneral movieInsightsGeneral = new MovieInsightsGeneral();
        movieInsightsGeneral.setId(id);
        return movieInsightsGeneral;
    }
}
