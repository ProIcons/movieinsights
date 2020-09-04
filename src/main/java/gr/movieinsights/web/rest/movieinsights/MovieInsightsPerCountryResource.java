package gr.movieinsights.web.rest.movieinsights;

import gr.movieinsights.config.converters.ExtendedParam;
import gr.movieinsights.domain.MovieInsightsPerCountry;
import gr.movieinsights.service.MovieInsightsPerCountryService;
import gr.movieinsights.service.dto.movieinsights.country.MovieInsightsPerCountryBasicDTO;
import gr.movieinsights.service.dto.movieinsights.country.MovieInsightsPerCountryDTO;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link gr.movieinsights.domain.MovieInsightsPerCountry}.
 */
@RestController
@RequestMapping({"/country","/cn"})
public class MovieInsightsPerCountryResource extends BaseMovieInsightsContainerResource<MovieInsightsPerCountry, MovieInsightsPerCountryDTO, MovieInsightsPerCountryBasicDTO, MovieInsightsPerCountryService> {
    private static final String ENTITY_NAME = "movieInsightsPerCountry";

    public MovieInsightsPerCountryResource(MovieInsightsPerCountryService movieInsightsPerCountryService) {
        super(movieInsightsPerCountryService, ENTITY_NAME);
    }

    /**
     * {@code GET /:iso?extended} : get the movieInsightsPerCountry by country.
     *
     * @param iso
     *     the iso of the movieInsightsPerCountryDTO to retrieve.
     * @param extended
     *     whether to include extended information of not
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerYearDTO, or
     * with status {@code 404 (Not Found)}.
     */
    @GetMapping(path = {"/{iso}"})
    public ResponseEntity<? extends MovieInsightsPerCountryBasicDTO> getByCountryIso(
        @PathVariable("iso") String iso,
        @ExtendedParam boolean extended) {
        log.debug("REST request to get{} movieInsightsPerCountry By Country {}", extended ? " extended" : "", iso);
        if (extended)
            return ResponseUtil.wrapOrNotFound(getService().findByCountryIso(iso));
        else
            return ResponseUtil.wrapOrNotFound(getService().findByCountryIsoBasic(iso));
    }

    /**
     * {@code GET /:iso/:year} : get the movieInsightsPerEntity by country and by year.
     *
     * @param iso
     *     the iso of the movieInsightsPerCountryDTO to retrieve.
     * @param year
     *     the year
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerYearDTO, or
     * with status {@code 404 (Not Found)}.
     */
    @GetMapping(path = {"/{iso}/{year}"})
    public ResponseEntity<MovieInsightsPerYearDTO> getByYear(
        @PathVariable("iso") String iso,
        @PathVariable("year") int year) {

        return ResponseUtil.wrapOrNotFound(getService().findByYear(iso, year));

    }
}
