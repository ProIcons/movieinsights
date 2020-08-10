package gr.movieinsights.web.rest;

import gr.movieinsights.service.ProductionCompanyService;
import gr.movieinsights.service.dto.ProductionCompanyDTO;
import gr.movieinsights.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link gr.movieinsights.domain.ProductionCompany}.
 */
@RestController
@RequestMapping("/api")
public class ProductionCompanyResource {

    private final Logger log = LoggerFactory.getLogger(ProductionCompanyResource.class);

    private static final String ENTITY_NAME = "productionCompany";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductionCompanyService productionCompanyService;

    public ProductionCompanyResource(ProductionCompanyService productionCompanyService) {
        this.productionCompanyService = productionCompanyService;
    }

    /**
     * {@code POST  /production-companies} : Create a new productionCompany.
     *
     * @param productionCompanyDTO the productionCompanyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productionCompanyDTO, or with status {@code 400 (Bad Request)} if the productionCompany has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/production-companies")
    public ResponseEntity<ProductionCompanyDTO> createProductionCompany(@Valid @RequestBody ProductionCompanyDTO productionCompanyDTO) throws URISyntaxException {
        log.debug("REST request to save ProductionCompany : {}", productionCompanyDTO);
        if (productionCompanyDTO.getId() != null) {
            throw new BadRequestAlertException("A new productionCompany cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductionCompanyDTO result = productionCompanyService.save(productionCompanyDTO);
        return ResponseEntity.created(new URI("/api/production-companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /production-companies} : Updates an existing productionCompany.
     *
     * @param productionCompanyDTO the productionCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the productionCompanyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productionCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/production-companies")
    public ResponseEntity<ProductionCompanyDTO> updateProductionCompany(@Valid @RequestBody ProductionCompanyDTO productionCompanyDTO) throws URISyntaxException {
        log.debug("REST request to update ProductionCompany : {}", productionCompanyDTO);
        if (productionCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductionCompanyDTO result = productionCompanyService.save(productionCompanyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productionCompanyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /production-companies} : get all the productionCompanies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productionCompanies in body.
     */
    @GetMapping("/production-companies")
    public ResponseEntity<List<ProductionCompanyDTO>> getAllProductionCompanies(Pageable pageable) {
        log.debug("REST request to get a page of ProductionCompanies");
        Page<ProductionCompanyDTO> page = productionCompanyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /production-companies/:id} : get the "id" productionCompany.
     *
     * @param id the id of the productionCompanyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productionCompanyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/production-companies/{id}")
    public ResponseEntity<ProductionCompanyDTO> getProductionCompany(@PathVariable Long id) {
        log.debug("REST request to get ProductionCompany : {}", id);
        Optional<ProductionCompanyDTO> productionCompanyDTO = productionCompanyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productionCompanyDTO);
    }

    /**
     * {@code DELETE  /production-companies/:id} : delete the "id" productionCompany.
     *
     * @param id the id of the productionCompanyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/production-companies/{id}")
    public ResponseEntity<Void> deleteProductionCompany(@PathVariable Long id) {
        log.debug("REST request to delete ProductionCompany : {}", id);
        productionCompanyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/production-companies?query=:query} : search for the productionCompany corresponding
     * to the query.
     *
     * @param query the query of the productionCompany search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/production-companies")
    public ResponseEntity<List<ProductionCompanyDTO>> searchProductionCompanies(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProductionCompanies for query {}", query);
        Page<ProductionCompanyDTO> page = productionCompanyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
