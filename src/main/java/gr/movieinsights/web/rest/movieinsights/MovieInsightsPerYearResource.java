package gr.movieinsights.web.rest.movieinsights;

import gr.movieinsights.domain.MovieInsightsPerYear;
import gr.movieinsights.service.MovieInsightsPerYearService;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * REST controller for managing {@link gr.movieinsights.domain.MovieInsightsPerYear}.
 */
@RestController
@RequestMapping("/year")
public class MovieInsightsPerYearResource extends BaseMovieInsightsResource<MovieInsightsPerYear, MovieInsightsPerYearDTO, MovieInsightsPerYearService> {

    private static final String ENTITY_NAME = "movieInsightsPerYear";

    public MovieInsightsPerYearResource(MovieInsightsPerYearService movieInsightsPerYearService) {
        super(movieInsightsPerYearService, ENTITY_NAME);
    }

    /**
     * {@code GET /:year} : get the movieInsightsPerYear by year.
     *
     * @param year
     *     the year of the movieInsightsPerYearDTO to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerYearDTO, or
     * with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{year}")
    public ResponseEntity<MovieInsightsPerYearDTO> getByYear(@PathVariable("year") int year) {
        log.debug("REST request to get MovieInsightsPerYear By Year : {}", year);
        Optional<MovieInsightsPerYearDTO> movieInsightsPerYear = getService().findByYear(year);
        return ResponseUtil.wrapOrNotFound(movieInsightsPerYear);
    }

}
