package gr.movieinsights.web.rest;

import gr.movieinsights.service.BannedEntityService;
import gr.movieinsights.service.dto.BannedEntityDTO;
import gr.movieinsights.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link gr.movieinsights.domain.BannedEntity}.
 */
@RestController
@RequestMapping("/api")
public class BannedEntityResource {

    private final Logger log = LoggerFactory.getLogger(BannedEntityResource.class);

    private static final String ENTITY_NAME = "bannedEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BannedEntityService bannedEntityService;

    public BannedEntityResource(BannedEntityService bannedEntityService) {
        this.bannedEntityService = bannedEntityService;
    }

    /**
     * {@code POST  /banned-entities} : Create a new bannedEntity.
     *
     * @param bannedEntityDTO the bannedEntityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bannedEntityDTO, or with status {@code 400 (Bad Request)} if the bannedEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/banned-entities")
    public ResponseEntity<BannedEntityDTO> createBannedEntity(@Valid @RequestBody BannedEntityDTO bannedEntityDTO) throws URISyntaxException {
        log.debug("REST request to save BannedEntity : {}", bannedEntityDTO);
        if (bannedEntityDTO.getId() != null) {
            throw new BadRequestAlertException("A new bannedEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BannedEntityDTO result = bannedEntityService.save(bannedEntityDTO);
        return ResponseEntity.created(new URI("/api/banned-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /banned-entities} : Updates an existing bannedEntity.
     *
     * @param bannedEntityDTO the bannedEntityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bannedEntityDTO,
     * or with status {@code 400 (Bad Request)} if the bannedEntityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bannedEntityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/banned-entities")
    public ResponseEntity<BannedEntityDTO> updateBannedEntity(@Valid @RequestBody BannedEntityDTO bannedEntityDTO) throws URISyntaxException {
        log.debug("REST request to update BannedEntity : {}", bannedEntityDTO);
        if (bannedEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BannedEntityDTO result = bannedEntityService.save(bannedEntityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bannedEntityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /banned-entities} : get all the bannedEntities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bannedEntities in body.
     */
    @GetMapping("/banned-entities")
    public List<BannedEntityDTO> getAllBannedEntities() {
        log.debug("REST request to get all BannedEntities");
        return bannedEntityService.findAll();
    }

    /**
     * {@code GET  /banned-entities/:id} : get the "id" bannedEntity.
     *
     * @param id the id of the bannedEntityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bannedEntityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/banned-entities/{id}")
    public ResponseEntity<BannedEntityDTO> getBannedEntity(@PathVariable Long id) {
        log.debug("REST request to get BannedEntity : {}", id);
        Optional<BannedEntityDTO> bannedEntityDTO = bannedEntityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bannedEntityDTO);
    }

    /**
     * {@code DELETE  /banned-entities/:id} : delete the "id" bannedEntity.
     *
     * @param id the id of the bannedEntityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/banned-entities/{id}")
    public ResponseEntity<Void> deleteBannedEntity(@PathVariable Long id) {
        log.debug("REST request to delete BannedEntity : {}", id);
        bannedEntityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/banned-entities?query=:query} : search for the bannedEntity corresponding
     * to the query.
     *
     * @param query the query of the bannedEntity search.
     * @return the result of the search.
     */
    @GetMapping("/_search/banned-entities")
    public List<BannedEntityDTO> searchBannedEntities(@RequestParam String query) {
        log.debug("REST request to search BannedEntities for query {}", query);
        return bannedEntityService.search(query);
    }
}
