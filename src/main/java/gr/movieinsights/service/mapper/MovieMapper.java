package gr.movieinsights.service.mapper;


import gr.movieinsights.domain.*;
import gr.movieinsights.service.dto.MovieDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Movie} and its DTO {@link MovieDTO}.
 */
@Mapper(componentModel = "spring", uses = {VoteMapper.class, ProductionCompanyMapper.class, ProductionCountryMapper.class, GenreMapper.class})
public interface MovieMapper extends EntityMapper<MovieDTO, Movie> {

    @Mapping(source = "vote.id", target = "voteId")
    MovieDTO toDto(Movie movie);

    @Mapping(source = "voteId", target = "vote")
    @Mapping(target = "credits", ignore = true)
    @Mapping(target = "removeCredits", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "removeImages", ignore = true)
    @Mapping(target = "banReasons", ignore = true)
    @Mapping(target = "removeBanReasons", ignore = true)
    @Mapping(target = "removeCompanies", ignore = true)
    @Mapping(target = "removeCountries", ignore = true)
    @Mapping(target = "removeGenres", ignore = true)
    Movie toEntity(MovieDTO movieDTO);

    default Movie fromId(Long id) {
        if (id == null) {
            return null;
        }
        Movie movie = new Movie();
        movie.setId(id);
        return movie;
    }
}
