package gr.movieinsights.service.mapper.util;

import gr.movieinsights.domain.MovieInsights;
import gr.movieinsights.domain.MovieInsightsPerPerson;
import gr.movieinsights.domain.MovieInsightsPerYear;
import gr.movieinsights.models.MovieInsightsContainerWithYears;
import gr.movieinsights.service.dto.movieinsights.BaseMovieInsightsContainerCategorizedDTO;
import gr.movieinsights.service.dto.movieinsights.BaseMovieInsightsContainerDTO;
import gr.movieinsights.service.dto.movieinsights.person.MovieInsightsPerPersonBasicDTO;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

public class MappingUtils {
    public static <TEntity extends MovieInsightsContainerWithYears> List<List<Object>> calculateMovieInsightsPerYearsToTotals(TEntity entity) {
        List<List<Object>> yearsData = new ArrayList<>();
        for (MovieInsightsPerYear movieInsightsPerYear : entity.getMovieInsightsPerYears()) {
            List<Object> yearData = new ArrayList<>();
            MovieInsights mi = movieInsightsPerYear.getMovieInsights();

            yearData.add(movieInsightsPerYear.getYear());

            yearData.add(mi.getTotalMovies());

            yearData.add(mi.getTotalBudget());
            yearData.add(mi.getTotalBudgetMovies());
            yearData.add(mi.getAverageBudget());

            yearData.add(mi.getTotalRevenue());
            yearData.add(mi.getTotalRevenueMovies());
            yearData.add(mi.getAverageRevenue());

            yearData.add(mi.getTotalRatedMovies());
            yearData.add(mi.getAverageRating());

            yearData.add(mi.getTotalActors());
            yearData.add(mi.getAverageActorCount());

            yearData.add(mi.getTotalDirectors());
            yearData.add(mi.getAverageDirectorCount());

            yearData.add(mi.getTotalProducers());
            yearData.add(mi.getAverageProducerCount());

            yearData.add(mi.getTotalWriters());
            yearData.add(mi.getAverageWriterCount());

            yearData.add(mi.getTotalGenres());
            yearData.add(mi.getAverageGenreCount());

            yearData.add(mi.getTotalProductionCompanies());
            yearData.add(mi.getAverageProductionCompanyCount());

            yearData.add(mi.getTotalProductionCountries());
            yearData.add(mi.getAverageProductionCountryCount());

            yearsData.add(yearData);
        }

        return yearsData;
    }
}
