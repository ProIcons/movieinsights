package gr.movieinsights.service;

import gr.movieinsights.domain.Vote;
import gr.movieinsights.repository.VoteRepository;
import gr.movieinsights.service.dto.vote.VoteDTO;
import gr.movieinsights.service.mapper.vote.VoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Vote}.
 */
@Service
@Transactional
public class VoteService {

    private final Logger log = LoggerFactory.getLogger(VoteService.class);

    private final VoteRepository voteRepository;

    private final VoteMapper voteMapper;


    public VoteService(VoteRepository voteRepository, VoteMapper voteMapper) {
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
    }

    /**
     * Save a vote.
     *
     * @param voteDTO the entity to save.
     * @return the persisted entity.
     */
    public VoteDTO save(VoteDTO voteDTO) {
        log.debug("Request to save Vote : {}", voteDTO);
        Vote vote = voteMapper.toEntity(voteDTO);
        vote = voteRepository.save(vote);
        VoteDTO result = voteMapper.toDto(vote);
        return result;
    }

    /**
     * Get all the votes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<VoteDTO> findAll() {
        log.debug("Request to get all Votes");
        return voteRepository.findAll().stream()
            .map(voteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one vote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VoteDTO> findOne(Long id) {
        log.debug("Request to get Vote : {}", id);
        return voteRepository.findById(id)
            .map(voteMapper::toDto);
    }

    /**
     * Delete the vote by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Vote : {}", id);
        voteRepository.deleteById(id);
    }

}
