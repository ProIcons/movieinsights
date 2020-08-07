package gr.movieinsights.web.rest;

import gr.movieinsights.service.MovieInsightsService;
import gr.movieinsights.web.rest.errors.BadRequestAlertException;
import gr.movieinsights.service.dto.MovieInsightsDTO;

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
 * REST controller for managing {@link gr.movieinsights.domain.MovieInsights}.
 */
@RestController
@RequestMapping("/api")
public class MovieInsightsResource {

    private final Logger log = LoggerFactory.getLogger(MovieInsightsResource.class);

    private static final String ENTITY_NAME = "movieInsights";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovieInsightsService movieInsightsService;

    public MovieInsightsResource(MovieInsightsService movieInsightsService) {
        this.movieInsightsService = movieInsightsService;
    }

    /**
     * {@code POST  /movie-insights} : Create a new movieInsights.
     *
     * @param movieInsightsDTO the movieInsightsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movieInsightsDTO, or with status {@code 400 (Bad Request)} if the movieInsights has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/movie-insights")
    public ResponseEntity<MovieInsightsDTO> createMovieInsights(@Valid @RequestBody MovieInsightsDTO movieInsightsDTO) throws URISyntaxException {
        log.debug("REST request to save MovieInsights : {}", movieInsightsDTO);
        if (movieInsightsDTO.getId() != null) {
            throw new BadRequestAlertException("A new movieInsights cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovieInsightsDTO result = movieInsightsService.save(movieInsightsDTO);
        return ResponseEntity.created(new URI("/api/movie-insights/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /movie-insights} : Updates an existing movieInsights.
     *
     * @param movieInsightsDTO the movieInsightsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movieInsightsDTO,
     * or with status {@code 400 (Bad Request)} if the movieInsightsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movieInsightsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/movie-insights")
    public ResponseEntity<MovieInsightsDTO> updateMovieInsights(@Valid @RequestBody MovieInsightsDTO movieInsightsDTO) throws URISyntaxException {
        log.debug("REST request to update MovieInsights : {}", movieInsightsDTO);
        if (movieInsightsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovieInsightsDTO result = movieInsightsService.save(movieInsightsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, movieInsightsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /movie-insights} : get all the movieInsights.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movieInsights in body.
     */
    @GetMapping("/movie-insights")
    public List<MovieInsightsDTO> getAllMovieInsights() {
        log.debug("REST request to get all MovieInsights");
        return movieInsightsService.findAll();
    }

    /**
     * {@code GET  /movie-insights/:id} : get the "id" movieInsights.
     *
     * @param id the id of the movieInsightsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/movie-insights/{id}")
    public ResponseEntity<MovieInsightsDTO> getMovieInsights(@PathVariable Long id) {
        log.debug("REST request to get MovieInsights : {}", id);
        Optional<MovieInsightsDTO> movieInsightsDTO = movieInsightsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movieInsightsDTO);
    }

    /**
     * {@code DELETE  /movie-insights/:id} : delete the "id" movieInsights.
     *
     * @param id the id of the movieInsightsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/movie-insights/{id}")
    public ResponseEntity<Void> deleteMovieInsights(@PathVariable Long id) {
        log.debug("REST request to delete MovieInsights : {}", id);
        movieInsightsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/movie-insights?query=:query} : search for the movieInsights corresponding
     * to the query.
     *
     * @param query the query of the movieInsights search.
     * @return the result of the search.
     */
    @GetMapping("/_search/movie-insights")
    public List<MovieInsightsDTO> searchMovieInsights(@RequestParam String query) {
        log.debug("REST request to search MovieInsights for query {}", query);
        return movieInsightsService.search(query);
    }
}
