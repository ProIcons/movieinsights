package gr.movieinsights.service;

import gr.movieinsights.domain.MovieInsightsPerCompany;
import gr.movieinsights.repository.MovieInsightsPerCompanyRepository;
import gr.movieinsights.repository.search.MovieInsightsPerCompanySearchRepository;
import gr.movieinsights.service.dto.MovieInsightsPerCompanyDTO;
import gr.movieinsights.service.mapper.MovieInsightsPerCompanyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MovieInsightsPerCompany}.
 */
@Service
@Transactional
public class MovieInsightsPerCompanyService {

    private final Logger log = LoggerFactory.getLogger(MovieInsightsPerCompanyService.class);

    private final MovieInsightsPerCompanyRepository movieInsightsPerCompanyRepository;

    private final MovieInsightsPerCompanyMapper movieInsightsPerCompanyMapper;

    private final MovieInsightsPerCompanySearchRepository movieInsightsPerCompanySearchRepository;

    public MovieInsightsPerCompanyService(MovieInsightsPerCompanyRepository movieInsightsPerCompanyRepository, MovieInsightsPerCompanyMapper movieInsightsPerCompanyMapper, MovieInsightsPerCompanySearchRepository movieInsightsPerCompanySearchRepository) {
        this.movieInsightsPerCompanyRepository = movieInsightsPerCompanyRepository;
        this.movieInsightsPerCompanyMapper = movieInsightsPerCompanyMapper;
        this.movieInsightsPerCompanySearchRepository = movieInsightsPerCompanySearchRepository;
    }

    /**
     * Save a movieInsightsPerCompany.
     *
     * @param movieInsightsPerCompanyDTO the entity to save.
     * @return the persisted entity.
     */
    public MovieInsightsPerCompanyDTO save(MovieInsightsPerCompanyDTO movieInsightsPerCompanyDTO) {
        log.debug("Request to save MovieInsightsPerCompany : {}", movieInsightsPerCompanyDTO);
        MovieInsightsPerCompany movieInsightsPerCompany = movieInsightsPerCompanyMapper.toEntity(movieInsightsPerCompanyDTO);
        movieInsightsPerCompany = movieInsightsPerCompanyRepository.save(movieInsightsPerCompany);
        MovieInsightsPerCompanyDTO result = movieInsightsPerCompanyMapper.toDto(movieInsightsPerCompany);
        movieInsightsPerCompanySearchRepository.save(movieInsightsPerCompany);
        return result;
    }

    /**
     * Get all the movieInsightsPerCompanies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MovieInsightsPerCompanyDTO> findAll() {
        log.debug("Request to get all MovieInsightsPerCompanies");
        return movieInsightsPerCompanyRepository.findAll().stream()
            .map(movieInsightsPerCompanyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one movieInsightsPerCompany by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieInsightsPerCompanyDTO> findOne(Long id) {
        log.debug("Request to get MovieInsightsPerCompany : {}", id);
        return movieInsightsPerCompanyRepository.findById(id)
            .map(movieInsightsPerCompanyMapper::toDto);
    }

    /**
     * Delete the movieInsightsPerCompany by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MovieInsightsPerCompany : {}", id);
        movieInsightsPerCompanyRepository.deleteById(id);
        movieInsightsPerCompanySearchRepository.deleteById(id);
    }

    /**
     * Search for the movieInsightsPerCompany corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MovieInsightsPerCompanyDTO> search(String query) {
        log.debug("Request to search MovieInsightsPerCompanies for query {}", query);
        return StreamSupport
            .stream(movieInsightsPerCompanySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(movieInsightsPerCompanyMapper::toDto)
            .collect(Collectors.toList());
    }
}
