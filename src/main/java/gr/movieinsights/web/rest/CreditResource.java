package gr.movieinsights.web.rest;

import gr.movieinsights.service.CreditService;
import gr.movieinsights.web.rest.errors.BadRequestAlertException;
import gr.movieinsights.service.dto.CreditDTO;

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
 * REST controller for managing {@link gr.movieinsights.domain.Credit}.
 */
@RestController
@RequestMapping("/api")
public class CreditResource {

    private final Logger log = LoggerFactory.getLogger(CreditResource.class);

    private static final String ENTITY_NAME = "credit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreditService creditService;

    public CreditResource(CreditService creditService) {
        this.creditService = creditService;
    }

    /**
     * {@code POST  /credits} : Create a new credit.
     *
     * @param creditDTO the creditDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new creditDTO, or with status {@code 400 (Bad Request)} if the credit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/credits")
    public ResponseEntity<CreditDTO> createCredit(@Valid @RequestBody CreditDTO creditDTO) throws URISyntaxException {
        log.debug("REST request to save Credit : {}", creditDTO);
        if (creditDTO.getId() != null) {
            throw new BadRequestAlertException("A new credit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditDTO result = creditService.save(creditDTO);
        return ResponseEntity.created(new URI("/api/credits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /credits} : Updates an existing credit.
     *
     * @param creditDTO the creditDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditDTO,
     * or with status {@code 400 (Bad Request)} if the creditDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the creditDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/credits")
    public ResponseEntity<CreditDTO> updateCredit(@Valid @RequestBody CreditDTO creditDTO) throws URISyntaxException {
        log.debug("REST request to update Credit : {}", creditDTO);
        if (creditDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CreditDTO result = creditService.save(creditDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creditDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /credits} : get all the credits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of credits in body.
     */
    @GetMapping("/credits")
    public List<CreditDTO> getAllCredits() {
        log.debug("REST request to get all Credits");
        return creditService.findAll();
    }

    /**
     * {@code GET  /credits/:id} : get the "id" credit.
     *
     * @param id the id of the creditDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the creditDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/credits/{id}")
    public ResponseEntity<CreditDTO> getCredit(@PathVariable Long id) {
        log.debug("REST request to get Credit : {}", id);
        Optional<CreditDTO> creditDTO = creditService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditDTO);
    }

    /**
     * {@code DELETE  /credits/:id} : delete the "id" credit.
     *
     * @param id the id of the creditDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/credits/{id}")
    public ResponseEntity<Void> deleteCredit(@PathVariable Long id) {
        log.debug("REST request to delete Credit : {}", id);
        creditService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/credits?query=:query} : search for the credit corresponding
     * to the query.
     *
     * @param query the query of the credit search.
     * @return the result of the search.
     */
    @GetMapping("/_search/credits")
    public List<CreditDTO> searchCredits(@RequestParam String query) {
        log.debug("REST request to search Credits for query {}", query);
        return creditService.search(query);
    }
}