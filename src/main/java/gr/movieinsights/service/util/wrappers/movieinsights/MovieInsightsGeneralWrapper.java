package gr.movieinsights.service.util.wrappers.movieinsights;

import gr.movieinsights.domain.MovieInsightsGeneral;
import gr.movieinsights.service.util.MovieInsightsProcessor;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.GeneralWrapper;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("00000")
public class MovieInsightsGeneralWrapper extends MovieInsightsWrapper<MovieInsightsGeneral, GeneralWrapper, Object> {
    protected MovieInsightsGeneralWrapper() {
    }

    public MovieInsightsGeneralWrapper(MovieInsightsProcessor processor, GeneralWrapper source) {
        super(processor, source);
    }

    @Override
    protected MovieInsightsGeneral getContainer() {
        return new MovieInsightsGeneral();
    }
}
