package gr.movieinsights.service.dto.movieinsights.general;

import gr.movieinsights.service.dto.movieinsights.IMovieInsightsContainerExtendedDTO;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;

import java.util.List;

public class MovieInsightsGeneralDTO extends MovieInsightsGeneralBasicDTO implements IMovieInsightsContainerExtendedDTO {
    @Override
    public List<MovieInsightsPerYearDTO> getMovieInsightsPerYears() {
        return this.movieInsightsPerYears;
    }

    @Override
    public void setMovieInsightsPerYears(List<MovieInsightsPerYearDTO> movieInsightsPerYears) {
        this.movieInsightsPerYears = movieInsightsPerYears;
    }
}
