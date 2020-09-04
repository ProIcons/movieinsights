package gr.movieinsights.web.rest;

import gr.movieinsights.MovieInsightsApp;
import gr.movieinsights.domain.Movie;
import gr.movieinsights.repository.MovieRepository;
import gr.movieinsights.service.MovieService;
import gr.movieinsights.service.dto.movie.MovieDTO;
import gr.movieinsights.service.mapper.movie.MovieMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MovieResource} REST controller.
 */
@SpringBootTest(classes = MovieInsightsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MovieResourceIT {

    private static final Long DEFAULT_TMDB_ID = 1L;
    private static final Long UPDATED_TMDB_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TAGLINE = "AAAAAAAAAA";
    private static final String UPDATED_TAGLINE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_REVENUE = 1L;
    private static final Long UPDATED_REVENUE = 2L;

    private static final Long DEFAULT_BUDGET = 1L;
    private static final Long UPDATED_BUDGET = 2L;

    private static final String DEFAULT_IMDB_ID = "AAAAAAAAAA";
    private static final String UPDATED_IMDB_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_POPULARITY = 1D;
    private static final Double UPDATED_POPULARITY = 2D;

    private static final Integer DEFAULT_RUNTIME = 1;
    private static final Integer UPDATED_RUNTIME = 2;

    private static final String DEFAULT_POSTER_PATH = "AAAAAAAAAA";
    private static final String UPDATED_POSTER_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_BACKDROP_PATH = "AAAAAAAAAA";
    private static final String UPDATED_BACKDROP_PATH = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RELEASE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RELEASE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MovieRepository movieRepository;

    @Mock
    private MovieRepository movieRepositoryMock;

    @Autowired
    private MovieMapper movieMapper;

    @Mock
    private MovieService movieServiceMock;

    @Autowired
    private MovieService movieService;

   @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMovieMockMvc;

    private Movie movie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movie createEntity(EntityManager em) {
        Movie movie = new Movie()
            .tmdbId(DEFAULT_TMDB_ID)
            .name(DEFAULT_NAME)
            .tagline(DEFAULT_TAGLINE)
            .description(DEFAULT_DESCRIPTION)
            .revenue(DEFAULT_REVENUE)
            .budget(DEFAULT_BUDGET)
            .imdbId(DEFAULT_IMDB_ID)
            .popularity(DEFAULT_POPULARITY)
            .runtime(DEFAULT_RUNTIME)
            .posterPath(DEFAULT_POSTER_PATH)
            .backdropPath(DEFAULT_BACKDROP_PATH)
            .releaseDate(DEFAULT_RELEASE_DATE);
        return movie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movie createUpdatedEntity(EntityManager em) {
        Movie movie = new Movie()
            .tmdbId(UPDATED_TMDB_ID)
            .name(UPDATED_NAME)
            .tagline(UPDATED_TAGLINE)
            .description(UPDATED_DESCRIPTION)
            .revenue(UPDATED_REVENUE)
            .budget(UPDATED_BUDGET)
            .imdbId(UPDATED_IMDB_ID)
            .popularity(UPDATED_POPULARITY)
            .runtime(UPDATED_RUNTIME)
            .posterPath(UPDATED_POSTER_PATH)
            .backdropPath(UPDATED_BACKDROP_PATH)
            .releaseDate(UPDATED_RELEASE_DATE);
        return movie;
    }

    @BeforeEach
    public void initTest() {
        movie = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovie() throws Exception {
        int databaseSizeBeforeCreate = movieRepository.findAll().size();
        // Create the Movie
        MovieDTO movieDTO = movieMapper.toDto(movie);
        restMovieMockMvc.perform(post("/api/movies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isCreated());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeCreate + 1);
        Movie testMovie = movieList.get(movieList.size() - 1);
        assertThat(testMovie.getTmdbId()).isEqualTo(DEFAULT_TMDB_ID);
        assertThat(testMovie.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMovie.getTagline()).isEqualTo(DEFAULT_TAGLINE);
        assertThat(testMovie.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMovie.getRevenue()).isEqualTo(DEFAULT_REVENUE);
        assertThat(testMovie.getBudget()).isEqualTo(DEFAULT_BUDGET);
        assertThat(testMovie.getImdbId()).isEqualTo(DEFAULT_IMDB_ID);
        assertThat(testMovie.getPopularity()).isEqualTo(DEFAULT_POPULARITY);
        assertThat(testMovie.getRuntime()).isEqualTo(DEFAULT_RUNTIME);
        assertThat(testMovie.getPosterPath()).isEqualTo(DEFAULT_POSTER_PATH);
        assertThat(testMovie.getBackdropPath()).isEqualTo(DEFAULT_BACKDROP_PATH);
        assertThat(testMovie.getReleaseDate()).isEqualTo(DEFAULT_RELEASE_DATE);
    }

    @Test
    @Transactional
    public void createMovieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieRepository.findAll().size();

        // Create the Movie with an existing ID
        movie.setId(1L);
        MovieDTO movieDTO = movieMapper.toDto(movie);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieMockMvc.perform(post("/api/movies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTmdbIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieRepository.findAll().size();
        // set the field null
        movie.setTmdbId(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);


        restMovieMockMvc.perform(post("/api/movies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieRepository.findAll().size();
        // set the field null
        movie.setName(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);


        restMovieMockMvc.perform(post("/api/movies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPopularityIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieRepository.findAll().size();
        // set the field null
        movie.setPopularity(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);


        restMovieMockMvc.perform(post("/api/movies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMovies() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList
        restMovieMockMvc.perform(get("/api/movies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movie.getId().intValue())))
            .andExpect(jsonPath("$.[*].tmdbId").value(hasItem(DEFAULT_TMDB_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].tagline").value(hasItem(DEFAULT_TAGLINE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].revenue").value(hasItem(DEFAULT_REVENUE.intValue())))
            .andExpect(jsonPath("$.[*].budget").value(hasItem(DEFAULT_BUDGET.intValue())))
            .andExpect(jsonPath("$.[*].imdbId").value(hasItem(DEFAULT_IMDB_ID)))
            .andExpect(jsonPath("$.[*].popularity").value(hasItem(DEFAULT_POPULARITY.doubleValue())))
            .andExpect(jsonPath("$.[*].runtime").value(hasItem(DEFAULT_RUNTIME)))
            .andExpect(jsonPath("$.[*].posterPath").value(hasItem(DEFAULT_POSTER_PATH)))
            .andExpect(jsonPath("$.[*].backdropPath").value(hasItem(DEFAULT_BACKDROP_PATH)))
            .andExpect(jsonPath("$.[*].releaseDate").value(hasItem(DEFAULT_RELEASE_DATE.toString())));
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMoviesWithEagerRelationshipsIsEnabled() throws Exception {
        when(movieServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMovieMockMvc.perform(get("/api/movies?eagerload=true"))
            .andExpect(status().isOk());

        verify(movieServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMoviesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(movieServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMovieMockMvc.perform(get("/api/movies?eagerload=true"))
            .andExpect(status().isOk());

        verify(movieServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMovie() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get the movie
        restMovieMockMvc.perform(get("/api/movies/{id}", movie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movie.getId().intValue()))
            .andExpect(jsonPath("$.tmdbId").value(DEFAULT_TMDB_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.tagline").value(DEFAULT_TAGLINE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.revenue").value(DEFAULT_REVENUE.intValue()))
            .andExpect(jsonPath("$.budget").value(DEFAULT_BUDGET.intValue()))
            .andExpect(jsonPath("$.imdbId").value(DEFAULT_IMDB_ID))
            .andExpect(jsonPath("$.popularity").value(DEFAULT_POPULARITY.doubleValue()))
            .andExpect(jsonPath("$.runtime").value(DEFAULT_RUNTIME))
            .andExpect(jsonPath("$.posterPath").value(DEFAULT_POSTER_PATH))
            .andExpect(jsonPath("$.backdropPath").value(DEFAULT_BACKDROP_PATH))
            .andExpect(jsonPath("$.releaseDate").value(DEFAULT_RELEASE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingMovie() throws Exception {
        // Get the movie
        restMovieMockMvc.perform(get("/api/movies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovie() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        int databaseSizeBeforeUpdate = movieRepository.findAll().size();

        // Update the movie
        Movie updatedMovie = movieRepository.findById(movie.getId()).get();
        // Disconnect from session so that the updates on updatedMovie are not directly saved in db
        em.detach(updatedMovie);
        updatedMovie
            .tmdbId(UPDATED_TMDB_ID)
            .name(UPDATED_NAME)
            .tagline(UPDATED_TAGLINE)
            .description(UPDATED_DESCRIPTION)
            .revenue(UPDATED_REVENUE)
            .budget(UPDATED_BUDGET)
            .imdbId(UPDATED_IMDB_ID)
            .popularity(UPDATED_POPULARITY)
            .runtime(UPDATED_RUNTIME)
            .posterPath(UPDATED_POSTER_PATH)
            .backdropPath(UPDATED_BACKDROP_PATH)
            .releaseDate(UPDATED_RELEASE_DATE);
        MovieDTO movieDTO = movieMapper.toDto(updatedMovie);

        restMovieMockMvc.perform(put("/api/movies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isOk());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
        Movie testMovie = movieList.get(movieList.size() - 1);
        assertThat(testMovie.getTmdbId()).isEqualTo(UPDATED_TMDB_ID);
        assertThat(testMovie.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMovie.getTagline()).isEqualTo(UPDATED_TAGLINE);
        assertThat(testMovie.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMovie.getRevenue()).isEqualTo(UPDATED_REVENUE);
        assertThat(testMovie.getBudget()).isEqualTo(UPDATED_BUDGET);
        assertThat(testMovie.getImdbId()).isEqualTo(UPDATED_IMDB_ID);
        assertThat(testMovie.getPopularity()).isEqualTo(UPDATED_POPULARITY);
        assertThat(testMovie.getRuntime()).isEqualTo(UPDATED_RUNTIME);
        assertThat(testMovie.getPosterPath()).isEqualTo(UPDATED_POSTER_PATH);
        assertThat(testMovie.getBackdropPath()).isEqualTo(UPDATED_BACKDROP_PATH);
        assertThat(testMovie.getReleaseDate()).isEqualTo(UPDATED_RELEASE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMovie() throws Exception {
        int databaseSizeBeforeUpdate = movieRepository.findAll().size();

        // Create the Movie
        MovieDTO movieDTO = movieMapper.toDto(movie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieMockMvc.perform(put("/api/movies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMovie() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        int databaseSizeBeforeDelete = movieRepository.findAll().size();

        // Delete the movie
        restMovieMockMvc.perform(delete("/api/movies/{id}", movie.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
