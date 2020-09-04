package gr.movieinsights.web.rest.movieinsights;

import gr.movieinsights.config.converters.ExtendedParam;
import gr.movieinsights.domain.MovieInsightsGeneral;
import gr.movieinsights.service.MovieInsightsGeneralService;
import gr.movieinsights.service.dto.movieinsights.general.MovieInsightsGeneralBasicDTO;
import gr.movieinsights.service.dto.movieinsights.general.MovieInsightsGeneralDTO;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link gr.movieinsights.domain.MovieInsightsGeneral}.
 */
@RestController
@RequestMapping({"/general", "/ge"})
@Transactional
public class MovieInsightsGeneralResource extends BaseMovieInsightsResource<MovieInsightsGeneral, MovieInsightsGeneralDTO, MovieInsightsGeneralService> {

    private static final String ENTITY_NAME = "movieInsightsGeneral";

    public MovieInsightsGeneralResource(MovieInsightsGeneralService movieInsightsGeneralService) {
        super(movieInsightsGeneralService, ENTITY_NAME);
    }

    /**
     * {@code GET  /} : get all the movieInsightsGenerals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movieInsightsGenerals in body.
     */
    @GetMapping
    public ResponseEntity<? extends MovieInsightsGeneralBasicDTO> get(@ExtendedParam boolean extended) {
        log.debug("REST request to get all MovieInsightsGenerals");
        if (!extended)
            return ResponseUtil.wrapOrNotFound(getService().getBasic());
        else
            return ResponseUtil.wrapOrNotFound(getService().get());

    }

    /**
     * {@code GET /:entityId/:year} : get the movieInsightsGenerals by year.
     *
     * @param year
     *     the year
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerYearDTO, or
     * with status {@code 404 (Not Found)}.
     */
    @GetMapping(path = {"/{year}"})
    public ResponseEntity<MovieInsightsPerYearDTO> getByYear(
        @PathVariable("year") int year) {

        return ResponseUtil.wrapOrNotFound(getService().findByYear(year));

    }
}


