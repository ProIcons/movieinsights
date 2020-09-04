package gr.movieinsights.config;

import gr.movieinsights.repository.GenreRepository;
import gr.movieinsights.repository.MovieInsightsRepository;
import gr.movieinsights.repository.VoteRepository;
import gr.movieinsights.repository.search.GenreSearchRepository;
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
    private final MovieInsightsRepository movieInsightsRepository;
    private final ElasticsearchIndexService elasticsearchIndexService;
    private final GenreSearchRepository genreSearchRepository;
    private final GenreRepository genreRepository;


    public ApplicationConfiguration(VoteRepository voteRepository, ImportService importService, MovieInsightsService movieInsightsService, MovieInsightsRepository movieInsightsRepository, ElasticsearchIndexService elasticsearchIndexService, GenreSearchRepository genreSearchRepository, GenreRepository genreRepository) {
        this.voteRepository = voteRepository;
        this.importService = importService;
        this.movieInsightsService = movieInsightsService;
        this.movieInsightsRepository = movieInsightsRepository;
        this.elasticsearchIndexService = elasticsearchIndexService;
        this.genreSearchRepository = genreSearchRepository;
        this.genreRepository = genreRepository;
    }

    @PostConstruct
    public void initializeApplication() {
        if (voteRepository.count() == 0) {
            log.info("Application Running for the first time.");

            log.info("Importing Data...");
            importService.initializeDemoDatabase();
        }
        if (movieInsightsRepository.count() == 0) {
            log.info("Calculating Movie Insights...");
            movieInsightsService.generateAndSaveMovieInsights();
        }
        if (genreSearchRepository.count() == 0) {
            log.info("Reindexing Elasticsearch...");
            elasticsearchIndexService.reindexAll();
        }
    }
}

