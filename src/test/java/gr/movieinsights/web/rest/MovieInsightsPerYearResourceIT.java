package gr.movieinsights.web.rest;

import gr.movieinsights.MovieInsightsApp;
import gr.movieinsights.domain.MovieInsightsPerYear;
import gr.movieinsights.domain.MovieInsights;
import gr.movieinsights.repository.MovieInsightsPerYearRepository;
import gr.movieinsights.repository.search.MovieInsightsPerYearSearchRepository;
import gr.movieinsights.service.MovieInsightsPerYearService;
import gr.movieinsights.service.dto.MovieInsightsPerYearDTO;
import gr.movieinsights.service.mapper.MovieInsightsPerYearMapper;

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
 * Integration tests for the {@link MovieInsightsPerYearResource} REST controller.
 */
@SpringBootTest(classes = MovieInsightsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MovieInsightsPerYearResourceIT {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    @Autowired
    private MovieInsightsPerYearRepository movieInsightsPerYearRepository;

    @Autowired
    private MovieInsightsPerYearMapper movieInsightsPerYearMapper;

    @Autowired
    private MovieInsightsPerYearService movieInsightsPerYearService;

    /**
     * This repository is mocked in the gr.movieinsights.repository.search test package.
     *
     * @see gr.movieinsights.repository.search.MovieInsightsPerYearSearchRepositoryMockConfiguration
     */
    @Autowired
    private MovieInsightsPerYearSearchRepository mockMovieInsightsPerYearSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMovieInsightsPerYearMockMvc;

    private MovieInsightsPerYear movieInsightsPerYear;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieInsightsPerYear createEntity(EntityManager em) {
        MovieInsightsPerYear movieInsightsPerYear = new MovieInsightsPerYear()
            .year(DEFAULT_YEAR);
        // Add required entity
        MovieInsights movieInsights;
        if (TestUtil.findAll(em, MovieInsights.class).isEmpty()) {
            movieInsights = MovieInsightsResourceIT.createEntity(em);
            em.persist(movieInsights);
            em.flush();
        } else {
            movieInsights = TestUtil.findAll(em, MovieInsights.class).get(0);
        }
        movieInsightsPerYear.setMovieInsights(movieInsights);
        return movieInsightsPerYear;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieInsightsPerYear createUpdatedEntity(EntityManager em) {
        MovieInsightsPerYear movieInsightsPerYear = new MovieInsightsPerYear()
            .year(UPDATED_YEAR);
        // Add required entity
        MovieInsights movieInsights;
        if (TestUtil.findAll(em, MovieInsights.class).isEmpty()) {
            movieInsights = MovieInsightsResourceIT.createUpdatedEntity(em);
            em.persist(movieInsights);
            em.flush();
        } else {
            movieInsights = TestUtil.findAll(em, MovieInsights.class).get(0);
        }
        movieInsightsPerYear.setMovieInsights(movieInsights);
        return movieInsightsPerYear;
    }

    @BeforeEach
    public void initTest() {
        movieInsightsPerYear = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovieInsightsPerYear() throws Exception {
        int databaseSizeBeforeCreate = movieInsightsPerYearRepository.findAll().size();
        // Create the MovieInsightsPerYear
        MovieInsightsPerYearDTO movieInsightsPerYearDTO = movieInsightsPerYearMapper.toDto(movieInsightsPerYear);
        restMovieInsightsPerYearMockMvc.perform(post("/api/movie-insights-per-years")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerYearDTO)))
            .andExpect(status().isCreated());

        // Validate the MovieInsightsPerYear in the database
        List<MovieInsightsPerYear> movieInsightsPerYearList = movieInsightsPerYearRepository.findAll();
        assertThat(movieInsightsPerYearList).hasSize(databaseSizeBeforeCreate + 1);
        MovieInsightsPerYear testMovieInsightsPerYear = movieInsightsPerYearList.get(movieInsightsPerYearList.size() - 1);
        assertThat(testMovieInsightsPerYear.getYear()).isEqualTo(DEFAULT_YEAR);

        // Validate the MovieInsightsPerYear in Elasticsearch
        verify(mockMovieInsightsPerYearSearchRepository, times(1)).save(testMovieInsightsPerYear);
    }

    @Test
    @Transactional
    public void createMovieInsightsPerYearWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieInsightsPerYearRepository.findAll().size();

        // Create the MovieInsightsPerYear with an existing ID
        movieInsightsPerYear.setId(1L);
        MovieInsightsPerYearDTO movieInsightsPerYearDTO = movieInsightsPerYearMapper.toDto(movieInsightsPerYear);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieInsightsPerYearMockMvc.perform(post("/api/movie-insights-per-years")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerYearDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieInsightsPerYear in the database
        List<MovieInsightsPerYear> movieInsightsPerYearList = movieInsightsPerYearRepository.findAll();
        assertThat(movieInsightsPerYearList).hasSize(databaseSizeBeforeCreate);

        // Validate the MovieInsightsPerYear in Elasticsearch
        verify(mockMovieInsightsPerYearSearchRepository, times(0)).save(movieInsightsPerYear);
    }


    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieInsightsPerYearRepository.findAll().size();
        // set the field null
        movieInsightsPerYear.setYear(null);

        // Create the MovieInsightsPerYear, which fails.
        MovieInsightsPerYearDTO movieInsightsPerYearDTO = movieInsightsPerYearMapper.toDto(movieInsightsPerYear);


        restMovieInsightsPerYearMockMvc.perform(post("/api/movie-insights-per-years")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerYearDTO)))
            .andExpect(status().isBadRequest());

        List<MovieInsightsPerYear> movieInsightsPerYearList = movieInsightsPerYearRepository.findAll();
        assertThat(movieInsightsPerYearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMovieInsightsPerYears() throws Exception {
        // Initialize the database
        movieInsightsPerYearRepository.saveAndFlush(movieInsightsPerYear);

        // Get all the movieInsightsPerYearList
        restMovieInsightsPerYearMockMvc.perform(get("/api/movie-insights-per-years?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieInsightsPerYear.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)));
    }
    
    @Test
    @Transactional
    public void getMovieInsightsPerYear() throws Exception {
        // Initialize the database
        movieInsightsPerYearRepository.saveAndFlush(movieInsightsPerYear);

        // Get the movieInsightsPerYear
        restMovieInsightsPerYearMockMvc.perform(get("/api/movie-insights-per-years/{id}", movieInsightsPerYear.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movieInsightsPerYear.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR));
    }
    @Test
    @Transactional
    public void getNonExistingMovieInsightsPerYear() throws Exception {
        // Get the movieInsightsPerYear
        restMovieInsightsPerYearMockMvc.perform(get("/api/movie-insights-per-years/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovieInsightsPerYear() throws Exception {
        // Initialize the database
        movieInsightsPerYearRepository.saveAndFlush(movieInsightsPerYear);

        int databaseSizeBeforeUpdate = movieInsightsPerYearRepository.findAll().size();

        // Update the movieInsightsPerYear
        MovieInsightsPerYear updatedMovieInsightsPerYear = movieInsightsPerYearRepository.findById(movieInsightsPerYear.getId()).get();
        // Disconnect from session so that the updates on updatedMovieInsightsPerYear are not directly saved in db
        em.detach(updatedMovieInsightsPerYear);
        updatedMovieInsightsPerYear
            .year(UPDATED_YEAR);
        MovieInsightsPerYearDTO movieInsightsPerYearDTO = movieInsightsPerYearMapper.toDto(updatedMovieInsightsPerYear);

        restMovieInsightsPerYearMockMvc.perform(put("/api/movie-insights-per-years")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerYearDTO)))
            .andExpect(status().isOk());

        // Validate the MovieInsightsPerYear in the database
        List<MovieInsightsPerYear> movieInsightsPerYearList = movieInsightsPerYearRepository.findAll();
        assertThat(movieInsightsPerYearList).hasSize(databaseSizeBeforeUpdate);
        MovieInsightsPerYear testMovieInsightsPerYear = movieInsightsPerYearList.get(movieInsightsPerYearList.size() - 1);
        assertThat(testMovieInsightsPerYear.getYear()).isEqualTo(UPDATED_YEAR);

        // Validate the MovieInsightsPerYear in Elasticsearch
        verify(mockMovieInsightsPerYearSearchRepository, times(1)).save(testMovieInsightsPerYear);
    }

    @Test
    @Transactional
    public void updateNonExistingMovieInsightsPerYear() throws Exception {
        int databaseSizeBeforeUpdate = movieInsightsPerYearRepository.findAll().size();

        // Create the MovieInsightsPerYear
        MovieInsightsPerYearDTO movieInsightsPerYearDTO = movieInsightsPerYearMapper.toDto(movieInsightsPerYear);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieInsightsPerYearMockMvc.perform(put("/api/movie-insights-per-years")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerYearDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieInsightsPerYear in the database
        List<MovieInsightsPerYear> movieInsightsPerYearList = movieInsightsPerYearRepository.findAll();
        assertThat(movieInsightsPerYearList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MovieInsightsPerYear in Elasticsearch
        verify(mockMovieInsightsPerYearSearchRepository, times(0)).save(movieInsightsPerYear);
    }

    @Test
    @Transactional
    public void deleteMovieInsightsPerYear() throws Exception {
        // Initialize the database
        movieInsightsPerYearRepository.saveAndFlush(movieInsightsPerYear);

        int databaseSizeBeforeDelete = movieInsightsPerYearRepository.findAll().size();

        // Delete the movieInsightsPerYear
        restMovieInsightsPerYearMockMvc.perform(delete("/api/movie-insights-per-years/{id}", movieInsightsPerYear.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MovieInsightsPerYear> movieInsightsPerYearList = movieInsightsPerYearRepository.findAll();
        assertThat(movieInsightsPerYearList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MovieInsightsPerYear in Elasticsearch
        verify(mockMovieInsightsPerYearSearchRepository, times(1)).deleteById(movieInsightsPerYear.getId());
    }

    @Test
    @Transactional
    public void searchMovieInsightsPerYear() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        movieInsightsPerYearRepository.saveAndFlush(movieInsightsPerYear);
        when(mockMovieInsightsPerYearSearchRepository.search(queryStringQuery("id:" + movieInsightsPerYear.getId())))
            .thenReturn(Collections.singletonList(movieInsightsPerYear));

        // Search the movieInsightsPerYear
        restMovieInsightsPerYearMockMvc.perform(get("/api/_search/movie-insights-per-years?query=id:" + movieInsightsPerYear.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieInsightsPerYear.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)));
    }
}
