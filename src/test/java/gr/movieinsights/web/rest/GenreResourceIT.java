/*
package gr.movieinsights.web.rest;

import gr.movieinsights.MovieInsightsApp;
import gr.movieinsights.domain.Genre;
import gr.movieinsights.repository.GenreRepository;
import gr.movieinsights.repository.search.GenreSearchRepository;
import gr.movieinsights.service.GenreService;
import gr.movieinsights.service.dto.genre.GenreDTO;
import gr.movieinsights.service.mapper.genre.GenreMapper;
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
 * Integration tests for the {@link GenreResource} REST controller.
 *//*

@SpringBootTest(classes = MovieInsightsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GenreResourceIT {

    private static final Long DEFAULT_TMDB_ID = 1L;
    private static final Long UPDATED_TMDB_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GenreMapper genreMapper;

    @Autowired
    private GenreService genreService;

    */
/**
     * This repository is mocked in the gr.movieinsights.repository.search test package.
     *
     * @see gr.movieinsights.repository.search.GenreSearchRepositoryMockConfiguration
     *//*

    @Autowired
    private GenreSearchRepository mockGenreSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGenreMockMvc;

    private Genre genre;

    */
/**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*

    public static Genre createEntity(EntityManager em) {
        Genre genre = new Genre()
            .tmdbId(DEFAULT_TMDB_ID)
            .name(DEFAULT_NAME);
        return genre;
    }
    */
/**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*

    public static Genre createUpdatedEntity(EntityManager em) {
        Genre genre = new Genre()
            .tmdbId(UPDATED_TMDB_ID)
            .name(UPDATED_NAME);
        return genre;
    }

    @BeforeEach
    public void initTest() {
        genre = createEntity(em);
    }

    @Test
    @Transactional
    public void createGenre() throws Exception {
        int databaseSizeBeforeCreate = genreRepository.findAll().size();
        // Create the Genre
        GenreDTO genreDTO = genreMapper.toDto(genre);
        restGenreMockMvc.perform(post("/api/genres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(genreDTO)))
            .andExpect(status().isCreated());

        // Validate the Genre in the database
        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeCreate + 1);
        Genre testGenre = genreList.get(genreList.size() - 1);
        assertThat(testGenre.getTmdbId()).isEqualTo(DEFAULT_TMDB_ID);
        assertThat(testGenre.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Genre in Elasticsearch
        verify(mockGenreSearchRepository, times(1)).save(testGenre);
    }

    @Test
    @Transactional
    public void createGenreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = genreRepository.findAll().size();

        // Create the Genre with an existing ID
        genre.setId(1L);
        GenreDTO genreDTO = genreMapper.toDto(genre);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGenreMockMvc.perform(post("/api/genres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(genreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Genre in the database
        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeCreate);

        // Validate the Genre in Elasticsearch
        verify(mockGenreSearchRepository, times(0)).save(genre);
    }


    @Test
    @Transactional
    public void checkTmdbIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = genreRepository.findAll().size();
        // set the field null
        genre.setTmdbId(null);

        // Create the Genre, which fails.
        GenreDTO genreDTO = genreMapper.toDto(genre);


        restGenreMockMvc.perform(post("/api/genres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(genreDTO)))
            .andExpect(status().isBadRequest());

        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = genreRepository.findAll().size();
        // set the field null
        genre.setName(null);

        // Create the Genre, which fails.
        GenreDTO genreDTO = genreMapper.toDto(genre);


        restGenreMockMvc.perform(post("/api/genres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(genreDTO)))
            .andExpect(status().isBadRequest());

        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGenres() throws Exception {
        // Initialize the database
        genreRepository.saveAndFlush(genre);

        // Get all the genreList
        restGenreMockMvc.perform(get("/api/genres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genre.getId().intValue())))
            .andExpect(jsonPath("$.[*].tmdbId").value(hasItem(DEFAULT_TMDB_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void getGenre() throws Exception {
        // Initialize the database
        genreRepository.saveAndFlush(genre);

        // Get the genre
        restGenreMockMvc.perform(get("/api/genres/{id}", genre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(genre.getId().intValue()))
            .andExpect(jsonPath("$.tmdbId").value(DEFAULT_TMDB_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingGenre() throws Exception {
        // Get the genre
        restGenreMockMvc.perform(get("/api/genres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGenre() throws Exception {
        // Initialize the database
        genreRepository.saveAndFlush(genre);

        int databaseSizeBeforeUpdate = genreRepository.findAll().size();

        // Update the genre
        Genre updatedGenre = genreRepository.findById(genre.getId()).get();
        // Disconnect from session so that the updates on updatedGenre are not directly saved in db
        em.detach(updatedGenre);
        updatedGenre
            .tmdbId(UPDATED_TMDB_ID)
            .name(UPDATED_NAME);
        GenreDTO genreDTO = genreMapper.toDto(updatedGenre);

        restGenreMockMvc.perform(put("/api/genres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(genreDTO)))
            .andExpect(status().isOk());

        // Validate the Genre in the database
        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeUpdate);
        Genre testGenre = genreList.get(genreList.size() - 1);
        assertThat(testGenre.getTmdbId()).isEqualTo(UPDATED_TMDB_ID);
        assertThat(testGenre.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Genre in Elasticsearch
        verify(mockGenreSearchRepository, times(1)).save(testGenre);
    }

    @Test
    @Transactional
    public void updateNonExistingGenre() throws Exception {
        int databaseSizeBeforeUpdate = genreRepository.findAll().size();

        // Create the Genre
        GenreDTO genreDTO = genreMapper.toDto(genre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGenreMockMvc.perform(put("/api/genres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(genreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Genre in the database
        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Genre in Elasticsearch
        verify(mockGenreSearchRepository, times(0)).save(genre);
    }

    @Test
    @Transactional
    public void deleteGenre() throws Exception {
        // Initialize the database
        genreRepository.saveAndFlush(genre);

        int databaseSizeBeforeDelete = genreRepository.findAll().size();

        // Delete the genre
        restGenreMockMvc.perform(delete("/api/genres/{id}", genre.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Genre in Elasticsearch
        verify(mockGenreSearchRepository, times(1)).deleteById(genre.getId());
    }

    @Test
    @Transactional
    public void searchGenre() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        genreRepository.saveAndFlush(genre);
        when(mockGenreSearchRepository.search(queryStringQuery("id:" + genre.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(genre), PageRequest.of(0, 1), 1));

        // Search the genre
        restGenreMockMvc.perform(get("/api/_search/genres?query=id:" + genre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genre.getId().intValue())))
            .andExpect(jsonPath("$.[*].tmdbId").value(hasItem(DEFAULT_TMDB_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
}
*/
