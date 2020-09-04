package gr.movieinsights.service.mapper.vote;

import gr.movieinsights.domain.Vote;
import gr.movieinsights.service.dto.vote.BasicVoteDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface BasicVoteMapper extends EntityMapper<BasicVoteDTO, Vote> {
    @Override
    @Mapping(target = "id",ignore = true)
    Vote toEntity(BasicVoteDTO dto);

    @Override
    BasicVoteDTO toDto(Vote entity);
}
