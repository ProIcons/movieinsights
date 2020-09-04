package gr.movieinsights.service.dto.credit;

import gr.movieinsights.service.dto.movie.BasicMovieDTO;
import gr.movieinsights.service.dto.person.BasicPersonDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

public class BasicCreditDTO extends BaseCreditDTO {
    @NotNull
    private BasicPersonDTO person;
    @NotNull
    private BasicMovieDTO movie;


    public BasicPersonDTO getPerson() {
        return person;
    }

    public void setPerson(BasicPersonDTO person) {
        this.person = person;
    }

    public BasicMovieDTO getMovie() {
        return movie;
    }

    public void setMovie(BasicMovieDTO movie) {
        this.movie = movie;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BasicCreditDTO)) return false;

        BasicCreditDTO that = (BasicCreditDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(person, that.person)
            .append(movie, that.movie)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(person)
            .append(movie)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("person", person)
            .append("movie", movie)
            .append("role", getRole())
            .toString();
    }
}
