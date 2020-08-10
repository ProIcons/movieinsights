package gr.movieinsights.service.mapper;


import gr.movieinsights.domain.Vote;
import gr.movieinsights.service.dto.VoteDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Vote} and its DTO {@link VoteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VoteMapper extends EntityMapper<VoteDTO, Vote> {



    default Vote fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vote vote = new Vote();
        vote.setId(id);
        return vote;
    }
}
