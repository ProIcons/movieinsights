package gr.movieinsights.web.rest;

import gr.movieinsights.MovieInsightsApp;
import gr.movieinsights.domain.MovieInsightsPerCountry;
import gr.movieinsights.domain.MovieInsights;
import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.repository.MovieInsightsPerCountryRepository;
import gr.movieinsights.repository.search.MovieInsightsPerCountrySearchRepository;
import gr.movieinsights.service.MovieInsightsPerCountryService;
import gr.movieinsights.service.dto.MovieInsightsPerCountryDTO;
import gr.movieinsights.service.mapper.MovieInsightsPerCountryMapper;

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
 * Integration tests for the {@link MovieInsightsPerCountryResource} REST controller.
 */
@SpringBootTest(classes = MovieInsightsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MovieInsightsPerCountryResourceIT {

    @Autowired
    private MovieInsightsPerCountryRepository movieInsightsPerCountryRepository;

    @Autowired
    private MovieInsightsPerCountryMapper movieInsightsPerCountryMapper;

    @Autowired
    private MovieInsightsPerCountryService movieInsightsPerCountryService;

    /**
     * This repository is mocked in the gr.movieinsights.repository.search test package.
     *
     * @see gr.movieinsights.repository.search.MovieInsightsPerCountrySearchRepositoryMockConfiguration
     */
    @Autowired
    private MovieInsightsPerCountrySearchRepository mockMovieInsightsPerCountrySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMovieInsightsPerCountryMockMvc;

    private MovieInsightsPerCountry movieInsightsPerCountry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieInsightsPerCountry createEntity(EntityManager em) {
        MovieInsightsPerCountry movieInsightsPerCountry = new MovieInsightsPerCountry();
        // Add required entity
        MovieInsights movieInsights;
        if (TestUtil.findAll(em, MovieInsights.class).isEmpty()) {
            movieInsights = MovieInsightsResourceIT.createEntity(em);
            em.persist(movieInsights);
            em.flush();
        } else {
            movieInsights = TestUtil.findAll(em, MovieInsights.class).get(0);
        }
        movieInsightsPerCountry.setMovieInsights(movieInsights);
        // Add required entity
        ProductionCountry productionCountry;
        if (TestUtil.findAll(em, ProductionCountry.class).isEmpty()) {
            productionCountry = ProductionCountryResourceIT.createEntity(em);
            em.persist(productionCountry);
            em.flush();
        } else {
            productionCountry = TestUtil.findAll(em, ProductionCountry.class).get(0);
        }
        movieInsightsPerCountry.setCountry(productionCountry);
        return movieInsightsPerCountry;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieInsightsPerCountry createUpdatedEntity(EntityManager em) {
        MovieInsightsPerCountry movieInsightsPerCountry = new MovieInsightsPerCountry();
        // Add required entity
        MovieInsights movieInsights;
        if (TestUtil.findAll(em, MovieInsights.class).isEmpty()) {
            movieInsights = MovieInsightsResourceIT.createUpdatedEntity(em);
            em.persist(movieInsights);
            em.flush();
        } else {
            movieInsights = TestUtil.findAll(em, MovieInsights.class).get(0);
        }
        movieInsightsPerCountry.setMovieInsights(movieInsights);
        // Add required entity
        ProductionCountry productionCountry;
        if (TestUtil.findAll(em, ProductionCountry.class).isEmpty()) {
            productionCountry = ProductionCountryResourceIT.createUpdatedEntity(em);
            em.persist(productionCountry);
            em.flush();
        } else {
            productionCountry = TestUtil.findAll(em, ProductionCountry.class).get(0);
        }
        movieInsightsPerCountry.setCountry(productionCountry);
        return movieInsightsPerCountry;
    }

    @BeforeEach
    public void initTest() {
        movieInsightsPerCountry = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovieInsightsPerCountry() throws Exception {
        int databaseSizeBeforeCreate = movieInsightsPerCountryRepository.findAll().size();
        // Create the MovieInsightsPerCountry
        MovieInsightsPerCountryDTO movieInsightsPerCountryDTO = movieInsightsPerCountryMapper.toDto(movieInsightsPerCountry);
        restMovieInsightsPerCountryMockMvc.perform(post("/api/movie-insights-per-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerCountryDTO)))
            .andExpect(status().isCreated());

        // Validate the MovieInsightsPerCountry in the database
        List<MovieInsightsPerCountry> movieInsightsPerCountryList = movieInsightsPerCountryRepository.findAll();
        assertThat(movieInsightsPerCountryList).hasSize(databaseSizeBeforeCreate + 1);
        MovieInsightsPerCountry testMovieInsightsPerCountry = movieInsightsPerCountryList.get(movieInsightsPerCountryList.size() - 1);

        // Validate the MovieInsightsPerCountry in Elasticsearch
        verify(mockMovieInsightsPerCountrySearchRepository, times(1)).save(testMovieInsightsPerCountry);
    }

    @Test
    @Transactional
    public void createMovieInsightsPerCountryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieInsightsPerCountryRepository.findAll().size();

        // Create the MovieInsightsPerCountry with an existing ID
        movieInsightsPerCountry.setId(1L);
        MovieInsightsPerCountryDTO movieInsightsPerCountryDTO = movieInsightsPerCountryMapper.toDto(movieInsightsPerCountry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieInsightsPerCountryMockMvc.perform(post("/api/movie-insights-per-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerCountryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieInsightsPerCountry in the database
        List<MovieInsightsPerCountry> movieInsightsPerCountryList = movieInsightsPerCountryRepository.findAll();
        assertThat(movieInsightsPerCountryList).hasSize(databaseSizeBeforeCreate);

        // Validate the MovieInsightsPerCountry in Elasticsearch
        verify(mockMovieInsightsPerCountrySearchRepository, times(0)).save(movieInsightsPerCountry);
    }


    @Test
    @Transactional
    public void getAllMovieInsightsPerCountries() throws Exception {
        // Initialize the database
        movieInsightsPerCountryRepository.saveAndFlush(movieInsightsPerCountry);

        // Get all the movieInsightsPerCountryList
        restMovieInsightsPerCountryMockMvc.perform(get("/api/movie-insights-per-countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieInsightsPerCountry.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getMovieInsightsPerCountry() throws Exception {
        // Initialize the database
        movieInsightsPerCountryRepository.saveAndFlush(movieInsightsPerCountry);

        // Get the movieInsightsPerCountry
        restMovieInsightsPerCountryMockMvc.perform(get("/api/movie-insights-per-countries/{id}", movieInsightsPerCountry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movieInsightsPerCountry.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingMovieInsightsPerCountry() throws Exception {
        // Get the movieInsightsPerCountry
        restMovieInsightsPerCountryMockMvc.perform(get("/api/movie-insights-per-countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovieInsightsPerCountry() throws Exception {
        // Initialize the database
        movieInsightsPerCountryRepository.saveAndFlush(movieInsightsPerCountry);

        int databaseSizeBeforeUpdate = movieInsightsPerCountryRepository.findAll().size();

        // Update the movieInsightsPerCountry
        MovieInsightsPerCountry updatedMovieInsightsPerCountry = movieInsightsPerCountryRepository.findById(movieInsightsPerCountry.getId()).get();
        // Disconnect from session so that the updates on updatedMovieInsightsPerCountry are not directly saved in db
        em.detach(updatedMovieInsightsPerCountry);
        MovieInsightsPerCountryDTO movieInsightsPerCountryDTO = movieInsightsPerCountryMapper.toDto(updatedMovieInsightsPerCountry);

        restMovieInsightsPerCountryMockMvc.perform(put("/api/movie-insights-per-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerCountryDTO)))
            .andExpect(status().isOk());

        // Validate the MovieInsightsPerCountry in the database
        List<MovieInsightsPerCountry> movieInsightsPerCountryList = movieInsightsPerCountryRepository.findAll();
        assertThat(movieInsightsPerCountryList).hasSize(databaseSizeBeforeUpdate);
        MovieInsightsPerCountry testMovieInsightsPerCountry = movieInsightsPerCountryList.get(movieInsightsPerCountryList.size() - 1);

        // Validate the MovieInsightsPerCountry in Elasticsearch
        verify(mockMovieInsightsPerCountrySearchRepository, times(1)).save(testMovieInsightsPerCountry);
    }

    @Test
    @Transactional
    public void updateNonExistingMovieInsightsPerCountry() throws Exception {
        int databaseSizeBeforeUpdate = movieInsightsPerCountryRepository.findAll().size();

        // Create the MovieInsightsPerCountry
        MovieInsightsPerCountryDTO movieInsightsPerCountryDTO = movieInsightsPerCountryMapper.toDto(movieInsightsPerCountry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieInsightsPerCountryMockMvc.perform(put("/api/movie-insights-per-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerCountryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieInsightsPerCountry in the database
        List<MovieInsightsPerCountry> movieInsightsPerCountryList = movieInsightsPerCountryRepository.findAll();
        assertThat(movieInsightsPerCountryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MovieInsightsPerCountry in Elasticsearch
        verify(mockMovieInsightsPerCountrySearchRepository, times(0)).save(movieInsightsPerCountry);
    }

    @Test
    @Transactional
    public void deleteMovieInsightsPerCountry() throws Exception {
        // Initialize the database
        movieInsightsPerCountryRepository.saveAndFlush(movieInsightsPerCountry);

        int databaseSizeBeforeDelete = movieInsightsPerCountryRepository.findAll().size();

        // Delete the movieInsightsPerCountry
        restMovieInsightsPerCountryMockMvc.perform(delete("/api/movie-insights-per-countries/{id}", movieInsightsPerCountry.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MovieInsightsPerCountry> movieInsightsPerCountryList = movieInsightsPerCountryRepository.findAll();
        assertThat(movieInsightsPerCountryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MovieInsightsPerCountry in Elasticsearch
        verify(mockMovieInsightsPerCountrySearchRepository, times(1)).deleteById(movieInsightsPerCountry.getId());
    }

    @Test
    @Transactional
    public void searchMovieInsightsPerCountry() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        movieInsightsPerCountryRepository.saveAndFlush(movieInsightsPerCountry);
        when(mockMovieInsightsPerCountrySearchRepository.search(queryStringQuery("id:" + movieInsightsPerCountry.getId())))
            .thenReturn(Collections.singletonList(movieInsightsPerCountry));

        // Search the movieInsightsPerCountry
        restMovieInsightsPerCountryMockMvc.perform(get("/api/_search/movie-insights-per-countries?query=id:" + movieInsightsPerCountry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieInsightsPerCountry.getId().intValue())));
    }
}
