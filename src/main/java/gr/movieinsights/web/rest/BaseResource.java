package gr.movieinsights.web.rest;

import gr.movieinsights.web.rest.movieinsights.MovieInsightsGeneralResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public abstract class BaseResource {
    protected final Logger log = LoggerFactory.getLogger(MovieInsightsGeneralResource.class);

    @Value("${jhipster.clientApp.name}")
    protected String applicationName;
}
