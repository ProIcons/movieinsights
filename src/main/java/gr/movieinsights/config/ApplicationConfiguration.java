package gr.movieinsights.config;

import gr.movieinsights.repository.VoteRepository;
import gr.movieinsights.service.ElasticsearchIndexService;
import gr.movieinsights.service.ImportService;
import gr.movieinsights.service.MovieInsightsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;

@Configuration
@DependsOn("liquibase")
public class ApplicationConfiguration {
    private final Logger log = LoggerFactory.getLogger(getClass());


    private final VoteRepository voteRepository;
    private final ImportService importService;
    private final MovieInsightsService movieInsightsService;
private final ElasticsearchIndexService elasticsearchIndexService;


    public ApplicationConfiguration(VoteRepository voteRepository, ImportService importService, MovieInsightsService movieInsightsService, ElasticsearchIndexService elasticsearchIndexService) {
        this.voteRepository = voteRepository;
        this.importService = importService;
        this.movieInsightsService = movieInsightsService;
        this.elasticsearchIndexService = elasticsearchIndexService;
    }

    @PostConstruct
    public void initializeApplication() {
        if (voteRepository.count() == 0) {
            log.info("Application Running for the first time.");

            log.info("Importing Data...");
            importService.initializeDemoDatabase();

            log.info("Calculating Movie Insights...");
            movieInsightsService.calculateMovieInsights();

            log.info("Reindexing Elasticsearch...");
            elasticsearchIndexService.reindexAll();
        }
    }
}

