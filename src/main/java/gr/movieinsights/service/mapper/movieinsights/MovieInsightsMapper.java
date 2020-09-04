package gr.movieinsights.service.mapper.movieinsights;


import gr.movieinsights.domain.MovieInsights;
import gr.movieinsights.service.dto.movieinsights.MovieInsightsDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.company.BasicProductionCompanyMapper;
import gr.movieinsights.service.mapper.country.BasicProductionCountryMapper;
import gr.movieinsights.service.mapper.genre.BasicGenreMapper;
import gr.movieinsights.service.mapper.movie.BasicMovieMapper;
import gr.movieinsights.service.mapper.person.BasicPersonMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link MovieInsights} and its DTO {@link MovieInsightsDTO}.
 */
@Mapper(componentModel = "spring", uses = {BasicMovieMapper.class, BasicGenreMapper.class, BasicPersonMapper.class, BasicProductionCountryMapper.class, BasicProductionCompanyMapper.class})
public interface MovieInsightsMapper extends EntityMapper<MovieInsightsDTO, MovieInsights> {
    @Override
    MovieInsights toEntity(MovieInsightsDTO dto);

    @Override
    MovieInsightsDTO toDto(MovieInsights entity);

    default MovieInsights fromId(Long id) {
        if (id == null) {
            return null;
        }
        MovieInsights movieInsights = new MovieInsights();
        movieInsights.setId(id);
        return movieInsights;
    }
}
