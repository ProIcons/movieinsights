package gr.movieinsights.web.rest;

import gr.movieinsights.service.MovieInsightsPerYearService;
import gr.movieinsights.web.rest.errors.BadRequestAlertException;
import gr.movieinsights.service.dto.MovieInsightsPerYearDTO;

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
 * REST controller for managing {@link gr.movieinsights.domain.MovieInsightsPerYear}.
 */
@RestController
@RequestMapping("/api")
public class MovieInsightsPerYearResource {

    private final Logger log = LoggerFactory.getLogger(MovieInsightsPerYearResource.class);

    private static final String ENTITY_NAME = "movieInsightsPerYear";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovieInsightsPerYearService movieInsightsPerYearService;

    public MovieInsightsPerYearResource(MovieInsightsPerYearService movieInsightsPerYearService) {
        this.movieInsightsPerYearService = movieInsightsPerYearService;
    }

    /**
     * {@code POST  /movie-insights-per-years} : Create a new movieInsightsPerYear.
     *
     * @param movieInsightsPerYearDTO the movieInsightsPerYearDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movieInsightsPerYearDTO, or with status {@code 400 (Bad Request)} if the movieInsightsPerYear has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/movie-insights-per-years")
    public ResponseEntity<MovieInsightsPerYearDTO> createMovieInsightsPerYear(@Valid @RequestBody MovieInsightsPerYearDTO movieInsightsPerYearDTO) throws URISyntaxException {
        log.debug("REST request to save MovieInsightsPerYear : {}", movieInsightsPerYearDTO);
        if (movieInsightsPerYearDTO.getId() != null) {
            throw new BadRequestAlertException("A new movieInsightsPerYear cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovieInsightsPerYearDTO result = movieInsightsPerYearService.save(movieInsightsPerYearDTO);
        return ResponseEntity.created(new URI("/api/movie-insights-per-years/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /movie-insights-per-years} : Updates an existing movieInsightsPerYear.
     *
     * @param movieInsightsPerYearDTO the movieInsightsPerYearDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movieInsightsPerYearDTO,
     * or with status {@code 400 (Bad Request)} if the movieInsightsPerYearDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movieInsightsPerYearDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/movie-insights-per-years")
    public ResponseEntity<MovieInsightsPerYearDTO> updateMovieInsightsPerYear(@Valid @RequestBody MovieInsightsPerYearDTO movieInsightsPerYearDTO) throws URISyntaxException {
        log.debug("REST request to update MovieInsightsPerYear : {}", movieInsightsPerYearDTO);
        if (movieInsightsPerYearDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovieInsightsPerYearDTO result = movieInsightsPerYearService.save(movieInsightsPerYearDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, movieInsightsPerYearDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /movie-insights-per-years} : get all the movieInsightsPerYears.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movieInsightsPerYears in body.
     */
    @GetMapping("/movie-insights-per-years")
    public List<MovieInsightsPerYearDTO> getAllMovieInsightsPerYears() {
        log.debug("REST request to get all MovieInsightsPerYears");
        return movieInsightsPerYearService.findAll();
    }

    /**
     * {@code GET  /movie-insights-per-years/:id} : get the "id" movieInsightsPerYear.
     *
     * @param id the id of the movieInsightsPerYearDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerYearDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/movie-insights-per-years/{id}")
    public ResponseEntity<MovieInsightsPerYearDTO> getMovieInsightsPerYear(@PathVariable Long id) {
        log.debug("REST request to get MovieInsightsPerYear : {}", id);
        Optional<MovieInsightsPerYearDTO> movieInsightsPerYearDTO = movieInsightsPerYearService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movieInsightsPerYearDTO);
    }

    /**
     * {@code DELETE  /movie-insights-per-years/:id} : delete the "id" movieInsightsPerYear.
     *
     * @param id the id of the movieInsightsPerYearDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/movie-insights-per-years/{id}")
    public ResponseEntity<Void> deleteMovieInsightsPerYear(@PathVariable Long id) {
        log.debug("REST request to delete MovieInsightsPerYear : {}", id);
        movieInsightsPerYearService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/movie-insights-per-years?query=:query} : search for the movieInsightsPerYear corresponding
     * to the query.
     *
     * @param query the query of the movieInsightsPerYear search.
     * @return the result of the search.
     */
    @GetMapping("/_search/movie-insights-per-years")
    public List<MovieInsightsPerYearDTO> searchMovieInsightsPerYears(@RequestParam String query) {
        log.debug("REST request to search MovieInsightsPerYears for query {}", query);
        return movieInsightsPerYearService.search(query);
    }
}
