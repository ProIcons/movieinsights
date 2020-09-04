package gr.movieinsights.web.rest.movieinsights;

import gr.movieinsights.domain.IdentifiedEntity;
import gr.movieinsights.service.dto.BaseDTO;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import gr.movieinsights.service.util.BaseMovieInsightsService;
import gr.movieinsights.service.util.IBasicDataProviderService;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public abstract class BaseMovieInsightsContainerResource<TEntity extends IdentifiedEntity, TDTO extends TBasicDTO, TBasicDTO extends BaseDTO, TService extends BaseMovieInsightsService<TEntity,TDTO,TBasicDTO,?,?,?>> extends BaseMovieInsightsBasicResource<TEntity,TDTO,TBasicDTO,TService> {
    public BaseMovieInsightsContainerResource(TService service, String entityName) {
        super(service, entityName);
    }

    /**
     * {@code GET /:entityId/:year} : get the movieInsightsPerEntity by entity and by year.
     *
     * @param id
     *     the id of the entity to retrieve.
     * @param year
     *     the year
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieInsightsPerYearDTO, or
     * with status {@code 404 (Not Found)}.
     */
    @GetMapping(path = {"/{entityId}/{year}"})
    public ResponseEntity<MovieInsightsPerYearDTO> getByYear(
        @PathVariable("entityId") Long id,
        @PathVariable("year") int year) {

        return ResponseUtil.wrapOrNotFound(getService().findByYear(id, year));

    }
}
