package gr.movieinsights.web.rest;

import gr.movieinsights.service.MovieInsightsPerCountryService;
import gr.movieinsights.web.rest.errors.BadRequestAlertException;
import gr.movieinsights.service.dto.MovieInsightsPerCountryDTO;

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
 * REST controller for managing {@link gr.movieinsights.domain.MovieInsightsPerCountry}.
 */
@RestController
@RequestMapping("/api")
public class MovieInsightsPerCountryResource {

    private final Logger log = LoggerFactory.getLogger(MovieInsightsPerCountryResource.class);

    private static final String ENTITY_NAME = "movieInsightsPerCountry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovieInsightsPerCountryService movieInsightsPerCountryService;

    public MovieInsightsPerCountryResource(MovieInsightsPerCountryService movieInsightsPerCountryService) {
        this.movieInsightsPerCountryService = movieInsightsPerCountryService;
    }

    /**
     * {@code POST  /movie-insights-per-countries} : Create a new movieInsightsPerCountry.
     *
     * @param movieInsightsPerCountryDTO the movieInsightsPerCountryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movieInsightsPerCountryDTO, or with status {@code 400 (Bad Request)} if the movieInsightsPerCountry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/movie-insights-per-countries")
    public ResponseEntity<MovieInsightsPerCountryDTO> createMovieInsightsPerCountry(@Valid @RequestBody MovieInsightsPerCountryDTO movieInsightsPerCountryDTO) throws URISyntaxException {
        log.debug("REST request to save MovieInsightsPerCountry : {}", movieInsightsPerCountryDTO);
        if (movieInsightsPerCountryDTO.getId() != null) {
            throw new BadRequestAlertException("A new movieInsightsPerCountry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovieInsightsPerCountryDTO result = movieInsightsPerCountryService.save(movieInsightsPerCountryDTO);
        return ResponseEntity.created(new URI("/api/movie-insights-per-countries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /movie-insights-per-countries} : Updates an existing movieInsightsPerCountry.
     *
     * @param movieInsightsPerCountryDTO the movieInsightsPerCountryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movieInsightsPerCountryDTO,
     * or with status {@code 400 (Bad Request)} if the movieInsightsPerCountryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movieInsightsPerCountryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/movie-insights-per-countries")
    public ResponseEntity<MovieInsightsPerCountryDTO> updateMovieInsightsPerCountry(@Valid @RequestBody MovieInsightsPerCountryDTO movieInsightsPerCountryDTO) throws URISyntaxException {
        log.debug("REST request to update MovieInsightsPerCountry : {}", movieInsightsPerCountryDTO);
        if (movieInsightsPerCountryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovieInsightsPerCountryDTO result = movieInsightsPerCountryService.save(movieInsightsPerCountryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, movieInsightsPerCountryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /movie-insights-per-countries} : get all the movieInsightsPerCountries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movieInsightsPerCountries in body.
     */
    @GetMapping("/movie-insights-per-countries")
    public List<MovieInsightsPerCountryDTO> getAllMovieInsightsPerCountries() {
        log.debug("REST request to get all MovieInsightsPerCountries");
        return movieInsightsPerCountryService.findAll();
    }

    /**
     * {@code GET  /movie-insights-per-countries/:id} : get the "id" movieInsightsPerCountry.
     *
     * @param id the id of the movieInsightsPerCountryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerCountryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/movie-insights-per-countries/{id}")
    public ResponseEntity<MovieInsightsPerCountryDTO> getMovieInsightsPerCountry(@PathVariable Long id) {
        log.debug("REST request to get MovieInsightsPerCountry : {}", id);
        Optional<MovieInsightsPerCountryDTO> movieInsightsPerCountryDTO = movieInsightsPerCountryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movieInsightsPerCountryDTO);
    }

    /**
     * {@code DELETE  /movie-insights-per-countries/:id} : delete the "id" movieInsightsPerCountry.
     *
     * @param id the id of the movieInsightsPerCountryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/movie-insights-per-countries/{id}")
    public ResponseEntity<Void> deleteMovieInsightsPerCountry(@PathVariable Long id) {
        log.debug("REST request to delete MovieInsightsPerCountry : {}", id);
        movieInsightsPerCountryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/movie-insights-per-countries?query=:query} : search for the movieInsightsPerCountry corresponding
     * to the query.
     *
     * @param query the query of the movieInsightsPerCountry search.
     * @return the result of the search.
     */
    @GetMapping("/_search/movie-insights-per-countries")
    public List<MovieInsightsPerCountryDTO> searchMovieInsightsPerCountries(@RequestParam String query) {
        log.debug("REST request to search MovieInsightsPerCountries for query {}", query);
        return movieInsightsPerCountryService.search(query);
    }
}
