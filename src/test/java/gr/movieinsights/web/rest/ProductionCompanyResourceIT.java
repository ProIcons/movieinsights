package gr.movieinsights.web.rest;

import gr.movieinsights.MovieInsightsApp;
import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.repository.ProductionCompanyRepository;
import gr.movieinsights.repository.search.ProductionCompanySearchRepository;
import gr.movieinsights.service.ProductionCompanyService;
import gr.movieinsights.service.dto.ProductionCompanyDTO;
import gr.movieinsights.service.mapper.ProductionCompanyMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductionCompanyResource} REST controller.
 */
@SpringBootTest(classes = MovieInsightsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductionCompanyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_TMDB_ID = 1L;
    private static final Long UPDATED_TMDB_ID = 2L;

    private static final String DEFAULT_LOGO_PATH = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGIN_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private ProductionCompanyRepository productionCompanyRepository;

    @Autowired
    private ProductionCompanyMapper productionCompanyMapper;

    @Autowired
    private ProductionCompanyService productionCompanyService;

    /**
     * This repository is mocked in the gr.movieinsights.repository.search test package.
     *
     * @see gr.movieinsights.repository.search.ProductionCompanySearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductionCompanySearchRepository mockProductionCompanySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductionCompanyMockMvc;

    private ProductionCompany productionCompany;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionCompany createEntity(EntityManager em) {
        ProductionCompany productionCompany = new ProductionCompany()
            .name(DEFAULT_NAME)
            .tmdbId(DEFAULT_TMDB_ID)
            .logoPath(DEFAULT_LOGO_PATH)
            .originCountry(DEFAULT_ORIGIN_COUNTRY);
        return productionCompany;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionCompany createUpdatedEntity(EntityManager em) {
        ProductionCompany productionCompany = new ProductionCompany()
            .name(UPDATED_NAME)
            .tmdbId(UPDATED_TMDB_ID)
            .logoPath(UPDATED_LOGO_PATH)
            .originCountry(UPDATED_ORIGIN_COUNTRY);
        return productionCompany;
    }

    @BeforeEach
    public void initTest() {
        productionCompany = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductionCompany() throws Exception {
        int databaseSizeBeforeCreate = productionCompanyRepository.findAll().size();
        // Create the ProductionCompany
        ProductionCompanyDTO productionCompanyDTO = productionCompanyMapper.toDto(productionCompany);
        restProductionCompanyMockMvc.perform(post("/api/production-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionCompanyDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductionCompany in the database
        List<ProductionCompany> productionCompanyList = productionCompanyRepository.findAll();
        assertThat(productionCompanyList).hasSize(databaseSizeBeforeCreate + 1);
        ProductionCompany testProductionCompany = productionCompanyList.get(productionCompanyList.size() - 1);
        assertThat(testProductionCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductionCompany.getTmdbId()).isEqualTo(DEFAULT_TMDB_ID);
        assertThat(testProductionCompany.getLogoPath()).isEqualTo(DEFAULT_LOGO_PATH);
        assertThat(testProductionCompany.getOriginCountry()).isEqualTo(DEFAULT_ORIGIN_COUNTRY);

        // Validate the ProductionCompany in Elasticsearch
        verify(mockProductionCompanySearchRepository, times(1)).save(testProductionCompany);
    }

    @Test
    @Transactional
    public void createProductionCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productionCompanyRepository.findAll().size();

        // Create the ProductionCompany with an existing ID
        productionCompany.setId(1L);
        ProductionCompanyDTO productionCompanyDTO = productionCompanyMapper.toDto(productionCompany);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductionCompanyMockMvc.perform(post("/api/production-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionCompanyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductionCompany in the database
        List<ProductionCompany> productionCompanyList = productionCompanyRepository.findAll();
        assertThat(productionCompanyList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProductionCompany in Elasticsearch
        verify(mockProductionCompanySearchRepository, times(0)).save(productionCompany);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productionCompanyRepository.findAll().size();
        // set the field null
        productionCompany.setName(null);

        // Create the ProductionCompany, which fails.
        ProductionCompanyDTO productionCompanyDTO = productionCompanyMapper.toDto(productionCompany);


        restProductionCompanyMockMvc.perform(post("/api/production-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionCompanyDTO)))
            .andExpect(status().isBadRequest());

        List<ProductionCompany> productionCompanyList = productionCompanyRepository.findAll();
        assertThat(productionCompanyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTmdbIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = productionCompanyRepository.findAll().size();
        // set the field null
        productionCompany.setTmdbId(null);

        // Create the ProductionCompany, which fails.
        ProductionCompanyDTO productionCompanyDTO = productionCompanyMapper.toDto(productionCompany);


        restProductionCompanyMockMvc.perform(post("/api/production-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionCompanyDTO)))
            .andExpect(status().isBadRequest());

        List<ProductionCompany> productionCompanyList = productionCompanyRepository.findAll();
        assertThat(productionCompanyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductionCompanies() throws Exception {
        // Initialize the database
        productionCompanyRepository.saveAndFlush(productionCompany);

        // Get all the productionCompanyList
        restProductionCompanyMockMvc.perform(get("/api/production-companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productionCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].tmdbId").value(hasItem(DEFAULT_TMDB_ID.intValue())))
            .andExpect(jsonPath("$.[*].logoPath").value(hasItem(DEFAULT_LOGO_PATH)))
            .andExpect(jsonPath("$.[*].originCountry").value(hasItem(DEFAULT_ORIGIN_COUNTRY)));
    }
    
    @Test
    @Transactional
    public void getProductionCompany() throws Exception {
        // Initialize the database
        productionCompanyRepository.saveAndFlush(productionCompany);

        // Get the productionCompany
        restProductionCompanyMockMvc.perform(get("/api/production-companies/{id}", productionCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productionCompany.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.tmdbId").value(DEFAULT_TMDB_ID.intValue()))
            .andExpect(jsonPath("$.logoPath").value(DEFAULT_LOGO_PATH))
            .andExpect(jsonPath("$.originCountry").value(DEFAULT_ORIGIN_COUNTRY));
    }
    @Test
    @Transactional
    public void getNonExistingProductionCompany() throws Exception {
        // Get the productionCompany
        restProductionCompanyMockMvc.perform(get("/api/production-companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductionCompany() throws Exception {
        // Initialize the database
        productionCompanyRepository.saveAndFlush(productionCompany);

        int databaseSizeBeforeUpdate = productionCompanyRepository.findAll().size();

        // Update the productionCompany
        ProductionCompany updatedProductionCompany = productionCompanyRepository.findById(productionCompany.getId()).get();
        // Disconnect from session so that the updates on updatedProductionCompany are not directly saved in db
        em.detach(updatedProductionCompany);
        updatedProductionCompany
            .name(UPDATED_NAME)
            .tmdbId(UPDATED_TMDB_ID)
            .logoPath(UPDATED_LOGO_PATH)
            .originCountry(UPDATED_ORIGIN_COUNTRY);
        ProductionCompanyDTO productionCompanyDTO = productionCompanyMapper.toDto(updatedProductionCompany);

        restProductionCompanyMockMvc.perform(put("/api/production-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionCompanyDTO)))
            .andExpect(status().isOk());

        // Validate the ProductionCompany in the database
        List<ProductionCompany> productionCompanyList = productionCompanyRepository.findAll();
        assertThat(productionCompanyList).hasSize(databaseSizeBeforeUpdate);
        ProductionCompany testProductionCompany = productionCompanyList.get(productionCompanyList.size() - 1);
        assertThat(testProductionCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductionCompany.getTmdbId()).isEqualTo(UPDATED_TMDB_ID);
        assertThat(testProductionCompany.getLogoPath()).isEqualTo(UPDATED_LOGO_PATH);
        assertThat(testProductionCompany.getOriginCountry()).isEqualTo(UPDATED_ORIGIN_COUNTRY);

        // Validate the ProductionCompany in Elasticsearch
        verify(mockProductionCompanySearchRepository, times(1)).save(testProductionCompany);
    }

    @Test
    @Transactional
    public void updateNonExistingProductionCompany() throws Exception {
        int databaseSizeBeforeUpdate = productionCompanyRepository.findAll().size();

        // Create the ProductionCompany
        ProductionCompanyDTO productionCompanyDTO = productionCompanyMapper.toDto(productionCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionCompanyMockMvc.perform(put("/api/production-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionCompanyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductionCompany in the database
        List<ProductionCompany> productionCompanyList = productionCompanyRepository.findAll();
        assertThat(productionCompanyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProductionCompany in Elasticsearch
        verify(mockProductionCompanySearchRepository, times(0)).save(productionCompany);
    }

    @Test
    @Transactional
    public void deleteProductionCompany() throws Exception {
        // Initialize the database
        productionCompanyRepository.saveAndFlush(productionCompany);

        int databaseSizeBeforeDelete = productionCompanyRepository.findAll().size();

        // Delete the productionCompany
        restProductionCompanyMockMvc.perform(delete("/api/production-companies/{id}", productionCompany.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductionCompany> productionCompanyList = productionCompanyRepository.findAll();
        assertThat(productionCompanyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProductionCompany in Elasticsearch
        verify(mockProductionCompanySearchRepository, times(1)).deleteById(productionCompany.getId());
    }

    @Test
    @Transactional
    public void searchProductionCompany() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        productionCompanyRepository.saveAndFlush(productionCompany);
        when(mockProductionCompanySearchRepository.search(queryStringQuery("id:" + productionCompany.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(productionCompany), PageRequest.of(0, 1), 1));

        // Search the productionCompany
        restProductionCompanyMockMvc.perform(get("/api/_search/production-companies?query=id:" + productionCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productionCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].tmdbId").value(hasItem(DEFAULT_TMDB_ID.intValue())))
            .andExpect(jsonPath("$.[*].logoPath").value(hasItem(DEFAULT_LOGO_PATH)))
            .andExpect(jsonPath("$.[*].originCountry").value(hasItem(DEFAULT_ORIGIN_COUNTRY)));
    }
}
