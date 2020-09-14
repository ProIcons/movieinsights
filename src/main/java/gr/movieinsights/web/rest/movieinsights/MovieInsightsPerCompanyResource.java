package gr.movieinsights.web.rest.movieinsights;

import gr.movieinsights.config.converters.ExtendedParam;
import gr.movieinsights.domain.MovieInsightsPerCompany;
import gr.movieinsights.service.MovieInsightsPerCompanyService;
import gr.movieinsights.service.dto.movieinsights.company.MovieInsightsPerCompanyBasicDTO;
import gr.movieinsights.service.dto.movieinsights.company.MovieInsightsPerCompanyDTO;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link gr.movieinsights.domain.MovieInsightsPerCompany}.
 */
@RestController
@RequestMapping({"/company", "/cm"})
public class MovieInsightsPerCompanyResource extends BaseMovieInsightsContainerResource<MovieInsightsPerCompany, MovieInsightsPerCompanyDTO, MovieInsightsPerCompanyBasicDTO, MovieInsightsPerCompanyService> {
    private static final String ENTITY_NAME = "movieInsightsPerCompany";

    public MovieInsightsPerCompanyResource(MovieInsightsPerCompanyService movieInsightsPerCompanyService) {
        super(movieInsightsPerCompanyService, ENTITY_NAME);
    }

    /**
     * {@code GET /:companyId?extended} : get the movieInsightsPerCompany by company.
     *
     * @param id
     *     the id of the movieInsightsPerCompanyDTO to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerYearDTO, or
     * with status {@code 404 (Not Found)}.
     */
    @GetMapping(path = {"/{companyId}"})
    public ResponseEntity<? extends MovieInsightsPerCompanyBasicDTO> getByCompanyIdExtended(
        @PathVariable("companyId") Long id,
        @ExtendedParam boolean extended) {

        log.debug("REST request to get{} movieInsightsPerCompany By Company {}", extended ? " extended" : "", id);

        if (!extended)
            return ResponseUtil.wrapOrNotFound(getService().findByCompanyIdBasic(id));
        else
            return ResponseUtil.wrapOrNotFound(getService().findByCompanyId(id));
    }


    /**
     * {@code GET /:companyId/:year} : get the movieInsightsPerCompany by company and by year.
     *
     * @param id
     *     the id of the company to retrieve.
     * @param year
     *     the year
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerYearDTO, or
     * with status {@code 404 (Not Found)}.
     */
    @GetMapping(path = {"/{companyId}/{year}"})
    public ResponseEntity<MovieInsightsPerYearDTO> getByYear(
        @PathVariable("companyId") Long id,
        @PathVariable("year") int year) {

        return ResponseUtil.wrapOrNotFound(getService().findByYear(id, year));

    }
}
