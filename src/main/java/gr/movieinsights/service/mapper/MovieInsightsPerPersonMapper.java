package gr.movieinsights.service.mapper;


import gr.movieinsights.domain.MovieInsightsPerPerson;
import gr.movieinsights.service.dto.MovieInsightsPerPersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MovieInsightsPerPerson} and its DTO {@link MovieInsightsPerPersonDTO}.
 */
@Mapper(componentModel = "spring", uses = {MovieInsightsMapper.class, PersonMapper.class})
public interface MovieInsightsPerPersonMapper extends EntityMapper<MovieInsightsPerPersonDTO, MovieInsightsPerPerson> {

    @Mapping(source = "movieInsights.id", target = "movieInsightsId")
    @Mapping(source = "person.id", target = "personId")
    MovieInsightsPerPersonDTO toDto(MovieInsightsPerPerson movieInsightsPerPerson);

    @Mapping(source = "movieInsightsId", target = "movieInsights")
    @Mapping(target = "movieInsightsPerYears", ignore = true)
    @Mapping(target = "removeMovieInsightsPerYear", ignore = true)
    @Mapping(source = "personId", target = "person")
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
