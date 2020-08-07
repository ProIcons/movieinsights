package gr.movieinsights.web.rest;

import gr.movieinsights.MovieInsightsApp;
import gr.movieinsights.domain.Credit;
import gr.movieinsights.repository.CreditRepository;
import gr.movieinsights.repository.search.CreditSearchRepository;
import gr.movieinsights.service.CreditService;
import gr.movieinsights.service.dto.CreditDTO;
import gr.movieinsights.service.mapper.CreditMapper;

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
 * Integration tests for the {@link CreditResource} REST controller.
 */
@SpringBootTest(classes = MovieInsightsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CreditResourceIT {

    private static final String DEFAULT_CREDIT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_PERSON_TMDB_ID = 1L;
    private static final Long UPDATED_PERSON_TMDB_ID = 2L;

    private static final Long DEFAULT_MOVIE_TMDB_ID = 1L;
    private static final Long UPDATED_MOVIE_TMDB_ID = 2L;

    private static final CreditRole DEFAULT_ROLE = CreditRole.ACTOR;
    private static final CreditRole UPDATED_ROLE = CreditRole.PRODUCER;

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private CreditMapper creditMapper;

    @Autowired
    private CreditService creditService;

    /**
     * This repository is mocked in the gr.movieinsights.repository.search test package.
     *
     * @see gr.movieinsights.repository.search.CreditSearchRepositoryMockConfiguration
     */
    @Autowired
    private CreditSearchRepository mockCreditSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCreditMockMvc;

    private Credit credit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Credit createEntity(EntityManager em) {
        Credit credit = new Credit()
            .creditId(DEFAULT_CREDIT_ID)
            .personTmdbId(DEFAULT_PERSON_TMDB_ID)
            .movieTmdbId(DEFAULT_MOVIE_TMDB_ID)
            .role(DEFAULT_ROLE);
        return credit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Credit createUpdatedEntity(EntityManager em) {
        Credit credit = new Credit()
            .creditId(UPDATED_CREDIT_ID)
            .personTmdbId(UPDATED_PERSON_TMDB_ID)
            .movieTmdbId(UPDATED_MOVIE_TMDB_ID)
            .role(UPDATED_ROLE);
        return credit;
    }

    @BeforeEach
    public void initTest() {
        credit = createEntity(em);
    }

    @Test
    @Transactional
    public void createCredit() throws Exception {
        int databaseSizeBeforeCreate = creditRepository.findAll().size();
        // Create the Credit
        CreditDTO creditDTO = creditMapper.toDto(credit);
        restCreditMockMvc.perform(post("/api/credits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(creditDTO)))
            .andExpect(status().isCreated());

        // Validate the Credit in the database
        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeCreate + 1);
        Credit testCredit = creditList.get(creditList.size() - 1);
        assertThat(testCredit.getCreditId()).isEqualTo(DEFAULT_CREDIT_ID);
        assertThat(testCredit.getPersonTmdbId()).isEqualTo(DEFAULT_PERSON_TMDB_ID);
        assertThat(testCredit.getMovieTmdbId()).isEqualTo(DEFAULT_MOVIE_TMDB_ID);
        assertThat(testCredit.getRole()).isEqualTo(DEFAULT_ROLE);

        // Validate the Credit in Elasticsearch
        verify(mockCreditSearchRepository, times(1)).save(testCredit);
    }

    @Test
    @Transactional
    public void createCreditWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = creditRepository.findAll().size();

        // Create the Credit with an existing ID
        credit.setId(1L);
        CreditDTO creditDTO = creditMapper.toDto(credit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditMockMvc.perform(post("/api/credits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(creditDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Credit in the database
        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeCreate);

        // Validate the Credit in Elasticsearch
        verify(mockCreditSearchRepository, times(0)).save(credit);
    }


    @Test
    @Transactional
    public void checkCreditIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditRepository.findAll().size();
        // set the field null
        credit.setCreditId(null);

        // Create the Credit, which fails.
        CreditDTO creditDTO = creditMapper.toDto(credit);


        restCreditMockMvc.perform(post("/api/credits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(creditDTO)))
            .andExpect(status().isBadRequest());

        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPersonTmdbIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditRepository.findAll().size();
        // set the field null
        credit.setPersonTmdbId(null);

        // Create the Credit, which fails.
        CreditDTO creditDTO = creditMapper.toDto(credit);


        restCreditMockMvc.perform(post("/api/credits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(creditDTO)))
            .andExpect(status().isBadRequest());

        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMovieTmdbIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditRepository.findAll().size();
        // set the field null
        credit.setMovieTmdbId(null);

        // Create the Credit, which fails.
        CreditDTO creditDTO = creditMapper.toDto(credit);


        restCreditMockMvc.perform(post("/api/credits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(creditDTO)))
            .andExpect(status().isBadRequest());

        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditRepository.findAll().size();
        // set the field null
        credit.setRole(null);

        // Create the Credit, which fails.
        CreditDTO creditDTO = creditMapper.toDto(credit);


        restCreditMockMvc.perform(post("/api/credits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(creditDTO)))
            .andExpect(status().isBadRequest());

        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCredits() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList
        restCreditMockMvc.perform(get("/api/credits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(credit.getId().intValue())))
            .andExpect(jsonPath("$.[*].creditId").value(hasItem(DEFAULT_CREDIT_ID)))
            .andExpect(jsonPath("$.[*].personTmdbId").value(hasItem(DEFAULT_PERSON_TMDB_ID.intValue())))
            .andExpect(jsonPath("$.[*].movieTmdbId").value(hasItem(DEFAULT_MOVIE_TMDB_ID.intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));
    }
    
    @Test
    @Transactional
    public void getCredit() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get the credit
        restCreditMockMvc.perform(get("/api/credits/{id}", credit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(credit.getId().intValue()))
            .andExpect(jsonPath("$.creditId").value(DEFAULT_CREDIT_ID))
            .andExpect(jsonPath("$.personTmdbId").value(DEFAULT_PERSON_TMDB_ID.intValue()))
            .andExpect(jsonPath("$.movieTmdbId").value(DEFAULT_MOVIE_TMDB_ID.intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCredit() throws Exception {
        // Get the credit
        restCreditMockMvc.perform(get("/api/credits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCredit() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        int databaseSizeBeforeUpdate = creditRepository.findAll().size();

        // Update the credit
        Credit updatedCredit = creditRepository.findById(credit.getId()).get();
        // Disconnect from session so that the updates on updatedCredit are not directly saved in db
        em.detach(updatedCredit);
        updatedCredit
            .creditId(UPDATED_CREDIT_ID)
            .personTmdbId(UPDATED_PERSON_TMDB_ID)
            .movieTmdbId(UPDATED_MOVIE_TMDB_ID)
            .role(UPDATED_ROLE);
        CreditDTO creditDTO = creditMapper.toDto(updatedCredit);

        restCreditMockMvc.perform(put("/api/credits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(creditDTO)))
            .andExpect(status().isOk());

        // Validate the Credit in the database
        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeUpdate);
        Credit testCredit = creditList.get(creditList.size() - 1);
        assertThat(testCredit.getCreditId()).isEqualTo(UPDATED_CREDIT_ID);
        assertThat(testCredit.getPersonTmdbId()).isEqualTo(UPDATED_PERSON_TMDB_ID);
        assertThat(testCredit.getMovieTmdbId()).isEqualTo(UPDATED_MOVIE_TMDB_ID);
        assertThat(testCredit.getRole()).isEqualTo(UPDATED_ROLE);

        // Validate the Credit in Elasticsearch
        verify(mockCreditSearchRepository, times(1)).save(testCredit);
    }

    @Test
    @Transactional
    public void updateNonExistingCredit() throws Exception {
        int databaseSizeBeforeUpdate = creditRepository.findAll().size();

        // Create the Credit
        CreditDTO creditDTO = creditMapper.toDto(credit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditMockMvc.perform(put("/api/credits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(creditDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Credit in the database
        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Credit in Elasticsearch
        verify(mockCreditSearchRepository, times(0)).save(credit);
    }

    @Test
    @Transactional
    public void deleteCredit() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        int databaseSizeBeforeDelete = creditRepository.findAll().size();

        // Delete the credit
        restCreditMockMvc.perform(delete("/api/credits/{id}", credit.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Credit in Elasticsearch
        verify(mockCreditSearchRepository, times(1)).deleteById(credit.getId());
    }

    @Test
    @Transactional
    public void searchCredit() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        creditRepository.saveAndFlush(credit);
        when(mockCreditSearchRepository.search(queryStringQuery("id:" + credit.getId())))
            .thenReturn(Collections.singletonList(credit));

        // Search the credit
        restCreditMockMvc.perform(get("/api/_search/credits?query=id:" + credit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(credit.getId().intValue())))
            .andExpect(jsonPath("$.[*].creditId").value(hasItem(DEFAULT_CREDIT_ID)))
            .andExpect(jsonPath("$.[*].personTmdbId").value(hasItem(DEFAULT_PERSON_TMDB_ID.intValue())))
            .andExpect(jsonPath("$.[*].movieTmdbId").value(hasItem(DEFAULT_MOVIE_TMDB_ID.intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));
    }
}
