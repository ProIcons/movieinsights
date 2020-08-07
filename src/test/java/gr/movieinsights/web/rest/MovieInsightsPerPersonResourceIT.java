package gr.movieinsights.web.rest;

import gr.movieinsights.MovieInsightsApp;
import gr.movieinsights.domain.MovieInsightsPerPerson;
import gr.movieinsights.domain.MovieInsights;
import gr.movieinsights.domain.Person;
import gr.movieinsights.repository.MovieInsightsPerPersonRepository;
import gr.movieinsights.repository.search.MovieInsightsPerPersonSearchRepository;
import gr.movieinsights.service.MovieInsightsPerPersonService;
import gr.movieinsights.service.dto.MovieInsightsPerPersonDTO;
import gr.movieinsights.service.mapper.MovieInsightsPerPersonMapper;

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

import gr.movieinsights.domain.enumeration.CreditRole;
/**
 * Integration tests for the {@link MovieInsightsPerPersonResource} REST controller.
 */
@SpringBootTest(classes = MovieInsightsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MovieInsightsPerPersonResourceIT {

    private static final CreditRole DEFAULT_AS = CreditRole.ACTOR;
    private static final CreditRole UPDATED_AS = CreditRole.PRODUCER;

    @Autowired
    private MovieInsightsPerPersonRepository movieInsightsPerPersonRepository;

    @Autowired
    private MovieInsightsPerPersonMapper movieInsightsPerPersonMapper;

    @Autowired
    private MovieInsightsPerPersonService movieInsightsPerPersonService;

    /**
     * This repository is mocked in the gr.movieinsights.repository.search test package.
     *
     * @see gr.movieinsights.repository.search.MovieInsightsPerPersonSearchRepositoryMockConfiguration
     */
    @Autowired
    private MovieInsightsPerPersonSearchRepository mockMovieInsightsPerPersonSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMovieInsightsPerPersonMockMvc;

    private MovieInsightsPerPerson movieInsightsPerPerson;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieInsightsPerPerson createEntity(EntityManager em) {
        MovieInsightsPerPerson movieInsightsPerPerson = new MovieInsightsPerPerson()
            .as(DEFAULT_AS);
        // Add required entity
        MovieInsights movieInsights;
        if (TestUtil.findAll(em, MovieInsights.class).isEmpty()) {
            movieInsights = MovieInsightsResourceIT.createEntity(em);
            em.persist(movieInsights);
            em.flush();
        } else {
            movieInsights = TestUtil.findAll(em, MovieInsights.class).get(0);
        }
        movieInsightsPerPerson.setMovieInsights(movieInsights);
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        movieInsightsPerPerson.setPerson(person);
        return movieInsightsPerPerson;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieInsightsPerPerson createUpdatedEntity(EntityManager em) {
        MovieInsightsPerPerson movieInsightsPerPerson = new MovieInsightsPerPerson()
            .as(UPDATED_AS);
        // Add required entity
        MovieInsights movieInsights;
        if (TestUtil.findAll(em, MovieInsights.class).isEmpty()) {
            movieInsights = MovieInsightsResourceIT.createUpdatedEntity(em);
            em.persist(movieInsights);
            em.flush();
        } else {
            movieInsights = TestUtil.findAll(em, MovieInsights.class).get(0);
        }
        movieInsightsPerPerson.setMovieInsights(movieInsights);
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createUpdatedEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        movieInsightsPerPerson.setPerson(person);
        return movieInsightsPerPerson;
    }

    @BeforeEach
    public void initTest() {
        movieInsightsPerPerson = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovieInsightsPerPerson() throws Exception {
        int databaseSizeBeforeCreate = movieInsightsPerPersonRepository.findAll().size();
        // Create the MovieInsightsPerPerson
        MovieInsightsPerPersonDTO movieInsightsPerPersonDTO = movieInsightsPerPersonMapper.toDto(movieInsightsPerPerson);
        restMovieInsightsPerPersonMockMvc.perform(post("/api/movie-insights-per-people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerPersonDTO)))
            .andExpect(status().isCreated());

        // Validate the MovieInsightsPerPerson in the database
        List<MovieInsightsPerPerson> movieInsightsPerPersonList = movieInsightsPerPersonRepository.findAll();
        assertThat(movieInsightsPerPersonList).hasSize(databaseSizeBeforeCreate + 1);
        MovieInsightsPerPerson testMovieInsightsPerPerson = movieInsightsPerPersonList.get(movieInsightsPerPersonList.size() - 1);
        assertThat(testMovieInsightsPerPerson.getAs()).isEqualTo(DEFAULT_AS);

        // Validate the MovieInsightsPerPerson in Elasticsearch
        verify(mockMovieInsightsPerPersonSearchRepository, times(1)).save(testMovieInsightsPerPerson);
    }

    @Test
    @Transactional
    public void createMovieInsightsPerPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieInsightsPerPersonRepository.findAll().size();

        // Create the MovieInsightsPerPerson with an existing ID
        movieInsightsPerPerson.setId(1L);
        MovieInsightsPerPersonDTO movieInsightsPerPersonDTO = movieInsightsPerPersonMapper.toDto(movieInsightsPerPerson);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieInsightsPerPersonMockMvc.perform(post("/api/movie-insights-per-people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieInsightsPerPerson in the database
        List<MovieInsightsPerPerson> movieInsightsPerPersonList = movieInsightsPerPersonRepository.findAll();
        assertThat(movieInsightsPerPersonList).hasSize(databaseSizeBeforeCreate);

        // Validate the MovieInsightsPerPerson in Elasticsearch
        verify(mockMovieInsightsPerPersonSearchRepository, times(0)).save(movieInsightsPerPerson);
    }


    @Test
    @Transactional
    public void checkAsIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieInsightsPerPersonRepository.findAll().size();
        // set the field null
        movieInsightsPerPerson.setAs(null);

        // Create the MovieInsightsPerPerson, which fails.
        MovieInsightsPerPersonDTO movieInsightsPerPersonDTO = movieInsightsPerPersonMapper.toDto(movieInsightsPerPerson);


        restMovieInsightsPerPersonMockMvc.perform(post("/api/movie-insights-per-people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerPersonDTO)))
            .andExpect(status().isBadRequest());

        List<MovieInsightsPerPerson> movieInsightsPerPersonList = movieInsightsPerPersonRepository.findAll();
        assertThat(movieInsightsPerPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMovieInsightsPerPeople() throws Exception {
        // Initialize the database
        movieInsightsPerPersonRepository.saveAndFlush(movieInsightsPerPerson);

        // Get all the movieInsightsPerPersonList
        restMovieInsightsPerPersonMockMvc.perform(get("/api/movie-insights-per-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieInsightsPerPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].as").value(hasItem(DEFAULT_AS.toString())));
    }
    
    @Test
    @Transactional
    public void getMovieInsightsPerPerson() throws Exception {
        // Initialize the database
        movieInsightsPerPersonRepository.saveAndFlush(movieInsightsPerPerson);

        // Get the movieInsightsPerPerson
        restMovieInsightsPerPersonMockMvc.perform(get("/api/movie-insights-per-people/{id}", movieInsightsPerPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movieInsightsPerPerson.getId().intValue()))
            .andExpect(jsonPath("$.as").value(DEFAULT_AS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingMovieInsightsPerPerson() throws Exception {
        // Get the movieInsightsPerPerson
        restMovieInsightsPerPersonMockMvc.perform(get("/api/movie-insights-per-people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovieInsightsPerPerson() throws Exception {
        // Initialize the database
        movieInsightsPerPersonRepository.saveAndFlush(movieInsightsPerPerson);

        int databaseSizeBeforeUpdate = movieInsightsPerPersonRepository.findAll().size();

        // Update the movieInsightsPerPerson
        MovieInsightsPerPerson updatedMovieInsightsPerPerson = movieInsightsPerPersonRepository.findById(movieInsightsPerPerson.getId()).get();
        // Disconnect from session so that the updates on updatedMovieInsightsPerPerson are not directly saved in db
        em.detach(updatedMovieInsightsPerPerson);
        updatedMovieInsightsPerPerson
            .as(UPDATED_AS);
        MovieInsightsPerPersonDTO movieInsightsPerPersonDTO = movieInsightsPerPersonMapper.toDto(updatedMovieInsightsPerPerson);

        restMovieInsightsPerPersonMockMvc.perform(put("/api/movie-insights-per-people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerPersonDTO)))
            .andExpect(status().isOk());

        // Validate the MovieInsightsPerPerson in the database
        List<MovieInsightsPerPerson> movieInsightsPerPersonList = movieInsightsPerPersonRepository.findAll();
        assertThat(movieInsightsPerPersonList).hasSize(databaseSizeBeforeUpdate);
        MovieInsightsPerPerson testMovieInsightsPerPerson = movieInsightsPerPersonList.get(movieInsightsPerPersonList.size() - 1);
        assertThat(testMovieInsightsPerPerson.getAs()).isEqualTo(UPDATED_AS);

        // Validate the MovieInsightsPerPerson in Elasticsearch
        verify(mockMovieInsightsPerPersonSearchRepository, times(1)).save(testMovieInsightsPerPerson);
    }

    @Test
    @Transactional
    public void updateNonExistingMovieInsightsPerPerson() throws Exception {
        int databaseSizeBeforeUpdate = movieInsightsPerPersonRepository.findAll().size();

        // Create the MovieInsightsPerPerson
        MovieInsightsPerPersonDTO movieInsightsPerPersonDTO = movieInsightsPerPersonMapper.toDto(movieInsightsPerPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieInsightsPerPersonMockMvc.perform(put("/api/movie-insights-per-people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieInsightsPerPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieInsightsPerPerson in the database
        List<MovieInsightsPerPerson> movieInsightsPerPersonList = movieInsightsPerPersonRepository.findAll();
        assertThat(movieInsightsPerPersonList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MovieInsightsPerPerson in Elasticsearch
        verify(mockMovieInsightsPerPersonSearchRepository, times(0)).save(movieInsightsPerPerson);
    }

    @Test
    @Transactional
    public void deleteMovieInsightsPerPerson() throws Exception {
        // Initialize the database
        movieInsightsPerPersonRepository.saveAndFlush(movieInsightsPerPerson);

        int databaseSizeBeforeDelete = movieInsightsPerPersonRepository.findAll().size();

        // Delete the movieInsightsPerPerson
        restMovieInsightsPerPersonMockMvc.perform(delete("/api/movie-insights-per-people/{id}", movieInsightsPerPerson.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MovieInsightsPerPerson> movieInsightsPerPersonList = movieInsightsPerPersonRepository.findAll();
        assertThat(movieInsightsPerPersonList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MovieInsightsPerPerson in Elasticsearch
        verify(mockMovieInsightsPerPersonSearchRepository, times(1)).deleteById(movieInsightsPerPerson.getId());
    }

    @Test
    @Transactional
    public void searchMovieInsightsPerPerson() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        movieInsightsPerPersonRepository.saveAndFlush(movieInsightsPerPerson);
        when(mockMovieInsightsPerPersonSearchRepository.search(queryStringQuery("id:" + movieInsightsPerPerson.getId())))
            .thenReturn(Collections.singletonList(movieInsightsPerPerson));

        // Search the movieInsightsPerPerson
        restMovieInsightsPerPersonMockMvc.perform(get("/api/_search/movie-insights-per-people?query=id:" + movieInsightsPerPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieInsightsPerPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].as").value(hasItem(DEFAULT_AS.toString())));
    }
}
