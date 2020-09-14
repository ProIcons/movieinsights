package gr.movieinsights.web.rest.movieinsights;

import gr.movieinsights.config.converters.ExtendedParam;
import gr.movieinsights.domain.MovieInsightsPerPerson;
import gr.movieinsights.domain.MovieInsightsPerYear;
import gr.movieinsights.domain.Person;
import gr.movieinsights.domain.enumeration.CreditRole;
import gr.movieinsights.service.MovieInsightsPerPersonService;
import gr.movieinsights.service.dto.BaseDTO;
import gr.movieinsights.service.dto.movieinsights.person.MovieInsightsPerPersonBasicDTO;
import gr.movieinsights.service.dto.movieinsights.person.MovieInsightsPerPersonDTO;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import gr.movieinsights.service.dto.person.BasicPersonDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.person.BasicPersonMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link gr.movieinsights.domain.MovieInsightsPerPerson}.
 */
@RestController
@RequestMapping({"/person","/p"})
public class MovieInsightsPerPersonResource extends BaseMovieInsightsContainerResource<MovieInsightsPerPerson, MovieInsightsPerPersonDTO, MovieInsightsPerPersonBasicDTO, MovieInsightsPerPersonService> {
    private static final String ENTITY_NAME = "movieInsightsPerPerson";

    public MovieInsightsPerPersonResource(MovieInsightsPerPersonService movieInsightsPerPersonService) {
        super(movieInsightsPerPersonService, ENTITY_NAME);
    }

    /**
     * {@code GET /:personId?extended} : get the movieInsights by person.
     *
     * @param id
     *     the id of the movieInsightsPerPersonDTOs to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerYearDTO, or
     * with status {@code 404 (Not Found)}.
     */
    @GetMapping(path = {"/{personId}"})
    public ResponseEntity<? extends MovieInsightsPerPersonService.MultiPersonView<?,? extends BasicPersonDTO>> getByPersonId(
        @PathVariable("personId") Long id,
        @ExtendedParam boolean extended) {
        log.debug("REST request to get{} movieInsightsPerPerson By Person {}", extended ? " extended" : "", id);
        Optional<? extends MovieInsightsPerPersonService.MultiPersonView<?,? extends BasicPersonDTO>> people;
        if (!extended)
            people = getService().findByPersonIdBasic(id);
        else
            people = getService().findByPersonId(id);

        return ResponseUtil.wrapOrNotFound(people);
    }


    /**
     * {@code GET /:personId/:year} : get the movieInsightsPerPerson by person and by year.
     *
     * @param id
     *     the id of the person to retrieve.
     * @param year
     *     the year
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerYearDTO, or
     * with status {@code 404 (Not Found)}.
     */
    @GetMapping(path = {"/{personId}/{year}"})
    public ResponseEntity<MovieInsightsPerPersonService.MultiPersonView<BasicPersonMapper, BasicPersonDTO>> getByYear(
        @PathVariable("personId") Long id,
        @PathVariable("year") int year) {

        return ResponseUtil.wrapOrNotFound(getService().findByYearMulti(id, year));

    }

    /**
     * {@code GET /:personId/:role?extended} : get the movieInsights by person.
     *
     * @param id
     *     the id of the movieInsightsPerPersonDTO to retrieve.
     * @param role
     *     the role of the person
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerYearDTO, or
     * with status {@code 404 (Not Found)}.
     */
    @GetMapping(path = {"/r/{personId}/{role}"})
    public ResponseEntity<? extends MovieInsightsPerPersonBasicDTO> getByPersonId(
        @PathVariable("personId") Long id,
        @PathVariable("role") CreditRole role,
        @ExtendedParam boolean extended) {
        log.debug("REST request to get{} movieInsightsPerPerson By Person {} as {}", extended ? " extended" : "", id, role);

        if (!extended)
            return ResponseUtil.wrapOrNotFound(getService().findByPersonIdBasic(id, role));
        else
            return ResponseUtil.wrapOrNotFound(getService().findByPersonId(id, role));
    }

    /**
     * {@code GET /:personId/:role?extended} : get the movieInsights by person.
     *
     * @param id
     *     the id of the movieInsightsPerPersonDTO to retrieve.
     * @param role
     *     the role of the person
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerYearDTO, or
     * with status {@code 404 (Not Found)}.
     */
    @GetMapping(path = {"/r/{personId}/{role}/{year}"})
    public ResponseEntity<MovieInsightsPerYearDTO> getByPersonId(
        @PathVariable("personId") Long id,
        @PathVariable("role") CreditRole role,
        @PathVariable("year") int year,
        @ExtendedParam boolean extended) {
        log.debug("REST request to get{} movieInsightsPerPerson By Person {} as {}", extended ? " extended" : "", id, role);
        return ResponseUtil.wrapOrNotFound(getService().findByYear(id, role,year));
    }
}
