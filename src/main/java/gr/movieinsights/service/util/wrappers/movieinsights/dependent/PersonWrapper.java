package gr.movieinsights.service.util.wrappers.movieinsights.dependent;

import gr.movieinsights.domain.Person;
import gr.movieinsights.domain.enumeration.CreditRole;
import gr.movieinsights.service.enumeration.MovieInsightsCategory;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Comparator;

public class PersonWrapper extends IdentifiedBaseWrapper<Person> {
    public static Comparator<PersonWrapper> getComparator() {
        return (PersonWrapper pw1, PersonWrapper pw2) -> {
            int result = Double.compare(pw1.getScore(), pw2.getScore());
            if (result == 0)
                return pw1.object.getPopularity().compareTo(pw2.object.getPopularity());
            return result;
        };
    }



    public CreditRole role;

    public PersonWrapper(Person person, CreditRole role) {
        super(person, MovieInsightsCategory.PerPerson);
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PersonWrapper)) return false;

        PersonWrapper that = (PersonWrapper) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(role, that.role)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(role)
            .toHashCode();
    }
}
