package gr.movieinsights.web.rest;

import gr.movieinsights.MovieInsightsApp;
import gr.movieinsights.domain.Image;
import gr.movieinsights.repository.ImageRepository;
import gr.movieinsights.repository.search.ImageSearchRepository;
import gr.movieinsights.service.ImageService;
import gr.movieinsights.service.dto.ImageDTO;
import gr.movieinsights.service.mapper.ImageMapper;

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
 * Integration tests for the {@link ImageResource} REST controller.
 */
@SpringBootTest(classes = MovieInsightsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ImageResourceIT {

    private static final Long DEFAULT_TMDB_ID = 1L;
    private static final Long UPDATED_TMDB_ID = 2L;

    private static final Integer DEFAULT_ASPECT_RATIO = 1;
    private static final Integer UPDATED_ASPECT_RATIO = 2;

    private static final String DEFAULT_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FILE_PATH = "BBBBBBBBBB";

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Integer DEFAULT_WIDTH = 1;
    private static final Integer UPDATED_WIDTH = 2;

    private static final String DEFAULT_ISO_6391 = "AAAAAAAAAA";
    private static final String UPDATED_ISO_6391 = "BBBBBBBBBB";

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private ImageService imageService;

    /**
     * This repository is mocked in the gr.movieinsights.repository.search test package.
     *
     * @see gr.movieinsights.repository.search.ImageSearchRepositoryMockConfiguration
     */
    @Autowired
    private ImageSearchRepository mockImageSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImageMockMvc;

    private Image image;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Image createEntity(EntityManager em) {
        Image image = new Image()
            .tmdbId(DEFAULT_TMDB_ID)
            .aspectRatio(DEFAULT_ASPECT_RATIO)
            .filePath(DEFAULT_FILE_PATH)
            .height(DEFAULT_HEIGHT)
            .width(DEFAULT_WIDTH)
            .iso6391(DEFAULT_ISO_6391);
        return image;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Image createUpdatedEntity(EntityManager em) {
        Image image = new Image()
            .tmdbId(UPDATED_TMDB_ID)
            .aspectRatio(UPDATED_ASPECT_RATIO)
            .filePath(UPDATED_FILE_PATH)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH)
            .iso6391(UPDATED_ISO_6391);
        return image;
    }

    @BeforeEach
    public void initTest() {
        image = createEntity(em);
    }

    @Test
    @Transactional
    public void createImage() throws Exception {
        int databaseSizeBeforeCreate = imageRepository.findAll().size();
        // Create the Image
        ImageDTO imageDTO = imageMapper.toDto(image);
        restImageMockMvc.perform(post("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isCreated());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeCreate + 1);
        Image testImage = imageList.get(imageList.size() - 1);
        assertThat(testImage.getTmdbId()).isEqualTo(DEFAULT_TMDB_ID);
        assertThat(testImage.getAspectRatio()).isEqualTo(DEFAULT_ASPECT_RATIO);
        assertThat(testImage.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);
        assertThat(testImage.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testImage.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testImage.getIso6391()).isEqualTo(DEFAULT_ISO_6391);

        // Validate the Image in Elasticsearch
        verify(mockImageSearchRepository, times(1)).save(testImage);
    }

    @Test
    @Transactional
    public void createImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imageRepository.findAll().size();

        // Create the Image with an existing ID
        image.setId(1L);
        ImageDTO imageDTO = imageMapper.toDto(image);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImageMockMvc.perform(post("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeCreate);

        // Validate the Image in Elasticsearch
        verify(mockImageSearchRepository, times(0)).save(image);
    }


    @Test
    @Transactional
    public void checkTmdbIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageRepository.findAll().size();
        // set the field null
        image.setTmdbId(null);

        // Create the Image, which fails.
        ImageDTO imageDTO = imageMapper.toDto(image);


        restImageMockMvc.perform(post("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAspectRatioIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageRepository.findAll().size();
        // set the field null
        image.setAspectRatio(null);

        // Create the Image, which fails.
        ImageDTO imageDTO = imageMapper.toDto(image);


        restImageMockMvc.perform(post("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFilePathIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageRepository.findAll().size();
        // set the field null
        image.setFilePath(null);

        // Create the Image, which fails.
        ImageDTO imageDTO = imageMapper.toDto(image);


        restImageMockMvc.perform(post("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageRepository.findAll().size();
        // set the field null
        image.setHeight(null);

        // Create the Image, which fails.
        ImageDTO imageDTO = imageMapper.toDto(image);


        restImageMockMvc.perform(post("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWidthIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageRepository.findAll().size();
        // set the field null
        image.setWidth(null);

        // Create the Image, which fails.
        ImageDTO imageDTO = imageMapper.toDto(image);


        restImageMockMvc.perform(post("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllImages() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList
        restImageMockMvc.perform(get("/api/images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(image.getId().intValue())))
            .andExpect(jsonPath("$.[*].tmdbId").value(hasItem(DEFAULT_TMDB_ID.intValue())))
            .andExpect(jsonPath("$.[*].aspectRatio").value(hasItem(DEFAULT_ASPECT_RATIO)))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].iso6391").value(hasItem(DEFAULT_ISO_6391)));
    }
    
    @Test
    @Transactional
    public void getImage() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get the image
        restImageMockMvc.perform(get("/api/images/{id}", image.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(image.getId().intValue()))
            .andExpect(jsonPath("$.tmdbId").value(DEFAULT_TMDB_ID.intValue()))
            .andExpect(jsonPath("$.aspectRatio").value(DEFAULT_ASPECT_RATIO))
            .andExpect(jsonPath("$.filePath").value(DEFAULT_FILE_PATH))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.iso6391").value(DEFAULT_ISO_6391));
    }
    @Test
    @Transactional
    public void getNonExistingImage() throws Exception {
        // Get the image
        restImageMockMvc.perform(get("/api/images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImage() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        int databaseSizeBeforeUpdate = imageRepository.findAll().size();

        // Update the image
        Image updatedImage = imageRepository.findById(image.getId()).get();
        // Disconnect from session so that the updates on updatedImage are not directly saved in db
        em.detach(updatedImage);
        updatedImage
            .tmdbId(UPDATED_TMDB_ID)
            .aspectRatio(UPDATED_ASPECT_RATIO)
            .filePath(UPDATED_FILE_PATH)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH)
            .iso6391(UPDATED_ISO_6391);
        ImageDTO imageDTO = imageMapper.toDto(updatedImage);

        restImageMockMvc.perform(put("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isOk());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
        Image testImage = imageList.get(imageList.size() - 1);
        assertThat(testImage.getTmdbId()).isEqualTo(UPDATED_TMDB_ID);
        assertThat(testImage.getAspectRatio()).isEqualTo(UPDATED_ASPECT_RATIO);
        assertThat(testImage.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testImage.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testImage.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testImage.getIso6391()).isEqualTo(UPDATED_ISO_6391);

        // Validate the Image in Elasticsearch
        verify(mockImageSearchRepository, times(1)).save(testImage);
    }

    @Test
    @Transactional
    public void updateNonExistingImage() throws Exception {
        int databaseSizeBeforeUpdate = imageRepository.findAll().size();

        // Create the Image
        ImageDTO imageDTO = imageMapper.toDto(image);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImageMockMvc.perform(put("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Image in Elasticsearch
        verify(mockImageSearchRepository, times(0)).save(image);
    }

    @Test
    @Transactional
    public void deleteImage() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        int databaseSizeBeforeDelete = imageRepository.findAll().size();

        // Delete the image
        restImageMockMvc.perform(delete("/api/images/{id}", image.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Image in Elasticsearch
        verify(mockImageSearchRepository, times(1)).deleteById(image.getId());
    }

    @Test
    @Transactional
    public void searchImage() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        imageRepository.saveAndFlush(image);
        when(mockImageSearchRepository.search(queryStringQuery("id:" + image.getId())))
            .thenReturn(Collections.singletonList(image));

        // Search the image
        restImageMockMvc.perform(get("/api/_search/images?query=id:" + image.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(image.getId().intValue())))
            .andExpect(jsonPath("$.[*].tmdbId").value(hasItem(DEFAULT_TMDB_ID.intValue())))
            .andExpect(jsonPath("$.[*].aspectRatio").value(hasItem(DEFAULT_ASPECT_RATIO)))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].iso6391").value(hasItem(DEFAULT_ISO_6391)));
    }
}
