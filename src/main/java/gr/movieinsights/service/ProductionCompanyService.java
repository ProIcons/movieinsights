package gr.movieinsights.service;

import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.repository.ProductionCompanyRepository;
import gr.movieinsights.repository.search.ProductionCompanySearchRepository;
import gr.movieinsights.service.dto.ProductionCompanyDTO;
import gr.movieinsights.service.mapper.ProductionCompanyMapper;
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
 * Service Implementation for managing {@link ProductionCompany}.
 */
@Service
@Transactional
public class ProductionCompanyService {

    private final Logger log = LoggerFactory.getLogger(ProductionCompanyService.class);

    private final ProductionCompanyRepository productionCompanyRepository;

    private final ProductionCompanyMapper productionCompanyMapper;

    private final ProductionCompanySearchRepository productionCompanySearchRepository;

    public ProductionCompanyService(ProductionCompanyRepository productionCompanyRepository, ProductionCompanyMapper productionCompanyMapper, ProductionCompanySearchRepository productionCompanySearchRepository) {
        this.productionCompanyRepository = productionCompanyRepository;
        this.productionCompanyMapper = productionCompanyMapper;
        this.productionCompanySearchRepository = productionCompanySearchRepository;
    }

    /**
     * Save a productionCompany.
     *
     * @param productionCompanyDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductionCompanyDTO save(ProductionCompanyDTO productionCompanyDTO) {
        log.debug("Request to save ProductionCompany : {}", productionCompanyDTO);
        ProductionCompany productionCompany = productionCompanyMapper.toEntity(productionCompanyDTO);
        productionCompany = productionCompanyRepository.save(productionCompany);
        ProductionCompanyDTO result = productionCompanyMapper.toDto(productionCompany);
        productionCompanySearchRepository.save(productionCompany);
        return result;
    }

    /**
     * Get all the productionCompanies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProductionCompanyDTO> findAll() {
        log.debug("Request to get all ProductionCompanies");
        return productionCompanyRepository.findAll().stream()
            .map(productionCompanyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productionCompany by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductionCompanyDTO> findOne(Long id) {
        log.debug("Request to get ProductionCompany : {}", id);
        return productionCompanyRepository.findById(id)
            .map(productionCompanyMapper::toDto);
    }

    /**
     * Delete the productionCompany by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductionCompany : {}", id);
        productionCompanyRepository.deleteById(id);
        productionCompanySearchRepository.deleteById(id);
    }

    /**
     * Search for the productionCompany corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProductionCompanyDTO> search(String query) {
        log.debug("Request to search ProductionCompanies for query {}", query);
        return StreamSupport
            .stream(productionCompanySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(productionCompanyMapper::toDto)
        .collect(Collectors.toList());
    }
}
