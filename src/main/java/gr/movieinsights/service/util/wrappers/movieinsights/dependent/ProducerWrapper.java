package gr.movieinsights.service.util.wrappers.movieinsights.dependent;

import gr.movieinsights.domain.Person;
import gr.movieinsights.domain.enumeration.CreditRole;

public class ProducerWrapper extends PersonWrapper {
    public ProducerWrapper(Person person) {
        super(person, CreditRole.PRODUCER);
    }
}
