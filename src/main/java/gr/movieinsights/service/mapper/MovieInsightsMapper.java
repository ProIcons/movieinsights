package gr.movieinsights.service.mapper;


import gr.movieinsights.domain.*;
import gr.movieinsights.service.dto.MovieInsightsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MovieInsights} and its DTO {@link MovieInsightsDTO}.
 */
@Mapper(componentModel = "spring", uses = {MovieMapper.class, GenreMapper.class, PersonMapper.class, ProductionCountryMapper.class, ProductionCompanyMapper.class})
public interface MovieInsightsMapper extends EntityMapper<MovieInsightsDTO, MovieInsights> {

    @Mapping(source = "highestRatedMovie.id", target = "highestRatedMovieId")
    @Mapping(source = "lowestRatedMovie.id", target = "lowestRatedMovieId")
    @Mapping(source = "highestBudgetMovie.id", target = "highestBudgetMovieId")
    @Mapping(source = "lowestBudgetMovie.id", target = "lowestBudgetMovieId")
    @Mapping(source = "highestRevenueMovie.id", target = "highestRevenueMovieId")
    @Mapping(source = "lowestRevenueMovie.id", target = "lowestRevenueMovieId")
    @Mapping(source = "mostPopularGenre.id", target = "mostPopularGenreId")
    @Mapping(source = "mostPopularActor.id", target = "mostPopularActorId")
    @Mapping(source = "mostPopularProducer.id", target = "mostPopularProducerId")
    @Mapping(source = "mostPopularWriter.id", target = "mostPopularWriterId")
    @Mapping(source = "mostPopularDirector.id", target = "mostPopularDirectorId")
    @Mapping(source = "mostPopularProductionCountry.id", target = "mostPopularProductionCountryId")
    @Mapping(source = "mostPopularProductionCompany.id", target = "mostPopularProductionCompanyId")
    MovieInsightsDTO toDto(MovieInsights movieInsights);

    @Mapping(source = "highestRatedMovieId", target = "highestRatedMovie")
    @Mapping(source = "lowestRatedMovieId", target = "lowestRatedMovie")
    @Mapping(source = "highestBudgetMovieId", target = "highestBudgetMovie")
    @Mapping(source = "lowestBudgetMovieId", target = "lowestBudgetMovie")
    @Mapping(source = "highestRevenueMovieId", target = "highestRevenueMovie")
    @Mapping(source = "lowestRevenueMovieId", target = "lowestRevenueMovie")
    @Mapping(source = "mostPopularGenreId", target = "mostPopularGenre")
    @Mapping(source = "mostPopularActorId", target = "mostPopularActor")
    @Mapping(source = "mostPopularProducerId", target = "mostPopularProducer")
    @Mapping(source = "mostPopularWriterId", target = "mostPopularWriter")
    @Mapping(source = "mostPopularDirectorId", target = "mostPopularDirector")
    @Mapping(source = "mostPopularProductionCountryId", target = "mostPopularProductionCountry")
    @Mapping(source = "mostPopularProductionCompanyId", target = "mostPopularProductionCompany")
    MovieInsights toEntity(MovieInsightsDTO movieInsightsDTO);

    default MovieInsights fromId(Long id) {
        if (id == null) {
            return null;
        }
        MovieInsights movieInsights = new MovieInsights();
        movieInsights.setId(id);
        return movieInsights;
    }
}
