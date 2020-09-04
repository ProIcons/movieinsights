/*
package gr.movieinsights.web.rest;

import gr.movieinsights.MovieInsightsApp;
import gr.movieinsights.domain.Person;
import gr.movieinsights.repository.PersonRepository;
import gr.movieinsights.repository.search.PersonSearchRepository;
import gr.movieinsights.service.PersonService;
import gr.movieinsights.service.dto.person.PersonDTO;
import gr.movieinsights.service.mapper.person.PersonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

*/
/**
 * Integration tests for the {@link PersonResource} REST controller.
 *//*

@SpringBootTest(classes = MovieInsightsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PersonResourceIT {

    private static final Long DEFAULT_TMDB_ID = 1L;
    private static final Long UPDATED_TMDB_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMDB_ID = "AAAAAAAAAA";
    private static final String UPDATED_IMDB_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_POPULARITY = 1D;
    private static final Double UPDATED_POPULARITY = 2D;

    private static final String DEFAULT_BIOGRAPHY = "AAAAAAAAAA";
    private static final String UPDATED_BIOGRAPHY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DAY = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PROFILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_PATH = "BBBBBBBBBB";

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PersonService personService;

    */
/**
     * This repository is mocked in the gr.movieinsights.repository.search test package.
     *
     * @see gr.movieinsights.repository.search.PersonSearchRepositoryMockConfiguration
     *//*

    @Autowired
    private PersonSearchRepository mockPersonSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonMockMvc;

    private Person person;

    */
/**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*

    public static Person createEntity(EntityManager em) {
        Person person = new Person()
            .tmdbId(DEFAULT_TMDB_ID)
            .name(DEFAULT_NAME)
            .imdbId(DEFAULT_IMDB_ID)
            .popularity(DEFAULT_POPULARITY)
            .biography(DEFAULT_BIOGRAPHY)
            .birthDay(DEFAULT_BIRTH_DAY)
            .profilePath(DEFAULT_PROFILE_PATH);
        return person;
    }
    */
/**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*

    public static Person createUpdatedEntity(EntityManager em) {
        Person person = new Person()
            .tmdbId(UPDATED_TMDB_ID)
            .name(UPDATED_NAME)
            .imdbId(UPDATED_IMDB_ID)
            .popularity(UPDATED_POPULARITY)
            .biography(UPDATED_BIOGRAPHY)
            .birthDay(UPDATED_BIRTH_DAY)
            .profilePath(UPDATED_PROFILE_PATH);
        return person;
    }

    @BeforeEach
    public void initTest() {
        person = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();
        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);
        restPersonMockMvc.perform(post("/api/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getTmdbId()).isEqualTo(DEFAULT_TMDB_ID);
        assertThat(testPerson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPerson.getImdbId()).isEqualTo(DEFAULT_IMDB_ID);
        assertThat(testPerson.getPopularity()).isEqualTo(DEFAULT_POPULARITY);
        assertThat(testPerson.getBiography()).isEqualTo(DEFAULT_BIOGRAPHY);
        assertThat(testPerson.getBirthDay()).isEqualTo(DEFAULT_BIRTH_DAY);
        assertThat(testPerson.getProfilePath()).isEqualTo(DEFAULT_PROFILE_PATH);

        // Validate the Person in Elasticsearch
        verify(mockPersonSearchRepository, times(1)).save(testPerson);
    }

    @Test
    @Transactional
    public void createPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person with an existing ID
        person.setId(1L);
        PersonDTO personDTO = personMapper.toDto(person);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMockMvc.perform(post("/api/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate);

        // Validate the Person in Elasticsearch
        verify(mockPersonSearchRepository, times(0)).save(person);
    }


    @Test
    @Transactional
    public void checkTmdbIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setTmdbId(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);


        restPersonMockMvc.perform(post("/api/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setName(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);


        restPersonMockMvc.perform(post("/api/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList
        restPersonMockMvc.perform(get("/api/people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].tmdbId").value(hasItem(DEFAULT_TMDB_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].imdbId").value(hasItem(DEFAULT_IMDB_ID)))
            .andExpect(jsonPath("$.[*].popularity").value(hasItem(DEFAULT_POPULARITY.doubleValue())))
            .andExpect(jsonPath("$.[*].biography").value(hasItem(DEFAULT_BIOGRAPHY.toString())))
            .andExpect(jsonPath("$.[*].birthDay").value(hasItem(DEFAULT_BIRTH_DAY.toString())))
            .andExpect(jsonPath("$.[*].profilePath").value(hasItem(DEFAULT_PROFILE_PATH)));
    }

    @Test
    @Transactional
    public void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.tmdbId").value(DEFAULT_TMDB_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.imdbId").value(DEFAULT_IMDB_ID))
            .andExpect(jsonPath("$.popularity").value(DEFAULT_POPULARITY.doubleValue()))
            .andExpect(jsonPath("$.biography").value(DEFAULT_BIOGRAPHY.toString()))
            .andExpect(jsonPath("$.birthDay").value(DEFAULT_BIRTH_DAY.toString()))
            .andExpect(jsonPath("$.profilePath").value(DEFAULT_PROFILE_PATH));
    }
    @Test
    @Transactional
    public void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = personRepository.findById(person.getId()).get();
        // Disconnect from session so that the updates on updatedPerson are not directly saved in db
        em.detach(updatedPerson);
        updatedPerson
            .tmdbId(UPDATED_TMDB_ID)
            .name(UPDATED_NAME)
            .imdbId(UPDATED_IMDB_ID)
            .popularity(UPDATED_POPULARITY)
            .biography(UPDATED_BIOGRAPHY)
            .birthDay(UPDATED_BIRTH_DAY)
            .profilePath(UPDATED_PROFILE_PATH);
        PersonDTO personDTO = personMapper.toDto(updatedPerson);

        restPersonMockMvc.perform(put("/api/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getTmdbId()).isEqualTo(UPDATED_TMDB_ID);
        assertThat(testPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPerson.getImdbId()).isEqualTo(UPDATED_IMDB_ID);
        assertThat(testPerson.getPopularity()).isEqualTo(UPDATED_POPULARITY);
        assertThat(testPerson.getBiography()).isEqualTo(UPDATED_BIOGRAPHY);
        assertThat(testPerson.getBirthDay()).isEqualTo(UPDATED_BIRTH_DAY);
        assertThat(testPerson.getProfilePath()).isEqualTo(UPDATED_PROFILE_PATH);

        // Validate the Person in Elasticsearch
        verify(mockPersonSearchRepository, times(1)).save(testPerson);
    }

    @Test
    @Transactional
    public void updateNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc.perform(put("/api/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Person in Elasticsearch
        verify(mockPersonSearchRepository, times(0)).save(person);
    }

    @Test
    @Transactional
    public void deletePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Delete the person
        restPersonMockMvc.perform(delete("/api/people/{id}", person.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Person in Elasticsearch
        verify(mockPersonSearchRepository, times(1)).deleteById(person.getId());
    }

    @Test
    @Transactional
    public void searchPerson() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        personRepository.saveAndFlush(person);
        when(mockPersonSearchRepository.search(queryStringQuery("id:" + person.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(person), PageRequest.of(0, 1), 1));

        // Search the person
        restPersonMockMvc.perform(get("/api/_search/people?query=id:" + person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].tmdbId").value(hasItem(DEFAULT_TMDB_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].imdbId").value(hasItem(DEFAULT_IMDB_ID)))
            .andExpect(jsonPath("$.[*].popularity").value(hasItem(DEFAULT_POPULARITY.doubleValue())))
            .andExpect(jsonPath("$.[*].biography").value(hasItem(DEFAULT_BIOGRAPHY.toString())))
            .andExpect(jsonPath("$.[*].birthDay").value(hasItem(DEFAULT_BIRTH_DAY.toString())))
            .andExpect(jsonPath("$.[*].profilePath").value(hasItem(DEFAULT_PROFILE_PATH)));
    }
}
*/
