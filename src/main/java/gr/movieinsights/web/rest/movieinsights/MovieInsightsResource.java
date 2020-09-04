package gr.movieinsights.web.rest.movieinsights;

import gr.movieinsights.domain.MovieInsights;
import gr.movieinsights.security.AuthoritiesConstants;
import gr.movieinsights.service.MovieInsightsService;
import gr.movieinsights.service.dto.movieinsights.MovieInsightsDTO;
import io.github.jhipster.web.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link gr.movieinsights.domain.MovieInsights}.
 */
@RestController
public class MovieInsightsResource extends BaseMovieInsightsResource<MovieInsights, MovieInsightsDTO, MovieInsightsService> {

    private static final String ENTITY_NAME = "movieInsights";

    public MovieInsightsResource(MovieInsightsService movieInsightsService) {
        super(movieInsightsService, ENTITY_NAME);
    }

    /**
     * {@code POST /movie-insights/clear} : clear all the movie insights
     */
    @Secured(AuthoritiesConstants.ADMIN)
    @DeleteMapping
    public ResponseEntity<Void> clear() {
        getService().clear();
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, "all")).build();
    }
}
