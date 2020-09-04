package gr.movieinsights.service.mapper.movie;


import gr.movieinsights.domain.Movie;
import gr.movieinsights.service.dto.movie.MovieDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.company.BasicProductionCompanyMapper;
import gr.movieinsights.service.mapper.country.BasicProductionCountryMapper;
import gr.movieinsights.service.mapper.credit.DependentByMovieCreditMapper;
import gr.movieinsights.service.mapper.genre.BasicGenreMapper;
import gr.movieinsights.service.mapper.vote.VoteMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Movie} and its DTO {@link MovieDTO}.
 */
@Mapper(componentModel = "spring", uses = {
    DependentByMovieCreditMapper.class,
    VoteMapper.class,
    BasicProductionCompanyMapper.class,
    BasicProductionCountryMapper.class,
    BasicGenreMapper.class
})
public interface MovieMapper extends EntityMapper<MovieDTO, Movie> {
    MovieDTO toDto(Movie movie);

    @Mapping(target = "removeCredits", ignore = true)
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
