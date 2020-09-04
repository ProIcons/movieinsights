package gr.movieinsights.service.dto.movieinsights;

import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;

import java.util.List;

public interface IMovieInsightsContainerExtendedDTO extends IMovieInsightsContainerDTO {
    List<MovieInsightsPerYearDTO> getMovieInsightsPerYears();
    void setMovieInsightsPerYears(List<MovieInsightsPerYearDTO> movieInsightsPerYears);
}
