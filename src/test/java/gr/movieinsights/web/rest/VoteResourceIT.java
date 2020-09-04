package gr.movieinsights.web.rest;

import gr.movieinsights.MovieInsightsApp;
import gr.movieinsights.domain.Vote;
import gr.movieinsights.repository.VoteRepository;
import gr.movieinsights.service.VoteService;
import gr.movieinsights.service.dto.vote.VoteDTO;
import gr.movieinsights.service.mapper.vote.VoteMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VoteResource} REST controller.
 */
@SpringBootTest(classes = MovieInsightsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class VoteResourceIT {

    private static final Double DEFAULT_AVERAGE = 1D;
    private static final Double UPDATED_AVERAGE = 2D;

    private static final Integer DEFAULT_VOTES = 1;
    private static final Integer UPDATED_VOTES = 2;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteMapper voteMapper;

    @Autowired
    private VoteService voteService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoteMockMvc;

    private Vote vote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vote createEntity(EntityManager em) {
        Vote vote = new Vote()
            .average(DEFAULT_AVERAGE)
            .votes(DEFAULT_VOTES);
        return vote;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vote createUpdatedEntity(EntityManager em) {
        Vote vote = new Vote()
            .average(UPDATED_AVERAGE)
            .votes(UPDATED_VOTES);
        return vote;
    }

    @BeforeEach
    public void initTest() {
        vote = createEntity(em);
    }

    @Test
    @Transactional
    public void createVote() throws Exception {
        int databaseSizeBeforeCreate = voteRepository.findAll().size();
        // Create the Vote
        VoteDTO voteDTO = voteMapper.toDto(vote);
        restVoteMockMvc.perform(post("/api/votes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voteDTO)))
            .andExpect(status().isCreated());

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeCreate + 1);
        Vote testVote = voteList.get(voteList.size() - 1);
        assertThat(testVote.getAverage()).isEqualTo(DEFAULT_AVERAGE);
        assertThat(testVote.getVotes()).isEqualTo(DEFAULT_VOTES);
    }

    @Test
    @Transactional
    public void createVoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voteRepository.findAll().size();

        // Create the Vote with an existing ID
        vote.setId(1L);
        VoteDTO voteDTO = voteMapper.toDto(vote);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoteMockMvc.perform(post("/api/votes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAverageIsRequired() throws Exception {
        int databaseSizeBeforeTest = voteRepository.findAll().size();
        // set the field null
        vote.setAverage(null);

        // Create the Vote, which fails.
        VoteDTO voteDTO = voteMapper.toDto(vote);


        restVoteMockMvc.perform(post("/api/votes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voteDTO)))
            .andExpect(status().isBadRequest());

        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVotesIsRequired() throws Exception {
        int databaseSizeBeforeTest = voteRepository.findAll().size();
        // set the field null
        vote.setVotes(null);

        // Create the Vote, which fails.
        VoteDTO voteDTO = voteMapper.toDto(vote);


        restVoteMockMvc.perform(post("/api/votes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voteDTO)))
            .andExpect(status().isBadRequest());

        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVotes() throws Exception {
        // Initialize the database
        voteRepository.saveAndFlush(vote);

        // Get all the voteList
        restVoteMockMvc.perform(get("/api/votes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vote.getId().intValue())))
            .andExpect(jsonPath("$.[*].average").value(hasItem(DEFAULT_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].votes").value(hasItem(DEFAULT_VOTES)));
    }

    @Test
    @Transactional
    public void getVote() throws Exception {
        // Initialize the database
        voteRepository.saveAndFlush(vote);

        // Get the vote
        restVoteMockMvc.perform(get("/api/votes/{id}", vote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vote.getId().intValue()))
            .andExpect(jsonPath("$.average").value(DEFAULT_AVERAGE.doubleValue()))
            .andExpect(jsonPath("$.votes").value(DEFAULT_VOTES));
    }
    @Test
    @Transactional
    public void getNonExistingVote() throws Exception {
        // Get the vote
        restVoteMockMvc.perform(get("/api/votes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVote() throws Exception {
        // Initialize the database
        voteRepository.saveAndFlush(vote);

        int databaseSizeBeforeUpdate = voteRepository.findAll().size();

        // Update the vote
        Vote updatedVote = voteRepository.findById(vote.getId()).get();
        // Disconnect from session so that the updates on updatedVote are not directly saved in db
        em.detach(updatedVote);
        updatedVote
            .average(UPDATED_AVERAGE)
            .votes(UPDATED_VOTES);
        VoteDTO voteDTO = voteMapper.toDto(updatedVote);

        restVoteMockMvc.perform(put("/api/votes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voteDTO)))
            .andExpect(status().isOk());

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeUpdate);
        Vote testVote = voteList.get(voteList.size() - 1);
        assertThat(testVote.getAverage()).isEqualTo(UPDATED_AVERAGE);
        assertThat(testVote.getVotes()).isEqualTo(UPDATED_VOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingVote() throws Exception {
        int databaseSizeBeforeUpdate = voteRepository.findAll().size();

        // Create the Vote
        VoteDTO voteDTO = voteMapper.toDto(vote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoteMockMvc.perform(put("/api/votes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVote() throws Exception {
        // Initialize the database
        voteRepository.saveAndFlush(vote);

        int databaseSizeBeforeDelete = voteRepository.findAll().size();

        // Delete the vote
        restVoteMockMvc.perform(delete("/api/votes/{id}", vote.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
