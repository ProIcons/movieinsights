package gr.movieinsights.service.dto.vote;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A DTO for the {@link gr.movieinsights.domain.Vote} entity.
 */
public class VoteDTO extends BasicVoteDTO {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("average", getAverage())
            .append("votes", getVotes())
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof VoteDTO)) return false;

        VoteDTO voteDTO = (VoteDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(id, voteDTO.id)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(id)
            .toHashCode();
    }
}
