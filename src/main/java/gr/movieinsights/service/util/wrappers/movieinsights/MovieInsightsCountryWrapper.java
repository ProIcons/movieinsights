package gr.movieinsights.service.util.wrappers.movieinsights;

import gr.movieinsights.domain.MovieInsightsPerCountry;
import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.service.util.MovieInsightsProcessor;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.CountryWrapper;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("0000")
public class MovieInsightsCountryWrapper extends MovieInsightsWrapper<MovieInsightsPerCountry, CountryWrapper, ProductionCountry> {

    protected MovieInsightsCountryWrapper() {
    }

    public MovieInsightsCountryWrapper(MovieInsightsProcessor processor, CountryWrapper source) {
        super(processor, source);
    }

    @Override
    protected MovieInsightsPerCountry getContainer() {
        MovieInsightsPerCountry movieInsightsPerCountry = new MovieInsightsPerCountry();
        movieInsightsPerCountry.setCountry(getSource().object);
        return movieInsightsPerCountry;
    }
}
