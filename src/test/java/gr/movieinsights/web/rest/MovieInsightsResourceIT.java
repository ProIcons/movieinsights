package gr.movieinsights.web.rest;

import gr.movieinsights.MovieInsightsApp;
import gr.movieinsights.domain.MovieInsights;
import gr.movieinsights.repository.MovieInsightsRepository;
import gr.movieinsights.repository.search.MovieInsightsSearchRepository;
import gr.movieinsights.service.MovieInsightsService;
import gr.movieinsights.service.dto.MovieInsightsDTO;
import gr.movieinsights.service.mapper.MovieInsightsMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MovieInsightsResource} REST controller.
 */
@SpringBootTest(classes = MovieInsightsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MovieInsightsResourceIT {

    private static final Double DEFAULT_AVERAGE_RATING = 1D;
    private static final Double UPDATED_AVERAGE_RATING = 2D;

    private static final Double DEFAULT_AVERAGE_BUDGET = 1D;
    private static final Double UPDATED_AVERAGE_BUDGET = 2D;

    private static final Double DEFAULT_AVERAGE_REVENUE = 1D;
    private static final Double UPDATED_AVERAGE_REVENUE = 2D;

    private static final Integer DEFAULT_TOTAL_MOVIES = 1;
    private static final Integer UPDATED_TOTAL_MOVIES = 2;

    private static final Integer DEFAULT_MOST_POPULAR_GENRE_MOVIE_COUNT = 1;
    private static final Integer UPDATED_MOST_POPULAR_GENRE_MOVIE_COUNT = 2;

    private static final Integer DEFAULT_MOST_POPULAR_ACTOR_MOVIE_COUNT = 1;
    private static final Integer UPDATED_MOST_POPULAR_ACTOR_MOVIE_COUNT = 2;

    private static final Integer DEFAULT_MOST_POPULAR_WRITER_MOVIE_COUNT = 1;
    private static final Integer UPDATED_MOST_POPULAR_WRITER_MOVIE_COUNT = 2;

    private static final Integer DEFAULT_MOST_POPULAR_PRODUCER_MOVIE_COUNT = 1;
    private static final Integer UPDATED_MOST_POPULAR_PRODUCER_MOVIE_COUNT = 2;

    private static final Integer DEFAULT_MOST_POPULAR_DIRECTOR_MOVIE_COUNT = 1;
    private static final Integer UPDATED_MOST_POPULAR_DIRECTOR_MOVIE_COUNT = 2;

    private static final Integer DEFAULT_MOST_POPULAR_PRODUCTION_COMPANY_MOVIE_COUNT = 1;
    private static final Integer UPDATED_MOST_POPULAR_PRODUCTION_COMPANY_MOVIE_COUNT = 2;

    private static final Integer DEFAULT_MOST_POPULAR_PRODUCTION_COUNTRY_MOVIE_COUNT = 1;
    private static final Integer UPDATED_MOST_POPULAR_PRODUCTION_COUNTRY_MOVIE_COUNT = 2;

    @Autowired
    private MovieInsightsRepository movieInsightsRepository;

    @Autowired
    private MovieInsightsMapper movieInsightsMapper;

    @Autowired
    private MovieInsightsService movieInsightsService;

    /**
     * This repository is mocked in the gr.movieinsights.repository.search test package.
     *
     * @see gr.movieinsights.repository.search.MovieInsightsSearchRepositoryMockConfiguration
     */
    @Autowired
    private MovieInsightsSearchRepository mockMovieInsightsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMovieInsightsMockMvc;

    private MovieInsights movieInsights;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieInsights createEntity(EntityManager em) {
        MovieInsights movieInsights = new MovieInsights()
            .averageRating(DEFAULT_AVERAGE_RATING)
            .averageBudget(DEFAULT_AVERAGE_BUDGET)
            .averageRevenue(DEFAULT_AVERAGE_REVENUE)
            .totalMovies(DEFAULT_TOTAL_MOVIES)
            .mostPopularGenreMovieCount(DEFAULT_MOST_POPULAR_GENRE_MOVIE_COUNT)
            .mostPopularActorMovieCount(DEFAULT_MOST_POPULAR_ACTOR_MOVIE_COUNT)
            .mostPopularWriterMovieCount(DEFAULT_MOST_POPULAR_WRITER_MOVIE_COUNT)
            .mostPopularProducerMovieCount(DEFAULT_MOST_POPULAR_PRODUCER_MOVIE_COUNT)
            .mostPopularDirectorMovieCount(DEFAULT_MOST_POPULAR_DIRECTOR_MOVIE_COUNT)
            .mostPopularProductionCompanyMovieCount(DEFAULT_MOST_POPULAR_PRODUCTION_COMPANY_MOVIE_COUNT)
            .mostPopularProductionCountryMovieCount(DEFAULT_MOST_POPULAR_PRODUCTION_COUNTRY_MOVIE_COUNT);
        return movieInsights;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieInsights createUpdatedEntity(EntityManager em) {
        MovieInsights movieInsights = new MovieInsights()
            .averageRating(UPDATED_AVERAGE_RATING)
            .averageBudget(UPDATED_AVERAGE_BUDGET)
            .averageRevenue(UPDATED_AVERAGE_REVENUE)
            .totalMovies(UPDATED_TOTAL_MOVIES)
            .mostPopularGenreMovieCount(UPDATED_MOST_POPULAR_GENRE_MOVIE_COUNT)
            .mostPopularActorMovieCount(UPDATED_MOST_POPULAR_ACTOR_MOVIE_COUNT)
            .mostPopularWriterMovieCount(UPDATED_MOST_POPULAR_WRITER_MOVIE_COUNT)
            .mostPopularProducerMovieCount(UPDATED_MOST_POPULAR_PRODUCER_MOVIE_COUNT)
            .mostPopularDirectorMovieCount(UPDATED_MOST_POPULAR_DIRECTOR_MOVIE_COUNT)
            .mostPopularProductionCompanyMovieCount(UPDATED_MOST_POPULAR_PRODUCTION_COMPANY_MOVIE_COUNT)
            .mostPopularProductionCountryMovieCount(UPDATED_MOST_POPULAR_PRODUCTION_COUNTRY_MOVIE_COUNT);
        return movieInsights;
    }

    @BeforeEach
    public void initTest() {
        movieInsights = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovieInsights() throws Exception {
        int databaseSizeBeforeCreate = movieInsightsRepository.findAll().size();
        // Create the MovieInsights
        MovieInsightsDTO movieInsightsDTO = movieInsightsMapper.toDto(movieInsights);
        restMovieInsightsMockMvc.perform(post("/api/movie-insights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsDTO)))
            .andExpect(status().isCreated());

        // Validate the MovieInsights in the database
        List<MovieInsights> movieInsightsList = movieInsightsRepository.findAll();
        assertThat(movieInsightsList).hasSize(databaseSizeBeforeCreate + 1);
        MovieInsights testMovieInsights = movieInsightsList.get(movieInsightsList.size() - 1);
        assertThat(testMovieInsights.getAverageRating()).isEqualTo(DEFAULT_AVERAGE_RATING);
        assertThat(testMovieInsights.getAverageBudget()).isEqualTo(DEFAULT_AVERAGE_BUDGET);
        assertThat(testMovieInsights.getAverageRevenue()).isEqualTo(DEFAULT_AVERAGE_REVENUE);
        assertThat(testMovieInsights.getTotalMovies()).isEqualTo(DEFAULT_TOTAL_MOVIES);
        assertThat(testMovieInsights.getMostPopularGenreMovieCount()).isEqualTo(DEFAULT_MOST_POPULAR_GENRE_MOVIE_COUNT);
        assertThat(testMovieInsights.getMostPopularActorMovieCount()).isEqualTo(DEFAULT_MOST_POPULAR_ACTOR_MOVIE_COUNT);
        assertThat(testMovieInsights.getMostPopularWriterMovieCount()).isEqualTo(DEFAULT_MOST_POPULAR_WRITER_MOVIE_COUNT);
        assertThat(testMovieInsights.getMostPopularProducerMovieCount()).isEqualTo(DEFAULT_MOST_POPULAR_PRODUCER_MOVIE_COUNT);
        assertThat(testMovieInsights.getMostPopularDirectorMovieCount()).isEqualTo(DEFAULT_MOST_POPULAR_DIRECTOR_MOVIE_COUNT);
        assertThat(testMovieInsights.getMostPopularProductionCompanyMovieCount()).isEqualTo(DEFAULT_MOST_POPULAR_PRODUCTION_COMPANY_MOVIE_COUNT);
        assertThat(testMovieInsights.getMostPopularProductionCountryMovieCount()).isEqualTo(DEFAULT_MOST_POPULAR_PRODUCTION_COUNTRY_MOVIE_COUNT);

        // Validate the MovieInsights in Elasticsearch
        verify(mockMovieInsightsSearchRepository, times(1)).save(testMovieInsights);
    }

    @Test
    @Transactional
    public void createMovieInsightsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieInsightsRepository.findAll().size();

        // Create the MovieInsights with an existing ID
        movieInsights.setId(1L);
        MovieInsightsDTO movieInsightsDTO = movieInsightsMapper.toDto(movieInsights);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieInsightsMockMvc.perform(post("/api/movie-insights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieInsights in the database
        List<MovieInsights> movieInsightsList = movieInsightsRepository.findAll();
        assertThat(movieInsightsList).hasSize(databaseSizeBeforeCreate);

        // Validate the MovieInsights in Elasticsearch
        verify(mockMovieInsightsSearchRepository, times(0)).save(movieInsights);
    }


    @Test
    @Transactional
    public void checkAverageRatingIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieInsightsRepository.findAll().size();
        // set the field null
        movieInsights.setAverageRating(null);

        // Create the MovieInsights, which fails.
        MovieInsightsDTO movieInsightsDTO = movieInsightsMapper.toDto(movieInsights);


        restMovieInsightsMockMvc.perform(post("/api/movie-insights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsDTO)))
            .andExpect(status().isBadRequest());

        List<MovieInsights> movieInsightsList = movieInsightsRepository.findAll();
        assertThat(movieInsightsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAverageBudgetIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieInsightsRepository.findAll().size();
        // set the field null
        movieInsights.setAverageBudget(null);

        // Create the MovieInsights, which fails.
        MovieInsightsDTO movieInsightsDTO = movieInsightsMapper.toDto(movieInsights);


        restMovieInsightsMockMvc.perform(post("/api/movie-insights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsDTO)))
            .andExpect(status().isBadRequest());

        List<MovieInsights> movieInsightsList = movieInsightsRepository.findAll();
        assertThat(movieInsightsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAverageRevenueIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieInsightsRepository.findAll().size();
        // set the field null
        movieInsights.setAverageRevenue(null);

        // Create the MovieInsights, which fails.
        MovieInsightsDTO movieInsightsDTO = movieInsightsMapper.toDto(movieInsights);


        restMovieInsightsMockMvc.perform(post("/api/movie-insights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsDTO)))
            .andExpect(status().isBadRequest());

        List<MovieInsights> movieInsightsList = movieInsightsRepository.findAll();
        assertThat(movieInsightsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalMoviesIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieInsightsRepository.findAll().size();
        // set the field null
        movieInsights.setTotalMovies(null);

        // Create the MovieInsights, which fails.
        MovieInsightsDTO movieInsightsDTO = movieInsightsMapper.toDto(movieInsights);


        restMovieInsightsMockMvc.perform(post("/api/movie-insights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsDTO)))
            .andExpect(status().isBadRequest());

        List<MovieInsights> movieInsightsList = movieInsightsRepository.findAll();
        assertThat(movieInsightsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMostPopularGenreMovieCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieInsightsRepository.findAll().size();
        // set the field null
        movieInsights.setMostPopularGenreMovieCount(null);

        // Create the MovieInsights, which fails.
        MovieInsightsDTO movieInsightsDTO = movieInsightsMapper.toDto(movieInsights);


        restMovieInsightsMockMvc.perform(post("/api/movie-insights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsDTO)))
            .andExpect(status().isBadRequest());

        List<MovieInsights> movieInsightsList = movieInsightsRepository.findAll();
        assertThat(movieInsightsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMostPopularActorMovieCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieInsightsRepository.findAll().size();
        // set the field null
        movieInsights.setMostPopularActorMovieCount(null);

        // Create the MovieInsights, which fails.
        MovieInsightsDTO movieInsightsDTO = movieInsightsMapper.toDto(movieInsights);


        restMovieInsightsMockMvc.perform(post("/api/movie-insights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsDTO)))
            .andExpect(status().isBadRequest());

        List<MovieInsights> movieInsightsList = movieInsightsRepository.findAll();
        assertThat(movieInsightsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMostPopularWriterMovieCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieInsightsRepository.findAll().size();
        // set the field null
        movieInsights.setMostPopularWriterMovieCount(null);

        // Create the MovieInsights, which fails.
        MovieInsightsDTO movieInsightsDTO = movieInsightsMapper.toDto(movieInsights);


        restMovieInsightsMockMvc.perform(post("/api/movie-insights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsDTO)))
            .andExpect(status().isBadRequest());

        List<MovieInsights> movieInsightsList = movieInsightsRepository.findAll();
        assertThat(movieInsightsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMostPopularProducerMovieCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieInsightsRepository.findAll().size();
        // set the field null
        movieInsights.setMostPopularProducerMovieCount(null);

        // Create the MovieInsights, which fails.
        MovieInsightsDTO movieInsightsDTO = movieInsightsMapper.toDto(movieInsights);


        restMovieInsightsMockMvc.perform(post("/api/movie-insights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsDTO)))
            .andExpect(status().isBadRequest());

        List<MovieInsights> movieInsightsList = movieInsightsRepository.findAll();
        assertThat(movieInsightsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMostPopularDirectorMovieCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieInsightsRepository.findAll().size();
        // set the field null
        movieInsights.setMostPopularDirectorMovieCount(null);

        // Create the MovieInsights, which fails.
        MovieInsightsDTO movieInsightsDTO = movieInsightsMapper.toDto(movieInsights);


        restMovieInsightsMockMvc.perform(post("/api/movie-insights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsDTO)))
            .andExpect(status().isBadRequest());

        List<MovieInsights> movieInsightsList = movieInsightsRepository.findAll();
        assertThat(movieInsightsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMostPopularProductionCompanyMovieCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieInsightsRepository.findAll().size();
        // set the field null
        movieInsights.setMostPopularProductionCompanyMovieCount(null);

        // Create the MovieInsights, which fails.
        MovieInsightsDTO movieInsightsDTO = movieInsightsMapper.toDto(movieInsights);


        restMovieInsightsMockMvc.perform(post("/api/movie-insights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsDTO)))
            .andExpect(status().isBadRequest());

        List<MovieInsights> movieInsightsList = movieInsightsRepository.findAll();
        assertThat(movieInsightsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMostPopularProductionCountryMovieCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieInsightsRepository.findAll().size();
        // set the field null
        movieInsights.setMostPopularProductionCountryMovieCount(null);

        // Create the MovieInsights, which fails.
        MovieInsightsDTO movieInsightsDTO = movieInsightsMapper.toDto(movieInsights);


        restMovieInsightsMockMvc.perform(post("/api/movie-insights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsDTO)))
            .andExpect(status().isBadRequest());

        List<MovieInsights> movieInsightsList = movieInsightsRepository.findAll();
        assertThat(movieInsightsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMovieInsights() throws Exception {
        // Initialize the database
        movieInsightsRepository.saveAndFlush(movieInsights);

        // Get all the movieInsightsList
        restMovieInsightsMockMvc.perform(get("/api/movie-insights?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieInsights.getId().intValue())))
            .andExpect(jsonPath("$.[*].averageRating").value(hasItem(DEFAULT_AVERAGE_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].averageBudget").value(hasItem(DEFAULT_AVERAGE_BUDGET.doubleValue())))
            .andExpect(jsonPath("$.[*].averageRevenue").value(hasItem(DEFAULT_AVERAGE_REVENUE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalMovies").value(hasItem(DEFAULT_TOTAL_MOVIES)))
            .andExpect(jsonPath("$.[*].mostPopularGenreMovieCount").value(hasItem(DEFAULT_MOST_POPULAR_GENRE_MOVIE_COUNT)))
            .andExpect(jsonPath("$.[*].mostPopularActorMovieCount").value(hasItem(DEFAULT_MOST_POPULAR_ACTOR_MOVIE_COUNT)))
            .andExpect(jsonPath("$.[*].mostPopularWriterMovieCount").value(hasItem(DEFAULT_MOST_POPULAR_WRITER_MOVIE_COUNT)))
            .andExpect(jsonPath("$.[*].mostPopularProducerMovieCount").value(hasItem(DEFAULT_MOST_POPULAR_PRODUCER_MOVIE_COUNT)))
            .andExpect(jsonPath("$.[*].mostPopularDirectorMovieCount").value(hasItem(DEFAULT_MOST_POPULAR_DIRECTOR_MOVIE_COUNT)))
            .andExpect(jsonPath("$.[*].mostPopularProductionCompanyMovieCount").value(hasItem(DEFAULT_MOST_POPULAR_PRODUCTION_COMPANY_MOVIE_COUNT)))
            .andExpect(jsonPath("$.[*].mostPopularProductionCountryMovieCount").value(hasItem(DEFAULT_MOST_POPULAR_PRODUCTION_COUNTRY_MOVIE_COUNT)));
    }
    
    @Test
    @Transactional
    public void getMovieInsights() throws Exception {
        // Initialize the database
        movieInsightsRepository.saveAndFlush(movieInsights);

        // Get the movieInsights
        restMovieInsightsMockMvc.perform(get("/api/movie-insights/{id}", movieInsights.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movieInsights.getId().intValue()))
            .andExpect(jsonPath("$.averageRating").value(DEFAULT_AVERAGE_RATING.doubleValue()))
            .andExpect(jsonPath("$.averageBudget").value(DEFAULT_AVERAGE_BUDGET.doubleValue()))
            .andExpect(jsonPath("$.averageRevenue").value(DEFAULT_AVERAGE_REVENUE.doubleValue()))
            .andExpect(jsonPath("$.totalMovies").value(DEFAULT_TOTAL_MOVIES))
            .andExpect(jsonPath("$.mostPopularGenreMovieCount").value(DEFAULT_MOST_POPULAR_GENRE_MOVIE_COUNT))
            .andExpect(jsonPath("$.mostPopularActorMovieCount").value(DEFAULT_MOST_POPULAR_ACTOR_MOVIE_COUNT))
            .andExpect(jsonPath("$.mostPopularWriterMovieCount").value(DEFAULT_MOST_POPULAR_WRITER_MOVIE_COUNT))
            .andExpect(jsonPath("$.mostPopularProducerMovieCount").value(DEFAULT_MOST_POPULAR_PRODUCER_MOVIE_COUNT))
            .andExpect(jsonPath("$.mostPopularDirectorMovieCount").value(DEFAULT_MOST_POPULAR_DIRECTOR_MOVIE_COUNT))
            .andExpect(jsonPath("$.mostPopularProductionCompanyMovieCount").value(DEFAULT_MOST_POPULAR_PRODUCTION_COMPANY_MOVIE_COUNT))
            .andExpect(jsonPath("$.mostPopularProductionCountryMovieCount").value(DEFAULT_MOST_POPULAR_PRODUCTION_COUNTRY_MOVIE_COUNT));
    }
    @Test
    @Transactional
    public void getNonExistingMovieInsights() throws Exception {
        // Get the movieInsights
        restMovieInsightsMockMvc.perform(get("/api/movie-insights/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovieInsights() throws Exception {
        // Initialize the database
        movieInsightsRepository.saveAndFlush(movieInsights);

        int databaseSizeBeforeUpdate = movieInsightsRepository.findAll().size();

        // Update the movieInsights
        MovieInsights updatedMovieInsights = movieInsightsRepository.findById(movieInsights.getId()).get();
        // Disconnect from session so that the updates on updatedMovieInsights are not directly saved in db
        em.detach(updatedMovieInsights);
        updatedMovieInsights
            .averageRating(UPDATED_AVERAGE_RATING)
            .averageBudget(UPDATED_AVERAGE_BUDGET)
            .averageRevenue(UPDATED_AVERAGE_REVENUE)
            .totalMovies(UPDATED_TOTAL_MOVIES)
            .mostPopularGenreMovieCount(UPDATED_MOST_POPULAR_GENRE_MOVIE_COUNT)
            .mostPopularActorMovieCount(UPDATED_MOST_POPULAR_ACTOR_MOVIE_COUNT)
            .mostPopularWriterMovieCount(UPDATED_MOST_POPULAR_WRITER_MOVIE_COUNT)
            .mostPopularProducerMovieCount(UPDATED_MOST_POPULAR_PRODUCER_MOVIE_COUNT)
            .mostPopularDirectorMovieCount(UPDATED_MOST_POPULAR_DIRECTOR_MOVIE_COUNT)
            .mostPopularProductionCompanyMovieCount(UPDATED_MOST_POPULAR_PRODUCTION_COMPANY_MOVIE_COUNT)
            .mostPopularProductionCountryMovieCount(UPDATED_MOST_POPULAR_PRODUCTION_COUNTRY_MOVIE_COUNT);
        MovieInsightsDTO movieInsightsDTO = movieInsightsMapper.toDto(updatedMovieInsights);

        restMovieInsightsMockMvc.perform(put("/api/movie-insights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsDTO)))
            .andExpect(status().isOk());

        // Validate the MovieInsights in the database
        List<MovieInsights> movieInsightsList = movieInsightsRepository.findAll();
        assertThat(movieInsightsList).hasSize(databaseSizeBeforeUpdate);
        MovieInsights testMovieInsights = movieInsightsList.get(movieInsightsList.size() - 1);
        assertThat(testMovieInsights.getAverageRating()).isEqualTo(UPDATED_AVERAGE_RATING);
        assertThat(testMovieInsights.getAverageBudget()).isEqualTo(UPDATED_AVERAGE_BUDGET);
        assertThat(testMovieInsights.getAverageRevenue()).isEqualTo(UPDATED_AVERAGE_REVENUE);
        assertThat(testMovieInsights.getTotalMovies()).isEqualTo(UPDATED_TOTAL_MOVIES);
        assertThat(testMovieInsights.getMostPopularGenreMovieCount()).isEqualTo(UPDATED_MOST_POPULAR_GENRE_MOVIE_COUNT);
        assertThat(testMovieInsights.getMostPopularActorMovieCount()).isEqualTo(UPDATED_MOST_POPULAR_ACTOR_MOVIE_COUNT);
        assertThat(testMovieInsights.getMostPopularWriterMovieCount()).isEqualTo(UPDATED_MOST_POPULAR_WRITER_MOVIE_COUNT);
        assertThat(testMovieInsights.getMostPopularProducerMovieCount()).isEqualTo(UPDATED_MOST_POPULAR_PRODUCER_MOVIE_COUNT);
        assertThat(testMovieInsights.getMostPopularDirectorMovieCount()).isEqualTo(UPDATED_MOST_POPULAR_DIRECTOR_MOVIE_COUNT);
        assertThat(testMovieInsights.getMostPopularProductionCompanyMovieCount()).isEqualTo(UPDATED_MOST_POPULAR_PRODUCTION_COMPANY_MOVIE_COUNT);
        assertThat(testMovieInsights.getMostPopularProductionCountryMovieCount()).isEqualTo(UPDATED_MOST_POPULAR_PRODUCTION_COUNTRY_MOVIE_COUNT);

        // Validate the MovieInsights in Elasticsearch
        verify(mockMovieInsightsSearchRepository, times(1)).save(testMovieInsights);
    }

    @Test
    @Transactional
    public void updateNonExistingMovieInsights() throws Exception {
        int databaseSizeBeforeUpdate = movieInsightsRepository.findAll().size();

        // Create the MovieInsights
        MovieInsightsDTO movieInsightsDTO = movieInsightsMapper.toDto(movieInsights);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieInsightsMockMvc.perform(put("/api/movie-insights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieInsights in the database
        List<MovieInsights> movieInsightsList = movieInsightsRepository.findAll();
        assertThat(movieInsightsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MovieInsights in Elasticsearch
        verify(mockMovieInsightsSearchRepository, times(0)).save(movieInsights);
    }

    @Test
    @Transactional
    public void deleteMovieInsights() throws Exception {
        // Initialize the database
        movieInsightsRepository.saveAndFlush(movieInsights);

        int databaseSizeBeforeDelete = movieInsightsRepository.findAll().size();

        // Delete the movieInsights
        restMovieInsightsMockMvc.perform(delete("/api/movie-insights/{id}", movieInsights.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MovieInsights> movieInsightsList = movieInsightsRepository.findAll();
        assertThat(movieInsightsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MovieInsights in Elasticsearch
        verify(mockMovieInsightsSearchRepository, times(1)).deleteById(movieInsights.getId());
    }

    @Test
    @Transactional
    public void searchMovieInsights() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        movieInsightsRepository.saveAndFlush(movieInsights);
        when(mockMovieInsightsSearchRepository.search(queryStringQuery("id:" + movieInsights.getId())))
            .thenReturn(Collections.singletonList(movieInsights));

        // Search the movieInsights
        restMovieInsightsMockMvc.perform(get("/api/_search/movie-insights?query=id:" + movieInsights.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieInsights.getId().intValue())))
            .andExpect(jsonPath("$.[*].averageRating").value(hasItem(DEFAULT_AVERAGE_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].averageBudget").value(hasItem(DEFAULT_AVERAGE_BUDGET.doubleValue())))
            .andExpect(jsonPath("$.[*].averageRevenue").value(hasItem(DEFAULT_AVERAGE_REVENUE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalMovies").value(hasItem(DEFAULT_TOTAL_MOVIES)))
            .andExpect(jsonPath("$.[*].mostPopularGenreMovieCount").value(hasItem(DEFAULT_MOST_POPULAR_GENRE_MOVIE_COUNT)))
            .andExpect(jsonPath("$.[*].mostPopularActorMovieCount").value(hasItem(DEFAULT_MOST_POPULAR_ACTOR_MOVIE_COUNT)))
            .andExpect(jsonPath("$.[*].mostPopularWriterMovieCount").value(hasItem(DEFAULT_MOST_POPULAR_WRITER_MOVIE_COUNT)))
            .andExpect(jsonPath("$.[*].mostPopularProducerMovieCount").value(hasItem(DEFAULT_MOST_POPULAR_PRODUCER_MOVIE_COUNT)))
            .andExpect(jsonPath("$.[*].mostPopularDirectorMovieCount").value(hasItem(DEFAULT_MOST_POPULAR_DIRECTOR_MOVIE_COUNT)))
            .andExpect(jsonPath("$.[*].mostPopularProductionCompanyMovieCount").value(hasItem(DEFAULT_MOST_POPULAR_PRODUCTION_COMPANY_MOVIE_COUNT)))
            .andExpect(jsonPath("$.[*].mostPopularProductionCountryMovieCount").value(hasItem(DEFAULT_MOST_POPULAR_PRODUCTION_COUNTRY_MOVIE_COUNT)));
    }
}
