package gr.movieinsights.service.mapper.vote;


import gr.movieinsights.domain.Vote;
import gr.movieinsights.service.dto.vote.VoteDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Vote} and its DTO {@link VoteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VoteMapper extends EntityMapper<VoteDTO, Vote> {
    @Override
    Vote toEntity(VoteDTO dto);

    @Override
    VoteDTO toDto(Vote entity);

    default Vote fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vote vote = new Vote();
        vote.setId(id);
        return vote;
    }
}
