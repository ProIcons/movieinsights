package gr.movieinsights.service.mapper.movieinsights.person;


import gr.movieinsights.domain.MovieInsightsPerPerson;
import gr.movieinsights.service.dto.movieinsights.person.MovieInsightsPerPersonDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.movieinsights.MovieInsightsMapper;
import gr.movieinsights.service.mapper.movieinsights.year.MovieInsightsPerYearMapper;
import gr.movieinsights.service.mapper.person.BasicPersonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MovieInsightsPerPerson} and its DTO {@link MovieInsightsPerPersonDTO}.
 */
@Mapper(componentModel = "spring", uses = {MovieInsightsMapper.class, MovieInsightsPerYearMapper.class, BasicPersonMapper.class})
public interface MovieInsightsPerPersonMapper extends EntityMapper<MovieInsightsPerPersonDTO, MovieInsightsPerPerson> {

    @Mapping(source = "person", target = "entity")
    MovieInsightsPerPersonDTO toDto(MovieInsightsPerPerson movieInsightsPerPerson);

    @Mapping(source = "entity", target = "person")
    @Mapping(target = "removeMovieInsightsPerYear",ignore = true)
    MovieInsightsPerPerson toEntity(MovieInsightsPerPersonDTO movieInsightsPerPersonDTO);

    default MovieInsightsPerPerson fromId(Long id) {
        if (id == null) {
            return null;
        }
        MovieInsightsPerPerson movieInsightsPerPerson = new MovieInsightsPerPerson();
        movieInsightsPerPerson.setId(id);
        return movieInsightsPerPerson;
    }
}
