package gr.movieinsights.service;

import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.repository.ProductionCountryRepository;
import gr.movieinsights.repository.search.ProductionCountrySearchRepository;
import gr.movieinsights.service.dto.country.ProductionCountryDTO;
import gr.movieinsights.service.mapper.country.ProductionCountryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        //productionCountrySearchRepository.save(productionCountry);
        return result;
    }

    /**
     * Get all the productionCountries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductionCountryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductionCountries");
        return productionCountryRepository.findAll(pageable)
            .map(productionCountryMapper::toDto);
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
     * Get one productionCountry by ISO 3166_1.
     *
     * @param iso
     *     the country iso code of the entity.
     *
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductionCountryDTO> findByIso31661(String iso) {
        log.debug("Request to get ProductionCountry by ISO 3166_1 : {}", iso);
        return productionCountryRepository.findByIso31661(iso)
            .map(productionCountryMapper::toDto);
    }

    /**
     * Search for the productionCountry corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductionCountryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProductionCountries for query {}", query);
        /*return productionCountrySearchRepository.search(queryStringQuery(query), pageable)
            .map(productionCountryMapper::toDto);*/
        return null;
    }
}
