package gr.movieinsights.web.rest;

import gr.movieinsights.service.ProductionCountryService;
import gr.movieinsights.web.rest.errors.BadRequestAlertException;
import gr.movieinsights.service.dto.ProductionCountryDTO;

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
 * REST controller for managing {@link gr.movieinsights.domain.ProductionCountry}.
 */
@RestController
@RequestMapping("/api")
public class ProductionCountryResource {

    private final Logger log = LoggerFactory.getLogger(ProductionCountryResource.class);

    private static final String ENTITY_NAME = "productionCountry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductionCountryService productionCountryService;

    public ProductionCountryResource(ProductionCountryService productionCountryService) {
        this.productionCountryService = productionCountryService;
    }

    /**
     * {@code POST  /production-countries} : Create a new productionCountry.
     *
     * @param productionCountryDTO the productionCountryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productionCountryDTO, or with status {@code 400 (Bad Request)} if the productionCountry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/production-countries")
    public ResponseEntity<ProductionCountryDTO> createProductionCountry(@Valid @RequestBody ProductionCountryDTO productionCountryDTO) throws URISyntaxException {
        log.debug("REST request to save ProductionCountry : {}", productionCountryDTO);
        if (productionCountryDTO.getId() != null) {
            throw new BadRequestAlertException("A new productionCountry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductionCountryDTO result = productionCountryService.save(productionCountryDTO);
        return ResponseEntity.created(new URI("/api/production-countries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /production-countries} : Updates an existing productionCountry.
     *
     * @param productionCountryDTO the productionCountryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionCountryDTO,
     * or with status {@code 400 (Bad Request)} if the productionCountryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productionCountryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/production-countries")
    public ResponseEntity<ProductionCountryDTO> updateProductionCountry(@Valid @RequestBody ProductionCountryDTO productionCountryDTO) throws URISyntaxException {
        log.debug("REST request to update ProductionCountry : {}", productionCountryDTO);
        if (productionCountryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductionCountryDTO result = productionCountryService.save(productionCountryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productionCountryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /production-countries} : get all the productionCountries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productionCountries in body.
     */
    @GetMapping("/production-countries")
    public List<ProductionCountryDTO> getAllProductionCountries() {
        log.debug("REST request to get all ProductionCountries");
        return productionCountryService.findAll();
    }

    /**
     * {@code GET  /production-countries/:id} : get the "id" productionCountry.
     *
     * @param id the id of the productionCountryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productionCountryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/production-countries/{id}")
    public ResponseEntity<ProductionCountryDTO> getProductionCountry(@PathVariable Long id) {
        log.debug("REST request to get ProductionCountry : {}", id);
        Optional<ProductionCountryDTO> productionCountryDTO = productionCountryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productionCountryDTO);
    }

    /**
     * {@code DELETE  /production-countries/:id} : delete the "id" productionCountry.
     *
     * @param id the id of the productionCountryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/production-countries/{id}")
    public ResponseEntity<Void> deleteProductionCountry(@PathVariable Long id) {
        log.debug("REST request to delete ProductionCountry : {}", id);
        productionCountryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/production-countries?query=:query} : search for the productionCountry corresponding
     * to the query.
     *
     * @param query the query of the productionCountry search.
     * @return the result of the search.
     */
    @GetMapping("/_search/production-countries")
    public List<ProductionCountryDTO> searchProductionCountries(@RequestParam String query) {
        log.debug("REST request to search ProductionCountries for query {}", query);
        return productionCountryService.search(query);
    }
}
