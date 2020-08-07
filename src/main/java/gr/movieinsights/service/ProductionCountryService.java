package gr.movieinsights.service;

import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.repository.ProductionCountryRepository;
import gr.movieinsights.repository.search.ProductionCountrySearchRepository;
import gr.movieinsights.service.dto.ProductionCountryDTO;
import gr.movieinsights.service.mapper.ProductionCountryMapper;
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
 * Service Implementation for managing {@link ProductionCountry}.
 */
@Service
@Transactional
public class ProductionCountryService {

    private final Logger log = LoggerFactory.getLogger(ProductionCountryService.class);

    private final ProductionCountryRepository productionCountryRepository;

    private final ProductionCountryMapper productionCountryMapper;

    private final ProductionCountrySearchRepository productionCountrySearchRepository;

    public ProductionCountryService(ProductionCountryRepository productionCountryRepository, ProductionCountryMapper productionCountryMapper, ProductionCountrySearchRepository productionCountrySearchRepository) {
        this.productionCountryRepository = productionCountryRepository;
        this.productionCountryMapper = productionCountryMapper;
        this.productionCountrySearchRepository = productionCountrySearchRepository;
    }

    /**
     * Save a productionCountry.
     *
     * @param productionCountryDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductionCountryDTO save(ProductionCountryDTO productionCountryDTO) {
        log.debug("Request to save ProductionCountry : {}", productionCountryDTO);
        ProductionCountry productionCountry = productionCountryMapper.toEntity(productionCountryDTO);
        productionCountry = productionCountryRepository.save(productionCountry);
        ProductionCountryDTO result = productionCountryMapper.toDto(productionCountry);
        productionCountrySearchRepository.save(productionCountry);
        return result;
    }

    /**
     * Get all the productionCountries.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProductionCountryDTO> findAll() {
        log.debug("Request to get all ProductionCountries");
        return productionCountryRepository.findAll().stream()
            .map(productionCountryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productionCountry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductionCountryDTO> findOne(Long id) {
        log.debug("Request to get ProductionCountry : {}", id);
        return productionCountryRepository.findById(id)
            .map(productionCountryMapper::toDto);
    }

    /**
     * Delete the productionCountry by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductionCountry : {}", id);
        productionCountryRepository.deleteById(id);
        productionCountrySearchRepository.deleteById(id);
    }

    /**
     * Search for the productionCountry corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProductionCountryDTO> search(String query) {
        log.debug("Request to search ProductionCountries for query {}", query);
        return StreamSupport
            .stream(productionCountrySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(productionCountryMapper::toDto)
        .collect(Collectors.toList());
    }
}
