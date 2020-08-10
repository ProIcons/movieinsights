package gr.movieinsights.service.util.wrappers.movieinsights;

import gr.movieinsights.domain.Person;
import gr.movieinsights.domain.enumeration.CreditRole;

public class ActorWrapper extends PersonWrapper {
    public ActorWrapper(Person person) {
        super(person, CreditRole.ACTOR);
    }
}
