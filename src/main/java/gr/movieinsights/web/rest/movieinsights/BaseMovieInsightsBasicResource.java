package gr.movieinsights.web.rest.movieinsights;

import gr.movieinsights.config.converters.ExtendedParam;
import gr.movieinsights.security.AuthoritiesConstants;
import gr.movieinsights.service.dto.BaseDTO;
import gr.movieinsights.service.util.IBasicDataProviderService;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public abstract class BaseMovieInsightsBasicResource<TEntity, TDTO extends TBasicDTO, TBasicDTO extends BaseDTO, TService extends IBasicDataProviderService<TEntity, TDTO, TBasicDTO, ?, ?, ?>> extends BaseMovieInsightsResource<TEntity, TDTO, TService> {
    public BaseMovieInsightsBasicResource(TService service, String entityName) {
        super(service, entityName);
    }

    /**
     * {@code GET /extended} : get all the entities expanded.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @Override
    @Secured(AuthoritiesConstants.ADMIN)
    @GetMapping(value = "/_/{id}")
    public ResponseEntity<? extends TDTO> getById(@PathVariable("id") Long id, @ExtendedParam boolean extended) {
        log.debug("REST request to get all {}", entityName);

        if (extended) {
            Optional<TDTO> movieInsightsGeneral = getService().findOne(id);
            return ResponseUtil.wrapOrNotFound(movieInsightsGeneral);
        } else
            return (ResponseEntity<? extends TDTO>) ResponseUtil.wrapOrNotFound(getService().findOneBasic(id));
    }

}
