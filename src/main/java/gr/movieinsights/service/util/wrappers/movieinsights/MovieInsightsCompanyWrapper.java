package gr.movieinsights.service.util.wrappers.movieinsights;

import gr.movieinsights.domain.MovieInsightsPerCompany;
import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.service.util.MovieInsightsProcessor;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.CompanyWrapper;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("000")
public class MovieInsightsCompanyWrapper extends MovieInsightsWrapper<MovieInsightsPerCompany,CompanyWrapper,ProductionCompany> {

    protected MovieInsightsCompanyWrapper() {
    }

    public MovieInsightsCompanyWrapper(MovieInsightsProcessor processor, CompanyWrapper source) {
        super(processor, source, null);
    }

    @Override
    public MovieInsightsPerCompany getContainer() {
        MovieInsightsPerCompany movieInsightsPerCompany = new MovieInsightsPerCompany();
        movieInsightsPerCompany.setCompany(getSource().object);
        return movieInsightsPerCompany;
    }
}

