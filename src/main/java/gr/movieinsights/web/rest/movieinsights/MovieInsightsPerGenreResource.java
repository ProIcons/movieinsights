package gr.movieinsights.web.rest.movieinsights;

import gr.movieinsights.config.converters.ExtendedParam;
import gr.movieinsights.domain.MovieInsightsPerGenre;
import gr.movieinsights.service.MovieInsightsPerGenreService;
import gr.movieinsights.service.dto.movieinsights.genre.MovieInsightsPerGenreBasicDTO;
import gr.movieinsights.service.dto.movieinsights.genre.MovieInsightsPerGenreDTO;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * REST controller for managing {@link gr.movieinsights.domain.MovieInsightsPerGenre}.
 */
@RestController
@RequestMapping({"/genre","/gn"})
public class MovieInsightsPerGenreResource extends BaseMovieInsightsContainerResource<MovieInsightsPerGenre, MovieInsightsPerGenreDTO, MovieInsightsPerGenreBasicDTO, MovieInsightsPerGenreService> {
    private static final String ENTITY_NAME = "movieInsightsPerGenre";

    public MovieInsightsPerGenreResource(MovieInsightsPerGenreService movieInsightsPerGenreService) {
        super(movieInsightsPerGenreService,ENTITY_NAME);
    }

    /**
     * {@code GET /:genreName?extended} : get the movieInsightsPerGenre by genre.
     *
     * @param name
     *     the name of the movieInsightsPerGenreDTO to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerYearDTO, or
     * with status {@code 404 (Not Found)}.
     */
    @GetMapping(path = {"/n/{genreName}"})
    public ResponseEntity<? extends MovieInsightsPerGenreBasicDTO> getByGenreName(
        @PathVariable("genreName") String name,
        @ExtendedParam boolean extended) {
        log.debug("REST request to get{} movieInsightsPerGenre By Genre {}", extended ? " extended" : "", name);
        if (!extended)
            return ResponseUtil.wrapOrNotFound(getService().findByGenreNameBasic(name));
        else
            return ResponseUtil.wrapOrNotFound(getService().findByGenreName(name));
    }

    /**
     * {@code GET /:iso/:year} : get the movieInsightsPerEntity by country and by year.
     *
     * @param genreName
     *     the name of the movieInsightsPerGenreDTO to retrieve.
     * @param year
     *     the year
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerYearDTO, or
     * with status {@code 404 (Not Found)}.
     */
    @GetMapping(path = {"/n/{genreName}/{year}"})
    public ResponseEntity<MovieInsightsPerYearDTO> getByYear(
        @PathVariable("genreName") String genreName,
        @PathVariable("year") int year) {

        return ResponseUtil.wrapOrNotFound(getService().findByYear(genreName, year));

    }

    /**
     * {@code GET /:genreId?extended} : get the movieInsightsPerGenre by genre.
     *
     * @param id
     *     the id of the movieInsightsPerGenreDTO to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerYearDTO, or
     * with status {@code 404 (Not Found)}.
     */
    @GetMapping(path = {"/{genreId}","/i/{genreId}"})
    public ResponseEntity<? extends MovieInsightsPerGenreBasicDTO> getByGenreId(
        @PathVariable("genreId") Long id,
        @RequestParam(value = "extended", required = false, defaultValue = "false") boolean extended,
        HttpServletRequest request) {
        log.debug("REST request to get{} movieInsightsPerGenre By Genre {}", extended ? " extended" : "", id);

        if (!extended)
            return ResponseUtil.wrapOrNotFound(getService().findByGenreIdBasic(id));
        else
            return ResponseUtil.wrapOrNotFound(getService().findByGenreId(id));
    }


    /**
     * {@code GET /:genreId/:year} : get the movieInsightsPerGenre by genre and by year.
     *
     * @param id
     *     the id of the genre to retrieve.
     * @param year
     *     the year
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerYearDTO, or
     * with status {@code 404 (Not Found)}.
     */
    @GetMapping(path = {"/i/{genreId}/{year}","/{genreId}/{year}"})
    public ResponseEntity<MovieInsightsPerYearDTO> getByYear(
        @PathVariable("genreId") Long id,
        @PathVariable("year") int year) {

        return ResponseUtil.wrapOrNotFound(getService().findByYear(id, year));

    }

}
