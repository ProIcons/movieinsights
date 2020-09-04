package gr.movieinsights.service.dto.person;

import gr.movieinsights.service.dto.credit.BasicCreditDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Lob;
import java.time.LocalDate;
import java.util.List;

/**
 * A DTO for the {@link gr.movieinsights.domain.Person} entity.
 */
public class PersonDTO extends BasicPersonDTO {

    private Double popularity;

    private List<BasicCreditDTO> credits;

    @Lob
    private String biography;

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<BasicCreditDTO> getCredits() {
        return credits;
    }

    public void setCredits(List<BasicCreditDTO> credits) {
        this.credits = credits;
    }

    private LocalDate birthDay;

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PersonDTO)) return false;

        PersonDTO personDTO = (PersonDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(popularity, personDTO.popularity)
            .append(biography, personDTO.biography)
            .append(birthDay,personDTO.birthDay)
            .append(credits,personDTO.credits)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(popularity)
            .append(biography)
            .append(birthDay)
            .append(credits)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("tmdbId", getTmdbId())
            .append("name", getName())
            .append("imdbId", getImdbId())
            .append("birthDay", getBirthDay())
            .append("profilePath", getProfilePath())
            .append("popularity", popularity)
            .append("biography", biography)
            .append("credits",credits)
            .toString();
    }
}
