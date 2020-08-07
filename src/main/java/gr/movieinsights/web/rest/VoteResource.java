package gr.movieinsights.web.rest;

import gr.movieinsights.service.VoteService;
import gr.movieinsights.web.rest.errors.BadRequestAlertException;
import gr.movieinsights.service.dto.VoteDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link gr.movieinsights.domain.Vote}.
 */
@RestController
@RequestMapping("/api")
public class VoteResource {

    private final Logger log = LoggerFactory.getLogger(VoteResource.class);

    private static final String ENTITY_NAME = "vote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoteService voteService;

    public VoteResource(VoteService voteService) {
        this.voteService = voteService;
    }

    /**
     * {@code POST  /votes} : Create a new vote.
     *
     * @param voteDTO the voteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voteDTO, or with status {@code 400 (Bad Request)} if the vote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/votes")
    public ResponseEntity<VoteDTO> createVote(@Valid @RequestBody VoteDTO voteDTO) throws URISyntaxException {
        log.debug("REST request to save Vote : {}", voteDTO);
        if (voteDTO.getId() != null) {
            throw new BadRequestAlertException("A new vote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoteDTO result = voteService.save(voteDTO);
        return ResponseEntity.created(new URI("/api/votes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /votes} : Updates an existing vote.
     *
     * @param voteDTO the voteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voteDTO,
     * or with status {@code 400 (Bad Request)} if the voteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/votes")
    public ResponseEntity<VoteDTO> updateVote(@Valid @RequestBody VoteDTO voteDTO) throws URISyntaxException {
        log.debug("REST request to update Vote : {}", voteDTO);
        if (voteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VoteDTO result = voteService.save(voteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, voteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /votes} : get all the votes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of votes in body.
     */
    @GetMapping("/votes")
    public List<VoteDTO> getAllVotes() {
        log.debug("REST request to get all Votes");
        return voteService.findAll();
    }

    /**
     * {@code GET  /votes/:id} : get the "id" vote.
     *
     * @param id the id of the voteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/votes/{id}")
    public ResponseEntity<VoteDTO> getVote(@PathVariable Long id) {
        log.debug("REST request to get Vote : {}", id);
        Optional<VoteDTO> voteDTO = voteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voteDTO);
    }

    /**
     * {@code DELETE  /votes/:id} : delete the "id" vote.
     *
     * @param id the id of the voteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/votes/{id}")
    public ResponseEntity<Void> deleteVote(@PathVariable Long id) {
        log.debug("REST request to delete Vote : {}", id);
        voteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/votes?query=:query} : search for the vote corresponding
     * to the query.
     *
     * @param query the query of the vote search.
     * @return the result of the search.
     */
    @GetMapping("/_search/votes")
    public List<VoteDTO> searchVotes(@RequestParam String query) {
        log.debug("REST request to search Votes for query {}", query);
        return voteService.search(query);
    }
}
