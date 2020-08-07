package gr.movieinsights.web.rest;

import gr.movieinsights.service.MovieInsightsPerCompanyService;
import gr.movieinsights.web.rest.errors.BadRequestAlertException;
import gr.movieinsights.service.dto.MovieInsightsPerCompanyDTO;

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
 * REST controller for managing {@link gr.movieinsights.domain.MovieInsightsPerCompany}.
 */
@RestController
@RequestMapping("/api")
public class MovieInsightsPerCompanyResource {

    private final Logger log = LoggerFactory.getLogger(MovieInsightsPerCompanyResource.class);

    private static final String ENTITY_NAME = "movieInsightsPerCompany";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovieInsightsPerCompanyService movieInsightsPerCompanyService;

    public MovieInsightsPerCompanyResource(MovieInsightsPerCompanyService movieInsightsPerCompanyService) {
        this.movieInsightsPerCompanyService = movieInsightsPerCompanyService;
    }

    /**
     * {@code POST  /movie-insights-per-companies} : Create a new movieInsightsPerCompany.
     *
     * @param movieInsightsPerCompanyDTO the movieInsightsPerCompanyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movieInsightsPerCompanyDTO, or with status {@code 400 (Bad Request)} if the movieInsightsPerCompany has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/movie-insights-per-companies")
    public ResponseEntity<MovieInsightsPerCompanyDTO> createMovieInsightsPerCompany(@Valid @RequestBody MovieInsightsPerCompanyDTO movieInsightsPerCompanyDTO) throws URISyntaxException {
        log.debug("REST request to save MovieInsightsPerCompany : {}", movieInsightsPerCompanyDTO);
        if (movieInsightsPerCompanyDTO.getId() != null) {
            throw new BadRequestAlertException("A new movieInsightsPerCompany cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovieInsightsPerCompanyDTO result = movieInsightsPerCompanyService.save(movieInsightsPerCompanyDTO);
        return ResponseEntity.created(new URI("/api/movie-insights-per-companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /movie-insights-per-companies} : Updates an existing movieInsightsPerCompany.
     *
     * @param movieInsightsPerCompanyDTO the movieInsightsPerCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movieInsightsPerCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the movieInsightsPerCompanyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movieInsightsPerCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/movie-insights-per-companies")
    public ResponseEntity<MovieInsightsPerCompanyDTO> updateMovieInsightsPerCompany(@Valid @RequestBody MovieInsightsPerCompanyDTO movieInsightsPerCompanyDTO) throws URISyntaxException {
        log.debug("REST request to update MovieInsightsPerCompany : {}", movieInsightsPerCompanyDTO);
        if (movieInsightsPerCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovieInsightsPerCompanyDTO result = movieInsightsPerCompanyService.save(movieInsightsPerCompanyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, movieInsightsPerCompanyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /movie-insights-per-companies} : get all the movieInsightsPerCompanies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movieInsightsPerCompanies in body.
     */
    @GetMapping("/movie-insights-per-companies")
    public List<MovieInsightsPerCompanyDTO> getAllMovieInsightsPerCompanies() {
        log.debug("REST request to get all MovieInsightsPerCompanies");
        return movieInsightsPerCompanyService.findAll();
    }

    /**
     * {@code GET  /movie-insights-per-companies/:id} : get the "id" movieInsightsPerCompany.
     *
     * @param id the id of the movieInsightsPerCompanyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerCompanyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/movie-insights-per-companies/{id}")
    public ResponseEntity<MovieInsightsPerCompanyDTO> getMovieInsightsPerCompany(@PathVariable Long id) {
        log.debug("REST request to get MovieInsightsPerCompany : {}", id);
        Optional<MovieInsightsPerCompanyDTO> movieInsightsPerCompanyDTO = movieInsightsPerCompanyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movieInsightsPerCompanyDTO);
    }

    /**
     * {@code DELETE  /movie-insights-per-companies/:id} : delete the "id" movieInsightsPerCompany.
     *
     * @param id the id of the movieInsightsPerCompanyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/movie-insights-per-companies/{id}")
    public ResponseEntity<Void> deleteMovieInsightsPerCompany(@PathVariable Long id) {
        log.debug("REST request to delete MovieInsightsPerCompany : {}", id);
        movieInsightsPerCompanyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/movie-insights-per-companies?query=:query} : search for the movieInsightsPerCompany corresponding
     * to the query.
     *
     * @param query the query of the movieInsightsPerCompany search.
     * @return the result of the search.
     */
    @GetMapping("/_search/movie-insights-per-companies")
    public List<MovieInsightsPerCompanyDTO> searchMovieInsightsPerCompanies(@RequestParam String query) {
        log.debug("REST request to search MovieInsightsPerCompanies for query {}", query);
        return movieInsightsPerCompanyService.search(query);
    }
}
