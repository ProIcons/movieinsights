package gr.movieinsights.web.rest;

import gr.movieinsights.MovieInsightsApp;
import gr.movieinsights.domain.BannedEntity;
import gr.movieinsights.repository.BannedEntityRepository;
import gr.movieinsights.repository.search.BannedEntitySearchRepository;
import gr.movieinsights.service.BannedEntityService;
import gr.movieinsights.service.dto.BannedEntityDTO;
import gr.movieinsights.service.mapper.BannedEntityMapper;

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

import gr.movieinsights.domain.enumeration.EntityType;
/**
 * Integration tests for the {@link BannedEntityResource} REST controller.
 */
@SpringBootTest(classes = MovieInsightsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class BannedEntityResourceIT {

    private static final Long DEFAULT_TMDB_ID = 1L;
    private static final Long UPDATED_TMDB_ID = 2L;

    private static final EntityType DEFAULT_TYPE = EntityType.MOVIE;
    private static final EntityType UPDATED_TYPE = EntityType.PERSON;

    @Autowired
    private BannedEntityRepository bannedEntityRepository;

    @Autowired
    private BannedEntityMapper bannedEntityMapper;

    @Autowired
    private BannedEntityService bannedEntityService;

    /**
     * This repository is mocked in the gr.movieinsights.repository.search test package.
     *
     * @see gr.movieinsights.repository.search.BannedEntitySearchRepositoryMockConfiguration
     */
    @Autowired
    private BannedEntitySearchRepository mockBannedEntitySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBannedEntityMockMvc;

    private BannedEntity bannedEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BannedEntity createEntity(EntityManager em) {
        BannedEntity bannedEntity = new BannedEntity()
            .tmdbId(DEFAULT_TMDB_ID)
            .type(DEFAULT_TYPE);
        return bannedEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BannedEntity createUpdatedEntity(EntityManager em) {
        BannedEntity bannedEntity = new BannedEntity()
            .tmdbId(UPDATED_TMDB_ID)
            .type(UPDATED_TYPE);
        return bannedEntity;
    }

    @BeforeEach
    public void initTest() {
        bannedEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createBannedEntity() throws Exception {
        int databaseSizeBeforeCreate = bannedEntityRepository.findAll().size();
        // Create the BannedEntity
        BannedEntityDTO bannedEntityDTO = bannedEntityMapper.toDto(bannedEntity);
        restBannedEntityMockMvc.perform(post("/api/banned-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bannedEntityDTO)))
            .andExpect(status().isCreated());

        // Validate the BannedEntity in the database
        List<BannedEntity> bannedEntityList = bannedEntityRepository.findAll();
        assertThat(bannedEntityList).hasSize(databaseSizeBeforeCreate + 1);
        BannedEntity testBannedEntity = bannedEntityList.get(bannedEntityList.size() - 1);
        assertThat(testBannedEntity.getTmdbId()).isEqualTo(DEFAULT_TMDB_ID);
        assertThat(testBannedEntity.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the BannedEntity in Elasticsearch
        verify(mockBannedEntitySearchRepository, times(1)).save(testBannedEntity);
    }

    @Test
    @Transactional
    public void createBannedEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bannedEntityRepository.findAll().size();

        // Create the BannedEntity with an existing ID
        bannedEntity.setId(1L);
        BannedEntityDTO bannedEntityDTO = bannedEntityMapper.toDto(bannedEntity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBannedEntityMockMvc.perform(post("/api/banned-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bannedEntityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BannedEntity in the database
        List<BannedEntity> bannedEntityList = bannedEntityRepository.findAll();
        assertThat(bannedEntityList).hasSize(databaseSizeBeforeCreate);

        // Validate the BannedEntity in Elasticsearch
        verify(mockBannedEntitySearchRepository, times(0)).save(bannedEntity);
    }


    @Test
    @Transactional
    public void checkTmdbIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bannedEntityRepository.findAll().size();
        // set the field null
        bannedEntity.setTmdbId(null);

        // Create the BannedEntity, which fails.
        BannedEntityDTO bannedEntityDTO = bannedEntityMapper.toDto(bannedEntity);


        restBannedEntityMockMvc.perform(post("/api/banned-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bannedEntityDTO)))
            .andExpect(status().isBadRequest());

        List<BannedEntity> bannedEntityList = bannedEntityRepository.findAll();
        assertThat(bannedEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bannedEntityRepository.findAll().size();
        // set the field null
        bannedEntity.setType(null);

        // Create the BannedEntity, which fails.
        BannedEntityDTO bannedEntityDTO = bannedEntityMapper.toDto(bannedEntity);


        restBannedEntityMockMvc.perform(post("/api/banned-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bannedEntityDTO)))
            .andExpect(status().isBadRequest());

        List<BannedEntity> bannedEntityList = bannedEntityRepository.findAll();
        assertThat(bannedEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBannedEntities() throws Exception {
        // Initialize the database
        bannedEntityRepository.saveAndFlush(bannedEntity);

        // Get all the bannedEntityList
        restBannedEntityMockMvc.perform(get("/api/banned-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bannedEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].tmdbId").value(hasItem(DEFAULT_TMDB_ID.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getBannedEntity() throws Exception {
        // Initialize the database
        bannedEntityRepository.saveAndFlush(bannedEntity);

        // Get the bannedEntity
        restBannedEntityMockMvc.perform(get("/api/banned-entities/{id}", bannedEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bannedEntity.getId().intValue()))
            .andExpect(jsonPath("$.tmdbId").value(DEFAULT_TMDB_ID.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBannedEntity() throws Exception {
        // Get the bannedEntity
        restBannedEntityMockMvc.perform(get("/api/banned-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBannedEntity() throws Exception {
        // Initialize the database
        bannedEntityRepository.saveAndFlush(bannedEntity);

        int databaseSizeBeforeUpdate = bannedEntityRepository.findAll().size();

        // Update the bannedEntity
        BannedEntity updatedBannedEntity = bannedEntityRepository.findById(bannedEntity.getId()).get();
        // Disconnect from session so that the updates on updatedBannedEntity are not directly saved in db
        em.detach(updatedBannedEntity);
        updatedBannedEntity
            .tmdbId(UPDATED_TMDB_ID)
            .type(UPDATED_TYPE);
        BannedEntityDTO bannedEntityDTO = bannedEntityMapper.toDto(updatedBannedEntity);

        restBannedEntityMockMvc.perform(put("/api/banned-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bannedEntityDTO)))
            .andExpect(status().isOk());

        // Validate the BannedEntity in the database
        List<BannedEntity> bannedEntityList = bannedEntityRepository.findAll();
        assertThat(bannedEntityList).hasSize(databaseSizeBeforeUpdate);
        BannedEntity testBannedEntity = bannedEntityList.get(bannedEntityList.size() - 1);
        assertThat(testBannedEntity.getTmdbId()).isEqualTo(UPDATED_TMDB_ID);
        assertThat(testBannedEntity.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the BannedEntity in Elasticsearch
        verify(mockBannedEntitySearchRepository, times(1)).save(testBannedEntity);
    }

    @Test
    @Transactional
    public void updateNonExistingBannedEntity() throws Exception {
        int databaseSizeBeforeUpdate = bannedEntityRepository.findAll().size();

        // Create the BannedEntity
        BannedEntityDTO bannedEntityDTO = bannedEntityMapper.toDto(bannedEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBannedEntityMockMvc.perform(put("/api/banned-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bannedEntityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BannedEntity in the database
        List<BannedEntity> bannedEntityList = bannedEntityRepository.findAll();
        assertThat(bannedEntityList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BannedEntity in Elasticsearch
        verify(mockBannedEntitySearchRepository, times(0)).save(bannedEntity);
    }

    @Test
    @Transactional
    public void deleteBannedEntity() throws Exception {
        // Initialize the database
        bannedEntityRepository.saveAndFlush(bannedEntity);

        int databaseSizeBeforeDelete = bannedEntityRepository.findAll().size();

        // Delete the bannedEntity
        restBannedEntityMockMvc.perform(delete("/api/banned-entities/{id}", bannedEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BannedEntity> bannedEntityList = bannedEntityRepository.findAll();
        assertThat(bannedEntityList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BannedEntity in Elasticsearch
        verify(mockBannedEntitySearchRepository, times(1)).deleteById(bannedEntity.getId());
    }

    @Test
    @Transactional
    public void searchBannedEntity() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        bannedEntityRepository.saveAndFlush(bannedEntity);
        when(mockBannedEntitySearchRepository.search(queryStringQuery("id:" + bannedEntity.getId())))
            .thenReturn(Collections.singletonList(bannedEntity));

        // Search the bannedEntity
        restBannedEntityMockMvc.perform(get("/api/_search/banned-entities?query=id:" + bannedEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bannedEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].tmdbId").value(hasItem(DEFAULT_TMDB_ID.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
}
