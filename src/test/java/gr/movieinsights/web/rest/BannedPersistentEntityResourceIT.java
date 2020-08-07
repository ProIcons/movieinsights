package gr.movieinsights.web.rest;

import gr.movieinsights.MovieInsightsApp;
import gr.movieinsights.domain.BannedPersistentEntity;
import gr.movieinsights.repository.BannedPersistentEntityRepository;
import gr.movieinsights.repository.search.BannedPersistentEntitySearchRepository;
import gr.movieinsights.service.BannedPersistentEntityService;
import gr.movieinsights.service.dto.BannedPersistentEntityDTO;
import gr.movieinsights.service.mapper.BannedPersistentEntityMapper;

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

import gr.movieinsights.domain.enumeration.BanReason;
/**
 * Integration tests for the {@link BannedPersistentEntityResource} REST controller.
 */
@SpringBootTest(classes = MovieInsightsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class BannedPersistentEntityResourceIT {

    private static final BanReason DEFAULT_REASON = BanReason.UNDEFINED;
    private static final BanReason UPDATED_REASON = BanReason.NOBUDGET;

    private static final String DEFAULT_REASON_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_REASON_TEXT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TIMESTAMP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TIMESTAMP = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private BannedPersistentEntityRepository bannedPersistentEntityRepository;

    @Autowired
    private BannedPersistentEntityMapper bannedPersistentEntityMapper;

    @Autowired
    private BannedPersistentEntityService bannedPersistentEntityService;

    /**
     * This repository is mocked in the gr.movieinsights.repository.search test package.
     *
     * @see gr.movieinsights.repository.search.BannedPersistentEntitySearchRepositoryMockConfiguration
     */
    @Autowired
    private BannedPersistentEntitySearchRepository mockBannedPersistentEntitySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBannedPersistentEntityMockMvc;

    private BannedPersistentEntity bannedPersistentEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BannedPersistentEntity createEntity(EntityManager em) {
        BannedPersistentEntity bannedPersistentEntity = new BannedPersistentEntity()
            .reason(DEFAULT_REASON)
            .reasonText(DEFAULT_REASON_TEXT)
            .timestamp(DEFAULT_TIMESTAMP);
        return bannedPersistentEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BannedPersistentEntity createUpdatedEntity(EntityManager em) {
        BannedPersistentEntity bannedPersistentEntity = new BannedPersistentEntity()
            .reason(UPDATED_REASON)
            .reasonText(UPDATED_REASON_TEXT)
            .timestamp(UPDATED_TIMESTAMP);
        return bannedPersistentEntity;
    }

    @BeforeEach
    public void initTest() {
        bannedPersistentEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createBannedPersistentEntity() throws Exception {
        int databaseSizeBeforeCreate = bannedPersistentEntityRepository.findAll().size();
        // Create the BannedPersistentEntity
        BannedPersistentEntityDTO bannedPersistentEntityDTO = bannedPersistentEntityMapper.toDto(bannedPersistentEntity);
        restBannedPersistentEntityMockMvc.perform(post("/api/banned-persistent-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bannedPersistentEntityDTO)))
            .andExpect(status().isCreated());

        // Validate the BannedPersistentEntity in the database
        List<BannedPersistentEntity> bannedPersistentEntityList = bannedPersistentEntityRepository.findAll();
        assertThat(bannedPersistentEntityList).hasSize(databaseSizeBeforeCreate + 1);
        BannedPersistentEntity testBannedPersistentEntity = bannedPersistentEntityList.get(bannedPersistentEntityList.size() - 1);
        assertThat(testBannedPersistentEntity.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testBannedPersistentEntity.getReasonText()).isEqualTo(DEFAULT_REASON_TEXT);
        assertThat(testBannedPersistentEntity.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);

        // Validate the BannedPersistentEntity in Elasticsearch
        verify(mockBannedPersistentEntitySearchRepository, times(1)).save(testBannedPersistentEntity);
    }

    @Test
    @Transactional
    public void createBannedPersistentEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bannedPersistentEntityRepository.findAll().size();

        // Create the BannedPersistentEntity with an existing ID
        bannedPersistentEntity.setId(1L);
        BannedPersistentEntityDTO bannedPersistentEntityDTO = bannedPersistentEntityMapper.toDto(bannedPersistentEntity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBannedPersistentEntityMockMvc.perform(post("/api/banned-persistent-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bannedPersistentEntityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BannedPersistentEntity in the database
        List<BannedPersistentEntity> bannedPersistentEntityList = bannedPersistentEntityRepository.findAll();
        assertThat(bannedPersistentEntityList).hasSize(databaseSizeBeforeCreate);

        // Validate the BannedPersistentEntity in Elasticsearch
        verify(mockBannedPersistentEntitySearchRepository, times(0)).save(bannedPersistentEntity);
    }


    @Test
    @Transactional
    public void checkReasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = bannedPersistentEntityRepository.findAll().size();
        // set the field null
        bannedPersistentEntity.setReason(null);

        // Create the BannedPersistentEntity, which fails.
        BannedPersistentEntityDTO bannedPersistentEntityDTO = bannedPersistentEntityMapper.toDto(bannedPersistentEntity);


        restBannedPersistentEntityMockMvc.perform(post("/api/banned-persistent-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bannedPersistentEntityDTO)))
            .andExpect(status().isBadRequest());

        List<BannedPersistentEntity> bannedPersistentEntityList = bannedPersistentEntityRepository.findAll();
        assertThat(bannedPersistentEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = bannedPersistentEntityRepository.findAll().size();
        // set the field null
        bannedPersistentEntity.setTimestamp(null);

        // Create the BannedPersistentEntity, which fails.
        BannedPersistentEntityDTO bannedPersistentEntityDTO = bannedPersistentEntityMapper.toDto(bannedPersistentEntity);


        restBannedPersistentEntityMockMvc.perform(post("/api/banned-persistent-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bannedPersistentEntityDTO)))
            .andExpect(status().isBadRequest());

        List<BannedPersistentEntity> bannedPersistentEntityList = bannedPersistentEntityRepository.findAll();
        assertThat(bannedPersistentEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBannedPersistentEntities() throws Exception {
        // Initialize the database
        bannedPersistentEntityRepository.saveAndFlush(bannedPersistentEntity);

        // Get all the bannedPersistentEntityList
        restBannedPersistentEntityMockMvc.perform(get("/api/banned-persistent-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bannedPersistentEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].reasonText").value(hasItem(DEFAULT_REASON_TEXT)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())));
    }
    
    @Test
    @Transactional
    public void getBannedPersistentEntity() throws Exception {
        // Initialize the database
        bannedPersistentEntityRepository.saveAndFlush(bannedPersistentEntity);

        // Get the bannedPersistentEntity
        restBannedPersistentEntityMockMvc.perform(get("/api/banned-persistent-entities/{id}", bannedPersistentEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bannedPersistentEntity.getId().intValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.reasonText").value(DEFAULT_REASON_TEXT))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBannedPersistentEntity() throws Exception {
        // Get the bannedPersistentEntity
        restBannedPersistentEntityMockMvc.perform(get("/api/banned-persistent-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBannedPersistentEntity() throws Exception {
        // Initialize the database
        bannedPersistentEntityRepository.saveAndFlush(bannedPersistentEntity);

        int databaseSizeBeforeUpdate = bannedPersistentEntityRepository.findAll().size();

        // Update the bannedPersistentEntity
        BannedPersistentEntity updatedBannedPersistentEntity = bannedPersistentEntityRepository.findById(bannedPersistentEntity.getId()).get();
        // Disconnect from session so that the updates on updatedBannedPersistentEntity are not directly saved in db
        em.detach(updatedBannedPersistentEntity);
        updatedBannedPersistentEntity
            .reason(UPDATED_REASON)
            .reasonText(UPDATED_REASON_TEXT)
            .timestamp(UPDATED_TIMESTAMP);
        BannedPersistentEntityDTO bannedPersistentEntityDTO = bannedPersistentEntityMapper.toDto(updatedBannedPersistentEntity);

        restBannedPersistentEntityMockMvc.perform(put("/api/banned-persistent-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bannedPersistentEntityDTO)))
            .andExpect(status().isOk());

        // Validate the BannedPersistentEntity in the database
        List<BannedPersistentEntity> bannedPersistentEntityList = bannedPersistentEntityRepository.findAll();
        assertThat(bannedPersistentEntityList).hasSize(databaseSizeBeforeUpdate);
        BannedPersistentEntity testBannedPersistentEntity = bannedPersistentEntityList.get(bannedPersistentEntityList.size() - 1);
        assertThat(testBannedPersistentEntity.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testBannedPersistentEntity.getReasonText()).isEqualTo(UPDATED_REASON_TEXT);
        assertThat(testBannedPersistentEntity.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);

        // Validate the BannedPersistentEntity in Elasticsearch
        verify(mockBannedPersistentEntitySearchRepository, times(1)).save(testBannedPersistentEntity);
    }

    @Test
    @Transactional
    public void updateNonExistingBannedPersistentEntity() throws Exception {
        int databaseSizeBeforeUpdate = bannedPersistentEntityRepository.findAll().size();

        // Create the BannedPersistentEntity
        BannedPersistentEntityDTO bannedPersistentEntityDTO = bannedPersistentEntityMapper.toDto(bannedPersistentEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBannedPersistentEntityMockMvc.perform(put("/api/banned-persistent-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bannedPersistentEntityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BannedPersistentEntity in the database
        List<BannedPersistentEntity> bannedPersistentEntityList = bannedPersistentEntityRepository.findAll();
        assertThat(bannedPersistentEntityList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BannedPersistentEntity in Elasticsearch
        verify(mockBannedPersistentEntitySearchRepository, times(0)).save(bannedPersistentEntity);
    }

    @Test
    @Transactional
    public void deleteBannedPersistentEntity() throws Exception {
        // Initialize the database
        bannedPersistentEntityRepository.saveAndFlush(bannedPersistentEntity);

        int databaseSizeBeforeDelete = bannedPersistentEntityRepository.findAll().size();

        // Delete the bannedPersistentEntity
        restBannedPersistentEntityMockMvc.perform(delete("/api/banned-persistent-entities/{id}", bannedPersistentEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BannedPersistentEntity> bannedPersistentEntityList = bannedPersistentEntityRepository.findAll();
        assertThat(bannedPersistentEntityList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BannedPersistentEntity in Elasticsearch
        verify(mockBannedPersistentEntitySearchRepository, times(1)).deleteById(bannedPersistentEntity.getId());
    }

    @Test
    @Transactional
    public void searchBannedPersistentEntity() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        bannedPersistentEntityRepository.saveAndFlush(bannedPersistentEntity);
        when(mockBannedPersistentEntitySearchRepository.search(queryStringQuery("id:" + bannedPersistentEntity.getId())))
            .thenReturn(Collections.singletonList(bannedPersistentEntity));

        // Search the bannedPersistentEntity
        restBannedPersistentEntityMockMvc.perform(get("/api/_search/banned-persistent-entities?query=id:" + bannedPersistentEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bannedPersistentEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].reasonText").value(hasItem(DEFAULT_REASON_TEXT)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())));
    }
}
