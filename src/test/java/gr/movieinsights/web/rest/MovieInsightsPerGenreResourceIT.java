package gr.movieinsights.web.rest;

import gr.movieinsights.MovieInsightsApp;
import gr.movieinsights.domain.MovieInsightsPerGenre;
import gr.movieinsights.domain.MovieInsights;
import gr.movieinsights.domain.Genre;
import gr.movieinsights.repository.MovieInsightsPerGenreRepository;
import gr.movieinsights.repository.search.MovieInsightsPerGenreSearchRepository;
import gr.movieinsights.service.MovieInsightsPerGenreService;
import gr.movieinsights.service.dto.MovieInsightsPerGenreDTO;
import gr.movieinsights.service.mapper.MovieInsightsPerGenreMapper;

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
 * Integration tests for the {@link MovieInsightsPerGenreResource} REST controller.
 */
@SpringBootTest(classes = MovieInsightsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MovieInsightsPerGenreResourceIT {

    @Autowired
    private MovieInsightsPerGenreRepository movieInsightsPerGenreRepository;

    @Autowired
    private MovieInsightsPerGenreMapper movieInsightsPerGenreMapper;

    @Autowired
    private MovieInsightsPerGenreService movieInsightsPerGenreService;

    /**
     * This repository is mocked in the gr.movieinsights.repository.search test package.
     *
     * @see gr.movieinsights.repository.search.MovieInsightsPerGenreSearchRepositoryMockConfiguration
     */
    @Autowired
    private MovieInsightsPerGenreSearchRepository mockMovieInsightsPerGenreSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMovieInsightsPerGenreMockMvc;

    private MovieInsightsPerGenre movieInsightsPerGenre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieInsightsPerGenre createEntity(EntityManager em) {
        MovieInsightsPerGenre movieInsightsPerGenre = new MovieInsightsPerGenre();
        // Add required entity
        MovieInsights movieInsights;
        if (TestUtil.findAll(em, MovieInsights.class).isEmpty()) {
            movieInsights = MovieInsightsResourceIT.createEntity(em);
            em.persist(movieInsights);
            em.flush();
        } else {
            movieInsights = TestUtil.findAll(em, MovieInsights.class).get(0);
        }
        movieInsightsPerGenre.setMovieInsights(movieInsights);
        // Add required entity
        Genre genre;
        if (TestUtil.findAll(em, Genre.class).isEmpty()) {
            genre = GenreResourceIT.createEntity(em);
            em.persist(genre);
            em.flush();
        } else {
            genre = TestUtil.findAll(em, Genre.class).get(0);
        }
        movieInsightsPerGenre.setGenre(genre);
        return movieInsightsPerGenre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieInsightsPerGenre createUpdatedEntity(EntityManager em) {
        MovieInsightsPerGenre movieInsightsPerGenre = new MovieInsightsPerGenre();
        // Add required entity
        MovieInsights movieInsights;
        if (TestUtil.findAll(em, MovieInsights.class).isEmpty()) {
            movieInsights = MovieInsightsResourceIT.createUpdatedEntity(em);
            em.persist(movieInsights);
            em.flush();
        } else {
            movieInsights = TestUtil.findAll(em, MovieInsights.class).get(0);
        }
        movieInsightsPerGenre.setMovieInsights(movieInsights);
        // Add required entity
        Genre genre;
        if (TestUtil.findAll(em, Genre.class).isEmpty()) {
            genre = GenreResourceIT.createUpdatedEntity(em);
            em.persist(genre);
            em.flush();
        } else {
            genre = TestUtil.findAll(em, Genre.class).get(0);
        }
        movieInsightsPerGenre.setGenre(genre);
        return movieInsightsPerGenre;
    }

    @BeforeEach
    public void initTest() {
        movieInsightsPerGenre = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovieInsightsPerGenre() throws Exception {
        int databaseSizeBeforeCreate = movieInsightsPerGenreRepository.findAll().size();
        // Create the MovieInsightsPerGenre
        MovieInsightsPerGenreDTO movieInsightsPerGenreDTO = movieInsightsPerGenreMapper.toDto(movieInsightsPerGenre);
        restMovieInsightsPerGenreMockMvc.perform(post("/api/movie-insights-per-genres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerGenreDTO)))
            .andExpect(status().isCreated());

        // Validate the MovieInsightsPerGenre in the database
        List<MovieInsightsPerGenre> movieInsightsPerGenreList = movieInsightsPerGenreRepository.findAll();
        assertThat(movieInsightsPerGenreList).hasSize(databaseSizeBeforeCreate + 1);
        MovieInsightsPerGenre testMovieInsightsPerGenre = movieInsightsPerGenreList.get(movieInsightsPerGenreList.size() - 1);

        // Validate the MovieInsightsPerGenre in Elasticsearch
        verify(mockMovieInsightsPerGenreSearchRepository, times(1)).save(testMovieInsightsPerGenre);
    }

    @Test
    @Transactional
    public void createMovieInsightsPerGenreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieInsightsPerGenreRepository.findAll().size();

        // Create the MovieInsightsPerGenre with an existing ID
        movieInsightsPerGenre.setId(1L);
        MovieInsightsPerGenreDTO movieInsightsPerGenreDTO = movieInsightsPerGenreMapper.toDto(movieInsightsPerGenre);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieInsightsPerGenreMockMvc.perform(post("/api/movie-insights-per-genres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerGenreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieInsightsPerGenre in the database
        List<MovieInsightsPerGenre> movieInsightsPerGenreList = movieInsightsPerGenreRepository.findAll();
        assertThat(movieInsightsPerGenreList).hasSize(databaseSizeBeforeCreate);

        // Validate the MovieInsightsPerGenre in Elasticsearch
        verify(mockMovieInsightsPerGenreSearchRepository, times(0)).save(movieInsightsPerGenre);
    }


    @Test
    @Transactional
    public void getAllMovieInsightsPerGenres() throws Exception {
        // Initialize the database
        movieInsightsPerGenreRepository.saveAndFlush(movieInsightsPerGenre);

        // Get all the movieInsightsPerGenreList
        restMovieInsightsPerGenreMockMvc.perform(get("/api/movie-insights-per-genres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieInsightsPerGenre.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getMovieInsightsPerGenre() throws Exception {
        // Initialize the database
        movieInsightsPerGenreRepository.saveAndFlush(movieInsightsPerGenre);

        // Get the movieInsightsPerGenre
        restMovieInsightsPerGenreMockMvc.perform(get("/api/movie-insights-per-genres/{id}", movieInsightsPerGenre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movieInsightsPerGenre.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingMovieInsightsPerGenre() throws Exception {
        // Get the movieInsightsPerGenre
        restMovieInsightsPerGenreMockMvc.perform(get("/api/movie-insights-per-genres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovieInsightsPerGenre() throws Exception {
        // Initialize the database
        movieInsightsPerGenreRepository.saveAndFlush(movieInsightsPerGenre);

        int databaseSizeBeforeUpdate = movieInsightsPerGenreRepository.findAll().size();

        // Update the movieInsightsPerGenre
        MovieInsightsPerGenre updatedMovieInsightsPerGenre = movieInsightsPerGenreRepository.findById(movieInsightsPerGenre.getId()).get();
        // Disconnect from session so that the updates on updatedMovieInsightsPerGenre are not directly saved in db
        em.detach(updatedMovieInsightsPerGenre);
        MovieInsightsPerGenreDTO movieInsightsPerGenreDTO = movieInsightsPerGenreMapper.toDto(updatedMovieInsightsPerGenre);

        restMovieInsightsPerGenreMockMvc.perform(put("/api/movie-insights-per-genres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerGenreDTO)))
            .andExpect(status().isOk());

        // Validate the MovieInsightsPerGenre in the database
        List<MovieInsightsPerGenre> movieInsightsPerGenreList = movieInsightsPerGenreRepository.findAll();
        assertThat(movieInsightsPerGenreList).hasSize(databaseSizeBeforeUpdate);
        MovieInsightsPerGenre testMovieInsightsPerGenre = movieInsightsPerGenreList.get(movieInsightsPerGenreList.size() - 1);

        // Validate the MovieInsightsPerGenre in Elasticsearch
        verify(mockMovieInsightsPerGenreSearchRepository, times(1)).save(testMovieInsightsPerGenre);
    }

    @Test
    @Transactional
    public void updateNonExistingMovieInsightsPerGenre() throws Exception {
        int databaseSizeBeforeUpdate = movieInsightsPerGenreRepository.findAll().size();

        // Create the MovieInsightsPerGenre
        MovieInsightsPerGenreDTO movieInsightsPerGenreDTO = movieInsightsPerGenreMapper.toDto(movieInsightsPerGenre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieInsightsPerGenreMockMvc.perform(put("/api/movie-insights-per-genres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerGenreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieInsightsPerGenre in the database
        List<MovieInsightsPerGenre> movieInsightsPerGenreList = movieInsightsPerGenreRepository.findAll();
        assertThat(movieInsightsPerGenreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MovieInsightsPerGenre in Elasticsearch
        verify(mockMovieInsightsPerGenreSearchRepository, times(0)).save(movieInsightsPerGenre);
    }

    @Test
    @Transactional
    public void deleteMovieInsightsPerGenre() throws Exception {
        // Initialize the database
        movieInsightsPerGenreRepository.saveAndFlush(movieInsightsPerGenre);

        int databaseSizeBeforeDelete = movieInsightsPerGenreRepository.findAll().size();

        // Delete the movieInsightsPerGenre
        restMovieInsightsPerGenreMockMvc.perform(delete("/api/movie-insights-per-genres/{id}", movieInsightsPerGenre.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MovieInsightsPerGenre> movieInsightsPerGenreList = movieInsightsPerGenreRepository.findAll();
        assertThat(movieInsightsPerGenreList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MovieInsightsPerGenre in Elasticsearch
        verify(mockMovieInsightsPerGenreSearchRepository, times(1)).deleteById(movieInsightsPerGenre.getId());
    }

    @Test
    @Transactional
    public void searchMovieInsightsPerGenre() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        movieInsightsPerGenreRepository.saveAndFlush(movieInsightsPerGenre);
        when(mockMovieInsightsPerGenreSearchRepository.search(queryStringQuery("id:" + movieInsightsPerGenre.getId())))
            .thenReturn(Collections.singletonList(movieInsightsPerGenre));

        // Search the movieInsightsPerGenre
        restMovieInsightsPerGenreMockMvc.perform(get("/api/_search/movie-insights-per-genres?query=id:" + movieInsightsPerGenre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieInsightsPerGenre.getId().intValue())));
    }
}
