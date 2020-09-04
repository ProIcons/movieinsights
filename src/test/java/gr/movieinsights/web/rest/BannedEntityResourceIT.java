package gr.movieinsights.web.rest;

import gr.movieinsights.MovieInsightsApp;
import gr.movieinsights.domain.BannedEntity;
import gr.movieinsights.domain.enumeration.BanReason;
import gr.movieinsights.domain.enumeration.TmdbEntityType;
import gr.movieinsights.repository.BannedEntityRepository;
import gr.movieinsights.service.BannedEntityService;
import gr.movieinsights.service.dto.BannedEntityDTO;
import gr.movieinsights.service.mapper.BannedEntityMapper;
import gr.movieinsights.web.rest.admin.BannedEntityResource;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    private static final String DEFAULT_IMDB_ID = "AAAAAAAAAA";
    private static final String UPDATED_IMDB_ID = "BBBBBBBBBB";

    private static final TmdbEntityType DEFAULT_TYPE = TmdbEntityType.MOVIE;
    private static final TmdbEntityType UPDATED_TYPE = TmdbEntityType.PERSON;

    private static final BanReason DEFAULT_REASON = BanReason.UNDEFINED;
    private static final BanReason UPDATED_REASON = BanReason.NOBUDGET;

    private static final String DEFAULT_REASON_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_REASON_TEXT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TIMESTAMP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TIMESTAMP = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private BannedEntityRepository bannedEntityRepository;

    @Autowired
    private BannedEntityMapper bannedEntityMapper;

    @Autowired
    private BannedEntityService bannedEntityService;

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
            .imdbId(DEFAULT_IMDB_ID)
            .type(DEFAULT_TYPE)
            .reason(DEFAULT_REASON)
            .reasonText(DEFAULT_REASON_TEXT)
            .timestamp(DEFAULT_TIMESTAMP);
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
            .imdbId(UPDATED_IMDB_ID)
            .type(UPDATED_TYPE)
            .reason(UPDATED_REASON)
            .reasonText(UPDATED_REASON_TEXT)
            .timestamp(UPDATED_TIMESTAMP);
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
        assertThat(testBannedEntity.getImdbId()).isEqualTo(DEFAULT_IMDB_ID);
        assertThat(testBannedEntity.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBannedEntity.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testBannedEntity.getReasonText()).isEqualTo(DEFAULT_REASON_TEXT);
        assertThat(testBannedEntity.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);

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
    public void checkReasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = bannedEntityRepository.findAll().size();
        // set the field null
        bannedEntity.setReason(null);

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
    public void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = bannedEntityRepository.findAll().size();
        // set the field null
        bannedEntity.setTimestamp(null);

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
            .andExpect(jsonPath("$.[*].imdbId").value(hasItem(DEFAULT_IMDB_ID)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].reasonText").value(hasItem(DEFAULT_REASON_TEXT)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())));
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
            .andExpect(jsonPath("$.imdbId").value(DEFAULT_IMDB_ID))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.reasonText").value(DEFAULT_REASON_TEXT))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()));
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
            .imdbId(UPDATED_IMDB_ID)
            .type(UPDATED_TYPE)
            .reason(UPDATED_REASON)
            .reasonText(UPDATED_REASON_TEXT)
            .timestamp(UPDATED_TIMESTAMP);
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
        assertThat(testBannedEntity.getImdbId()).isEqualTo(UPDATED_IMDB_ID);
        assertThat(testBannedEntity.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBannedEntity.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testBannedEntity.getReasonText()).isEqualTo(UPDATED_REASON_TEXT);
        assertThat(testBannedEntity.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
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
    }
}
