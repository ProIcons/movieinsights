package gr.movieinsights.web.rest;

import gr.movieinsights.service.MovieInsightsPerGenreService;
import gr.movieinsights.service.dto.MovieInsightsPerGenreDTO;
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
 * REST controller for managing {@link gr.movieinsights.domain.MovieInsightsPerGenre}.
 */
@RestController
@RequestMapping("/api")
public class MovieInsightsPerGenreResource {

    private final Logger log = LoggerFactory.getLogger(MovieInsightsPerGenreResource.class);

    private static final String ENTITY_NAME = "movieInsightsPerGenre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovieInsightsPerGenreService movieInsightsPerGenreService;

    public MovieInsightsPerGenreResource(MovieInsightsPerGenreService movieInsightsPerGenreService) {
        this.movieInsightsPerGenreService = movieInsightsPerGenreService;
    }

    /**
     * {@code POST  /movie-insights-per-genres} : Create a new movieInsightsPerGenre.
     *
     * @param movieInsightsPerGenreDTO the movieInsightsPerGenreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movieInsightsPerGenreDTO, or with status {@code 400 (Bad Request)} if the movieInsightsPerGenre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/movie-insights-per-genres")
    public ResponseEntity<MovieInsightsPerGenreDTO> createMovieInsightsPerGenre(@Valid @RequestBody MovieInsightsPerGenreDTO movieInsightsPerGenreDTO) throws URISyntaxException {
        log.debug("REST request to save MovieInsightsPerGenre : {}", movieInsightsPerGenreDTO);
        if (movieInsightsPerGenreDTO.getId() != null) {
            throw new BadRequestAlertException("A new movieInsightsPerGenre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovieInsightsPerGenreDTO result = movieInsightsPerGenreService.save(movieInsightsPerGenreDTO);
        return ResponseEntity.created(new URI("/api/movie-insights-per-genres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /movie-insights-per-genres} : Updates an existing movieInsightsPerGenre.
     *
     * @param movieInsightsPerGenreDTO the movieInsightsPerGenreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movieInsightsPerGenreDTO,
     * or with status {@code 400 (Bad Request)} if the movieInsightsPerGenreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movieInsightsPerGenreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/movie-insights-per-genres")
    public ResponseEntity<MovieInsightsPerGenreDTO> updateMovieInsightsPerGenre(@Valid @RequestBody MovieInsightsPerGenreDTO movieInsightsPerGenreDTO) throws URISyntaxException {
        log.debug("REST request to update MovieInsightsPerGenre : {}", movieInsightsPerGenreDTO);
        if (movieInsightsPerGenreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovieInsightsPerGenreDTO result = movieInsightsPerGenreService.save(movieInsightsPerGenreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, movieInsightsPerGenreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /movie-insights-per-genres} : get all the movieInsightsPerGenres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movieInsightsPerGenres in body.
     */
    @GetMapping("/movie-insights-per-genres")
    public List<MovieInsightsPerGenreDTO> getAllMovieInsightsPerGenres() {
        log.debug("REST request to get all MovieInsightsPerGenres");
        return movieInsightsPerGenreService.findAll();
    }

    /**
     * {@code GET  /movie-insights-per-genres/:id} : get the "id" movieInsightsPerGenre.
     *
     * @param id the id of the movieInsightsPerGenreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerGenreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/movie-insights-per-genres/{id}")
    public ResponseEntity<MovieInsightsPerGenreDTO> getMovieInsightsPerGenre(@PathVariable Long id) {
        log.debug("REST request to get MovieInsightsPerGenre : {}", id);
        Optional<MovieInsightsPerGenreDTO> movieInsightsPerGenreDTO = movieInsightsPerGenreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movieInsightsPerGenreDTO);
    }

    /**
     * {@code DELETE  /movie-insights-per-genres/:id} : delete the "id" movieInsightsPerGenre.
     *
     * @param id the id of the movieInsightsPerGenreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/movie-insights-per-genres/{id}")
    public ResponseEntity<Void> deleteMovieInsightsPerGenre(@PathVariable Long id) {
        log.debug("REST request to delete MovieInsightsPerGenre : {}", id);
        movieInsightsPerGenreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/movie-insights-per-genres?query=:query} : search for the movieInsightsPerGenre corresponding
     * to the query.
     *
     * @param query the query of the movieInsightsPerGenre search.
     * @return the result of the search.
     */
    @GetMapping("/_search/movie-insights-per-genres")
    public List<MovieInsightsPerGenreDTO> searchMovieInsightsPerGenres(@RequestParam String query) {
        log.debug("REST request to search MovieInsightsPerGenres for query {}", query);
        return movieInsightsPerGenreService.search(query);
    }
}
