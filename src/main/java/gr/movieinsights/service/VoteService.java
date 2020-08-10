package gr.movieinsights.service;

import gr.movieinsights.domain.Vote;
import gr.movieinsights.repository.VoteRepository;
import gr.movieinsights.repository.search.VoteSearchRepository;
import gr.movieinsights.service.dto.VoteDTO;
import gr.movieinsights.service.mapper.VoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Vote}.
 */
@Service
@Transactional
public class VoteService {

    private final Logger log = LoggerFactory.getLogger(VoteService.class);

    private final VoteRepository voteRepository;

    private final VoteMapper voteMapper;

    private final VoteSearchRepository voteSearchRepository;

    public VoteService(VoteRepository voteRepository, VoteMapper voteMapper, VoteSearchRepository voteSearchRepository) {
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
        this.voteSearchRepository = voteSearchRepository;
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
        voteSearchRepository.save(vote);
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
        voteSearchRepository.deleteById(id);
    }

    /**
     * Search for the vote corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<VoteDTO> search(String query) {
        log.debug("Request to search Votes for query {}", query);
        return StreamSupport
            .stream(voteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(voteMapper::toDto)
            .collect(Collectors.toList());
    }
}
