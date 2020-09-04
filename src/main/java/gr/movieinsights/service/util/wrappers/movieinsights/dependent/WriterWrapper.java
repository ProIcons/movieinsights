package gr.movieinsights.service.util.wrappers.movieinsights.dependent;

import gr.movieinsights.domain.Person;
import gr.movieinsights.domain.enumeration.CreditRole;

public class WriterWrapper extends PersonWrapper {
    public WriterWrapper(Person person) {
        super(person, CreditRole.WRITER);
    }
}
