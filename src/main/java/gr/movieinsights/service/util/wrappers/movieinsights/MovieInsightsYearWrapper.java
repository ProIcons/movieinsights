package gr.movieinsights.service.util.wrappers.movieinsights;

import gr.movieinsights.domain.MovieInsightsContainer;
import gr.movieinsights.domain.MovieInsightsPerYear;
import gr.movieinsights.service.util.MovieInsightsProcessor;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.BaseWrapper;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.YearWrapper;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("00000000")
public class MovieInsightsYearWrapper extends MovieInsightsWrapper<MovieInsightsPerYear, YearWrapper,Integer> {


    protected MovieInsightsYearWrapper() {
    }

    public MovieInsightsYearWrapper(MovieInsightsProcessor processor, YearWrapper source, IParameterizedMovieInsightsWrapper<? extends MovieInsightsContainer, ? extends BaseWrapper<?>, ?> master) {
        super(processor, source, master);
    }

    @Override
    protected MovieInsightsPerYear getContainer() {
        MovieInsightsPerYear movieInsightsPerYear = new MovieInsightsPerYear();
        movieInsightsPerYear.setYear(getSource().object);
        return movieInsightsPerYear;
    }
}
