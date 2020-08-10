package gr.movieinsights.service.util.wrappers.movieinsights;

import gr.movieinsights.domain.Person;
import gr.movieinsights.domain.enumeration.CreditRole;

public class DirectorWrapper extends PersonWrapper {
    public DirectorWrapper(Person person) {
        super(person, CreditRole.DIRECTOR);
    }
}
