/*
package gr.movieinsights.web.rest;

import gr.movieinsights.MovieInsightsApp;
import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.repository.ProductionCountryRepository;
import gr.movieinsights.repository.search.ProductionCountrySearchRepository;
import gr.movieinsights.service.ProductionCountryService;
import gr.movieinsights.service.dto.country.ProductionCountryDTO;
import gr.movieinsights.service.mapper.country.ProductionCountryMapper;
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
 * Integration tests for the {@link ProductionCountryResource} REST controller.
 *//*

@SpringBootTest(classes = MovieInsightsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductionCountryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ISO_31661 = "AAAAAAAAAA";
    private static final String UPDATED_ISO_31661 = "BBBBBBBBBB";

    @Autowired
    private ProductionCountryRepository productionCountryRepository;

    @Autowired
    private ProductionCountryMapper productionCountryMapper;

    @Autowired
    private ProductionCountryService productionCountryService;

    */
/**
     * This repository is mocked in the gr.movieinsights.repository.search test package.
     *
     * @see gr.movieinsights.repository.search.ProductionCountrySearchRepositoryMockConfiguration
     *//*

    @Autowired
    private ProductionCountrySearchRepository mockProductionCountrySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductionCountryMockMvc;

    private ProductionCountry productionCountry;

    */
/**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*

    public static ProductionCountry createEntity(EntityManager em) {
        ProductionCountry productionCountry = new ProductionCountry()
            .name(DEFAULT_NAME)
            .iso31661(DEFAULT_ISO_31661);
        return productionCountry;
    }
    */
/**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*

    public static ProductionCountry createUpdatedEntity(EntityManager em) {
        ProductionCountry productionCountry = new ProductionCountry()
            .name(UPDATED_NAME)
            .iso31661(UPDATED_ISO_31661);
        return productionCountry;
    }

    @BeforeEach
    public void initTest() {
        productionCountry = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductionCountry() throws Exception {
        int databaseSizeBeforeCreate = productionCountryRepository.findAll().size();
        // Create the ProductionCountry
        ProductionCountryDTO productionCountryDTO = productionCountryMapper.toDto(productionCountry);
        restProductionCountryMockMvc.perform(post("/api/production-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionCountryDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductionCountry in the database
        List<ProductionCountry> productionCountryList = productionCountryRepository.findAll();
        assertThat(productionCountryList).hasSize(databaseSizeBeforeCreate + 1);
        ProductionCountry testProductionCountry = productionCountryList.get(productionCountryList.size() - 1);
        assertThat(testProductionCountry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductionCountry.getIso31661()).isEqualTo(DEFAULT_ISO_31661);

        // Validate the ProductionCountry in Elasticsearch
        verify(mockProductionCountrySearchRepository, times(1)).save(testProductionCountry);
    }

    @Test
    @Transactional
    public void createProductionCountryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productionCountryRepository.findAll().size();

        // Create the ProductionCountry with an existing ID
        productionCountry.setId(1L);
        ProductionCountryDTO productionCountryDTO = productionCountryMapper.toDto(productionCountry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductionCountryMockMvc.perform(post("/api/production-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .conaatent(TestUtil.convertObjectToJsonBytes(productionCountryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductionCountry in the database
        List<ProductionCountry> productionCountryList = productionCountryRepository.findAll();
        assertThat(productionCountryList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProductionCountry in Elasticsearch
        verify(mockProductionCountrySearchRepository, times(0)).save(productionCountry);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productionCountryRepository.findAll().size();
        // set the field null
        productionCountry.setName(null);

        // Create the ProductionCountry, which fails.
        ProductionCountryDTO productionCountryDTO = productionCountryMapper.toDto(productionCountry);


        restProductionCountryMockMvc.perform(post("/api/production-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionCountryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductionCountry> productionCountryList = productionCountryRepository.findAll();
        assertThat(productionCountryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIso31661IsRequired() throws Exception {
        int databaseSizeBeforeTest = productionCountryRepository.findAll().size();
        // set the field null
        productionCountry.setIso31661(null);

        // Create the ProductionCountry, which fails.
        ProductionCountryDTO productionCountryDTO = productionCountryMapper.toDto(productionCountry);


        restProductionCountryMockMvc.perform(post("/api/production-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionCountryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductionCountry> productionCountryList = productionCountryRepository.findAll();
        assertThat(productionCountryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductionCountries() throws Exception {
        // Initialize the database
        productionCountryRepository.saveAndFlush(productionCountry);

        // Get all the productionCountryList
        restProductionCountryMockMvc.perform(get("/api/production-countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productionCountry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].iso31661").value(hasItem(DEFAULT_ISO_31661)));
    }

    @Test
    @Transactional
    public void getProductionCountry() throws Exception {
        // Initialize the database
        productionCountryRepository.saveAndFlush(productionCountry);

        // Get the productionCountry
        restProductionCountryMockMvc.perform(get("/api/production-countries/{id}", productionCountry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productionCountry.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.iso31661").value(DEFAULT_ISO_31661));
    }
    @Test
    @Transactional
    public void getNonExistingProductionCountry() throws Exception {
        // Get the productionCountry
        restProductionCountryMockMvc.perform(get("/api/production-countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductionCountry() throws Exception {
        // Initialize the database
        productionCountryRepository.saveAndFlush(productionCountry);

        int databaseSizeBeforeUpdate = productionCountryRepository.findAll().size();

        // Update the productionCountry
        ProductionCountry updatedProductionCountry = productionCountryRepository.findById(productionCountry.getId()).get();
        // Disconnect from session so that the updates on updatedProductionCountry are not directly saved in db
        em.detach(updatedProductionCountry);
        updatedProductionCountry
            .name(UPDATED_NAME)
            .iso31661(UPDATED_ISO_31661);
        ProductionCountryDTO productionCountryDTO = productionCountryMapper.toDto(updatedProductionCountry);

        restProductionCountryMockMvc.perform(put("/api/production-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionCountryDTO)))
            .andExpect(status().isOk());

        // Validate the ProductionCountry in the database
        List<ProductionCountry> productionCountryList = productionCountryRepository.findAll();
        assertThat(productionCountryList).hasSize(databaseSizeBeforeUpdate);
        ProductionCountry testProductionCountry = productionCountryList.get(productionCountryList.size() - 1);
        assertThat(testProductionCountry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductionCountry.getIso31661()).isEqualTo(UPDATED_ISO_31661);

        // Validate the ProductionCountry in Elasticsearch
        verify(mockProductionCountrySearchRepository, times(1)).save(testProductionCountry);
    }

    @Test
    @Transactional
    public void updateNonExistingProductionCountry() throws Exception {
        int databaseSizeBeforeUpdate = productionCountryRepository.findAll().size();

        // Create the ProductionCountry
        ProductionCountryDTO productionCountryDTO = productionCountryMapper.toDto(productionCountry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionCountryMockMvc.perform(put("/api/production-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionCountryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductionCountry in the database
        List<ProductionCountry> productionCountryList = productionCountryRepository.findAll();
        assertThat(productionCountryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProductionCountry in Elasticsearch
        verify(mockProductionCountrySearchRepository, times(0)).save(productionCountry);
    }

    @Test
    @Transactional
    public void deleteProductionCountry() throws Exception {
        // Initialize the database
        productionCountryRepository.saveAndFlush(productionCountry);

        int databaseSizeBeforeDelete = productionCountryRepository.findAll().size();

        // Delete the productionCountry
        restProductionCountryMockMvc.perform(delete("/api/production-countries/{id}", productionCountry.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductionCountry> productionCountryList = productionCountryRepository.findAll();
        assertThat(productionCountryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProductionCountry in Elasticsearch
        verify(mockProductionCountrySearchRepository, times(1)).deleteById(productionCountry.getId());
    }

    @Test
    @Transactional
    public void searchProductionCountry() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        productionCountryRepository.saveAndFlush(productionCountry);
        when(mockProductionCountrySearchRepository.search(queryStringQuery("id:" + productionCountry.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(productionCountry), PageRequest.of(0, 1), 1));

        // Search the productionCountry
        restProductionCountryMockMvc.perform(get("/api/_search/production-countries?query=id:" + productionCountry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productionCountry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].iso31661").value(hasItem(DEFAULT_ISO_31661)));
    }
}
*/
