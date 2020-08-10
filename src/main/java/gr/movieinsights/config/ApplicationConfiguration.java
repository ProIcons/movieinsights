package gr.movieinsights.config;

import gr.movieinsights.service.ElasticsearchIndexService;
import gr.movieinsights.service.TmdbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;

@Configuration
@DependsOn("liquibase")
public class ApplicationConfiguration {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final TmdbService tmdbService;

    public ApplicationConfiguration(TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @PostConstruct
    public void initializeApplication() {
        /*if (movieService.getAllIDs().size() < 1000) {
            log.info("Application Running for the first time. Importing data...");

            dataService.beginImport();
        }
        //TODO: remove this. Is only for testing
        movieInsightsService.test();*/
        //tmdbService.initializeDemoDatabase();
        //elasticsearchIndexService.reindexAll();
    }
}

