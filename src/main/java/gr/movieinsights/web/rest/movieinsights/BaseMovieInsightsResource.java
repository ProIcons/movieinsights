package gr.movieinsights.web.rest.movieinsights;

import gr.movieinsights.config.converters.ExtendedParam;
import gr.movieinsights.security.AuthoritiesConstants;
import gr.movieinsights.service.dto.BaseDTO;
import gr.movieinsights.service.util.IBaseService;
import gr.movieinsights.web.rest.BaseResource;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequestMapping({"/movieinsights","/m"})
public abstract class BaseMovieInsightsResource<TEntity, TDTO extends BaseDTO, TService extends IBaseService<TEntity, TDTO, ?, ?>> extends BaseResource {

    private final TService service;
    protected final String entityName;

    public BaseMovieInsightsResource(TService service, String entityName) {
        this.service = service;
        this.entityName = entityName;
    }

    public TService getService() {
        return service;
    }

    /**
     * {@code GET /} : get all the entities expanded.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @Secured(AuthoritiesConstants.ADMIN)
    @GetMapping("/_/{id}")
    public ResponseEntity<? extends TDTO> getById(@PathVariable("id") Long id, @ExtendedParam boolean extended) {
        log.debug("REST request to get all {}", entityName);
        Optional<TDTO> movieInsightsGeneral = getService().findOne(id);
        return ResponseUtil.wrapOrNotFound(movieInsightsGeneral);
    }
}
