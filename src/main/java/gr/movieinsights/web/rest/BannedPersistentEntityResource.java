package gr.movieinsights.web.rest;

import gr.movieinsights.service.BannedPersistentEntityService;
import gr.movieinsights.web.rest.errors.BadRequestAlertException;
import gr.movieinsights.service.dto.BannedPersistentEntityDTO;

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
 * REST controller for managing {@link gr.movieinsights.domain.BannedPersistentEntity}.
 */
@RestController
@RequestMapping("/api")
public class BannedPersistentEntityResource {

    private final Logger log = LoggerFactory.getLogger(BannedPersistentEntityResource.class);

    private static final String ENTITY_NAME = "bannedPersistentEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BannedPersistentEntityService bannedPersistentEntityService;

    public BannedPersistentEntityResource(BannedPersistentEntityService bannedPersistentEntityService) {
        this.bannedPersistentEntityService = bannedPersistentEntityService;
    }

    /**
     * {@code POST  /banned-persistent-entities} : Create a new bannedPersistentEntity.
     *
     * @param bannedPersistentEntityDTO the bannedPersistentEntityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bannedPersistentEntityDTO, or with status {@code 400 (Bad Request)} if the bannedPersistentEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/banned-persistent-entities")
    public ResponseEntity<BannedPersistentEntityDTO> createBannedPersistentEntity(@Valid @RequestBody BannedPersistentEntityDTO bannedPersistentEntityDTO) throws URISyntaxException {
        log.debug("REST request to save BannedPersistentEntity : {}", bannedPersistentEntityDTO);
        if (bannedPersistentEntityDTO.getId() != null) {
            throw new BadRequestAlertException("A new bannedPersistentEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BannedPersistentEntityDTO result = bannedPersistentEntityService.save(bannedPersistentEntityDTO);
        return ResponseEntity.created(new URI("/api/banned-persistent-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /banned-persistent-entities} : Updates an existing bannedPersistentEntity.
     *
     * @param bannedPersistentEntityDTO the bannedPersistentEntityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bannedPersistentEntityDTO,
     * or with status {@code 400 (Bad Request)} if the bannedPersistentEntityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bannedPersistentEntityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/banned-persistent-entities")
    public ResponseEntity<BannedPersistentEntityDTO> updateBannedPersistentEntity(@Valid @RequestBody BannedPersistentEntityDTO bannedPersistentEntityDTO) throws URISyntaxException {
        log.debug("REST request to update BannedPersistentEntity : {}", bannedPersistentEntityDTO);
        if (bannedPersistentEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BannedPersistentEntityDTO result = bannedPersistentEntityService.save(bannedPersistentEntityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bannedPersistentEntityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /banned-persistent-entities} : get all the bannedPersistentEntities.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bannedPersistentEntities in body.
     */
    @GetMapping("/banned-persistent-entities")
    public List<BannedPersistentEntityDTO> getAllBannedPersistentEntities(@RequestParam(required = false) String filter) {
        if ("bannedentity-is-null".equals(filter)) {
            log.debug("REST request to get all BannedPersistentEntitys where bannedEntity is null");
            return bannedPersistentEntityService.findAllWhereBannedEntityIsNull();
        }
        log.debug("REST request to get all BannedPersistentEntities");
        return bannedPersistentEntityService.findAll();
    }

    /**
     * {@code GET  /banned-persistent-entities/:id} : get the "id" bannedPersistentEntity.
     *
     * @param id the id of the bannedPersistentEntityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bannedPersistentEntityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/banned-persistent-entities/{id}")
    public ResponseEntity<BannedPersistentEntityDTO> getBannedPersistentEntity(@PathVariable Long id) {
        log.debug("REST request to get BannedPersistentEntity : {}", id);
        Optional<BannedPersistentEntityDTO> bannedPersistentEntityDTO = bannedPersistentEntityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bannedPersistentEntityDTO);
    }

    /**
     * {@code DELETE  /banned-persistent-entities/:id} : delete the "id" bannedPersistentEntity.
     *
     * @param id the id of the bannedPersistentEntityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/banned-persistent-entities/{id}")
    public ResponseEntity<Void> deleteBannedPersistentEntity(@PathVariable Long id) {
        log.debug("REST request to delete BannedPersistentEntity : {}", id);
        bannedPersistentEntityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/banned-persistent-entities?query=:query} : search for the bannedPersistentEntity corresponding
     * to the query.
     *
     * @param query the query of the bannedPersistentEntity search.
     * @return the result of the search.
     */
    @GetMapping("/_search/banned-persistent-entities")
    public List<BannedPersistentEntityDTO> searchBannedPersistentEntities(@RequestParam String query) {
        log.debug("REST request to search BannedPersistentEntities for query {}", query);
        return bannedPersistentEntityService.search(query);
    }
}
