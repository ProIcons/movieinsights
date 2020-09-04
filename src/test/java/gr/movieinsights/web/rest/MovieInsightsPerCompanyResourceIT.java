/*
package gr.movieinsights.web.rest;

import gr.movieinsights.MovieInsightsApp;
import gr.movieinsights.domain.MovieInsights;
import gr.movieinsights.domain.MovieInsightsPerCompany;
import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.repository.MovieInsightsPerCompanyRepository;
import gr.movieinsights.service.MovieInsightsPerCompanyService;
import gr.movieinsights.service.dto.movieinsights.company.MovieInsightsPerCompanyDTO;
import gr.movieinsights.service.mapper.movieinsights.company.MovieInsightsPerCompanyMapper;
import gr.movieinsights.web.rest.movieinsights.MovieInsightsPerCompanyResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

*/
/**
 * Integration tests for the {@link MovieInsightsPerCompanyResource} REST controller.
 *//*

@SpringBootTest(classes = MovieInsightsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MovieInsightsPerCompanyResourceIT {

    @Autowired
    private MovieInsightsPerCompanyRepository movieInsightsPerCompanyRepository;

    @Autowired
    private MovieInsightsPerCompanyMapper movieInsightsPerCompanyMapper;

    @Autowired
    private MovieInsightsPerCompanyService movieInsightsPerCompanyService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMovieInsightsPerCompanyMockMvc;

    private MovieInsightsPerCompany movieInsightsPerCompany;

    */
/**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*

    public static MovieInsightsPerCompany createEntity(EntityManager em) {
        MovieInsightsPerCompany movieInsightsPerCompany = new MovieInsightsPerCompany();
        // Add required entity
        MovieInsights movieInsights;
        if (TestUtil.findAll(em, MovieInsights.class).isEmpty()) {
            movieInsights = MovieInsightsResourceIT.createEntity(em);
            em.persist(movieInsights);
            em.flush();
        } else {
            movieInsights = TestUtil.findAll(em, MovieInsights.class).get(0);
        }
        movieInsightsPerCompany.setMovieInsights(movieInsights);
        // Add required entity
        ProductionCompany productionCompany;
        if (TestUtil.findAll(em, ProductionCompany.class).isEmpty()) {
            productionCompany = ProductionCompanyResourceIT.createEntity(em);
            em.persist(productionCompany);
            em.flush();
        } else {
            productionCompany = TestUtil.findAll(em, ProductionCompany.class).get(0);
        }
        movieInsightsPerCompany.setCompany(productionCompany);
        return movieInsightsPerCompany;
    }
    */
/**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*

    public static MovieInsightsPerCompany createUpdatedEntity(EntityManager em) {
        MovieInsightsPerCompany movieInsightsPerCompany = new MovieInsightsPerCompany();
        // Add required entity
        MovieInsights movieInsights;
        if (TestUtil.findAll(em, MovieInsights.class).isEmpty()) {
            movieInsights = MovieInsightsResourceIT.createUpdatedEntity(em);
            em.persist(movieInsights);
            em.flush();
        } else {
            movieInsights = TestUtil.findAll(em, MovieInsights.class).get(0);
        }
        movieInsightsPerCompany.setMovieInsights(movieInsights);
        // Add required entity
        ProductionCompany productionCompany;
        if (TestUtil.findAll(em, ProductionCompany.class).isEmpty()) {
            productionCompany = ProductionCompanyResourceIT.createUpdatedEntity(em);
            em.persist(productionCompany);
            em.flush();
        } else {
            productionCompany = TestUtil.findAll(em, ProductionCompany.class).get(0);
        }
        movieInsightsPerCompany.setCompany(productionCompany);
        return movieInsightsPerCompany;
    }

    @BeforeEach
    public void initTest() {
        movieInsightsPerCompany = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovieInsightsPerCompany() throws Exception {
        int databaseSizeBeforeCreate = movieInsightsPerCompanyRepository.findAll().size();
        // Create the MovieInsightsPerCompany
        MovieInsightsPerCompanyDTO movieInsightsPerCompanyDTO = movieInsightsPerCompanyMapper.toDto(movieInsightsPerCompany);
        restMovieInsightsPerCompanyMockMvc.perform(post("/api/movie-insights-per-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerCompanyDTO)))
            .andExpect(status().isCreated());

        // Validate the MovieInsightsPerCompany in the database
        List<MovieInsightsPerCompany> movieInsightsPerCompanyList = movieInsightsPerCompanyRepository.findAll();
        assertThat(movieInsightsPerCompanyList).hasSize(databaseSizeBeforeCreate + 1);
        MovieInsightsPerCompany testMovieInsightsPerCompany = movieInsightsPerCompanyList.get(movieInsightsPerCompanyList.size() - 1);
    }

    @Test
    @Transactional
    public void createMovieInsightsPerCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieInsightsPerCompanyRepository.findAll().size();

        // Create the MovieInsightsPerCompany with an existing ID
        movieInsightsPerCompany.setId(1L);
        MovieInsightsPerCompanyDTO movieInsightsPerCompanyDTO = movieInsightsPerCompanyMapper.toDto(movieInsightsPerCompany);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieInsightsPerCompanyMockMvc.perform(post("/api/movie-insights-per-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerCompanyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieInsightsPerCompany in the database
        List<MovieInsightsPerCompany> movieInsightsPerCompanyList = movieInsightsPerCompanyRepository.findAll();
        assertThat(movieInsightsPerCompanyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMovieInsightsPerCompanies() throws Exception {
        // Initialize the database
        movieInsightsPerCompanyRepository.saveAndFlush(movieInsightsPerCompany);

        // Get all the movieInsightsPerCompanyList
        restMovieInsightsPerCompanyMockMvc.perform(get("/api/movie-insights-per-companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieInsightsPerCompany.getId().intValue())));
    }

    @Test
    @Transactional
    public void getMovieInsightsPerCompany() throws Exception {
        // Initialize the database
        movieInsightsPerCompanyRepository.saveAndFlush(movieInsightsPerCompany);

        // Get the movieInsightsPerCompany
        restMovieInsightsPerCompanyMockMvc.perform(get("/api/movie-insights-per-companies/{id}", movieInsightsPerCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movieInsightsPerCompany.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingMovieInsightsPerCompany() throws Exception {
        // Get the movieInsightsPerCompany
        restMovieInsightsPerCompanyMockMvc.perform(get("/api/movie-insights-per-companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovieInsightsPerCompany() throws Exception {
        // Initialize the database
        movieInsightsPerCompanyRepository.saveAndFlush(movieInsightsPerCompany);

        int databaseSizeBeforeUpdate = movieInsightsPerCompanyRepository.findAll().size();

        // Update the movieInsightsPerCompany
        MovieInsightsPerCompany updatedMovieInsightsPerCompany = movieInsightsPerCompanyRepository.findById(movieInsightsPerCompany.getId()).get();
        // Disconnect from session so that the updates on updatedMovieInsightsPerCompany are not directly saved in db
        em.detach(updatedMovieInsightsPerCompany);
        MovieInsightsPerCompanyDTO movieInsightsPerCompanyDTO = movieInsightsPerCompanyMapper.toDto(updatedMovieInsightsPerCompany);

        restMovieInsightsPerCompanyMockMvc.perform(put("/api/movie-insights-per-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerCompanyDTO)))
            .andExpect(status().isOk());

        // Validate the MovieInsightsPerCompany in the database
        List<MovieInsightsPerCompany> movieInsightsPerCompanyList = movieInsightsPerCompanyRepository.findAll();
        assertThat(movieInsightsPerCompanyList).hasSize(databaseSizeBeforeUpdate);
        MovieInsightsPerCompany testMovieInsightsPerCompany = movieInsightsPerCompanyList.get(movieInsightsPerCompanyList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingMovieInsightsPerCompany() throws Exception {
        int databaseSizeBeforeUpdate = movieInsightsPerCompanyRepository.findAll().size();

        // Create the MovieInsightsPerCompany
        MovieInsightsPerCompanyDTO movieInsightsPerCompanyDTO = movieInsightsPerCompanyMapper.toDto(movieInsightsPerCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieInsightsPerCompanyMockMvc.perform(put("/api/movie-insights-per-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerCompanyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieInsightsPerCompany in the database
        List<MovieInsightsPerCompany> movieInsightsPerCompanyList = movieInsightsPerCompanyRepository.findAll();
        assertThat(movieInsightsPerCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMovieInsightsPerCompany() throws Exception {
        // Initialize the database
        movieInsightsPerCompanyRepository.saveAndFlush(movieInsightsPerCompany);

        int databaseSizeBeforeDelete = movieInsightsPerCompanyRepository.findAll().size();

        // Delete the movieInsightsPerCompany
        restMovieInsightsPerCompanyMockMvc.perform(delete("/api/movie-insights-per-companies/{id}", movieInsightsPerCompany.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MovieInsightsPerCompany> movieInsightsPerCompanyList = movieInsightsPerCompanyRepository.findAll();
        assertThat(movieInsightsPerCompanyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
*/
