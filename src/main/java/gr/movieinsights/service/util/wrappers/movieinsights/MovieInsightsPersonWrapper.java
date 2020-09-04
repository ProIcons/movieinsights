package gr.movieinsights.service.util.wrappers.movieinsights;

import gr.movieinsights.domain.MovieInsightsPerPerson;
import gr.movieinsights.domain.Person;
import gr.movieinsights.service.util.MovieInsightsProcessor;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.PersonWrapper;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("0000000")
public class MovieInsightsPersonWrapper extends MovieInsightsWrapper<MovieInsightsPerPerson, PersonWrapper, Person> {
    protected MovieInsightsPersonWrapper() {
    }

    public MovieInsightsPersonWrapper(MovieInsightsProcessor processor, PersonWrapper source) {
        super(processor, source);
    }

    @Override
    protected MovieInsightsPerPerson getContainer() {
        MovieInsightsPerPerson movieInsightsPerPerson = new MovieInsightsPerPerson();
        movieInsightsPerPerson.setPerson(getSource().object);
        movieInsightsPerPerson.setAs(getSource().role);
        return movieInsightsPerPerson;
    }
}
