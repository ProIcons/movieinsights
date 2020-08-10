package gr.movieinsights.web.rest;

import gr.movieinsights.service.MovieInsightsPerPersonService;
import gr.movieinsights.service.dto.MovieInsightsPerPersonDTO;
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
 * REST controller for managing {@link gr.movieinsights.domain.MovieInsightsPerPerson}.
 */
@RestController
@RequestMapping("/api")
public class MovieInsightsPerPersonResource {

    private final Logger log = LoggerFactory.getLogger(MovieInsightsPerPersonResource.class);

    private static final String ENTITY_NAME = "movieInsightsPerPerson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovieInsightsPerPersonService movieInsightsPerPersonService;

    public MovieInsightsPerPersonResource(MovieInsightsPerPersonService movieInsightsPerPersonService) {
        this.movieInsightsPerPersonService = movieInsightsPerPersonService;
    }

    /**
     * {@code POST  /movie-insights-per-people} : Create a new movieInsightsPerPerson.
     *
     * @param movieInsightsPerPersonDTO the movieInsightsPerPersonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movieInsightsPerPersonDTO, or with status {@code 400 (Bad Request)} if the movieInsightsPerPerson has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/movie-insights-per-people")
    public ResponseEntity<MovieInsightsPerPersonDTO> createMovieInsightsPerPerson(@Valid @RequestBody MovieInsightsPerPersonDTO movieInsightsPerPersonDTO) throws URISyntaxException {
        log.debug("REST request to save MovieInsightsPerPerson : {}", movieInsightsPerPersonDTO);
        if (movieInsightsPerPersonDTO.getId() != null) {
            throw new BadRequestAlertException("A new movieInsightsPerPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovieInsightsPerPersonDTO result = movieInsightsPerPersonService.save(movieInsightsPerPersonDTO);
        return ResponseEntity.created(new URI("/api/movie-insights-per-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /movie-insights-per-people} : Updates an existing movieInsightsPerPerson.
     *
     * @param movieInsightsPerPersonDTO the movieInsightsPerPersonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movieInsightsPerPersonDTO,
     * or with status {@code 400 (Bad Request)} if the movieInsightsPerPersonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movieInsightsPerPersonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/movie-insights-per-people")
    public ResponseEntity<MovieInsightsPerPersonDTO> updateMovieInsightsPerPerson(@Valid @RequestBody MovieInsightsPerPersonDTO movieInsightsPerPersonDTO) throws URISyntaxException {
        log.debug("REST request to update MovieInsightsPerPerson : {}", movieInsightsPerPersonDTO);
        if (movieInsightsPerPersonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovieInsightsPerPersonDTO result = movieInsightsPerPersonService.save(movieInsightsPerPersonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, movieInsightsPerPersonDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /movie-insights-per-people} : get all the movieInsightsPerPeople.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movieInsightsPerPeople in body.
     */
    @GetMapping("/movie-insights-per-people")
    public List<MovieInsightsPerPersonDTO> getAllMovieInsightsPerPeople() {
        log.debug("REST request to get all MovieInsightsPerPeople");
        return movieInsightsPerPersonService.findAll();
    }

    /**
     * {@code GET  /movie-insights-per-people/:id} : get the "id" movieInsightsPerPerson.
     *
     * @param id the id of the movieInsightsPerPersonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerPersonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/movie-insights-per-people/{id}")
    public ResponseEntity<MovieInsightsPerPersonDTO> getMovieInsightsPerPerson(@PathVariable Long id) {
        log.debug("REST request to get MovieInsightsPerPerson : {}", id);
        Optional<MovieInsightsPerPersonDTO> movieInsightsPerPersonDTO = movieInsightsPerPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movieInsightsPerPersonDTO);
    }

    /**
     * {@code DELETE  /movie-insights-per-people/:id} : delete the "id" movieInsightsPerPerson.
     *
     * @param id the id of the movieInsightsPerPersonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/movie-insights-per-people/{id}")
    public ResponseEntity<Void> deleteMovieInsightsPerPerson(@PathVariable Long id) {
        log.debug("REST request to delete MovieInsightsPerPerson : {}", id);
        movieInsightsPerPersonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/movie-insights-per-people?query=:query} : search for the movieInsightsPerPerson corresponding
     * to the query.
     *
     * @param query the query of the movieInsightsPerPerson search.
     * @return the result of the search.
     */
    @GetMapping("/_search/movie-insights-per-people")
    public List<MovieInsightsPerPersonDTO> searchMovieInsightsPerPeople(@RequestParam String query) {
        log.debug("REST request to search MovieInsightsPerPeople for query {}", query);
        return movieInsightsPerPersonService.search(query);
    }
}
