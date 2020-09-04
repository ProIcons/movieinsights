package gr.movieinsights.service.dto.vote;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class BasicVoteDTO implements Serializable {
    @NotNull
    private Double average;

    @NotNull
    private Integer votes;

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("average", average)
            .append("votes", votes)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BasicVoteDTO)) return false;

        BasicVoteDTO that = (BasicVoteDTO) o;

        return new EqualsBuilder()
            .append(average, that.average)
            .append(votes, that.votes)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(average)
            .append(votes)
            .toHashCode();
    }
}
