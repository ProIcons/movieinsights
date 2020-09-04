package gr.movieinsights.service.util.wrappers.movieinsights.dependent;

import gr.movieinsights.domain.Person;
import gr.movieinsights.domain.enumeration.CreditRole;

public class ActorWrapper extends PersonWrapper {
    public ActorWrapper(Person person) {
        super(person, CreditRole.ACTOR);
    }
}
