package gr.movieinsights.service;

import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.repository.ProductionCompanyRepository;
import gr.movieinsights.repository.search.ProductionCompanySearchRepository;
import gr.movieinsights.service.dto.ProductionCompanyDTO;
import gr.movieinsights.service.mapper.ProductionCompanyMapper;
import gr.movieinsights.service.util.TmdbIdentifiedBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link ProductionCompany}.
 */
@Service
@Transactional
public class ProductionCompanyService extends TmdbIdentifiedBaseService<ProductionCompany, ProductionCompanyDTO, ProductionCompanyRepository, ProductionCompanyMapper> {

    private final Logger log = LoggerFactory.getLogger(ProductionCompanyService.class);

    private final ProductionCompanySearchRepository productionCompanySearchRepository;

    public ProductionCompanyService(ProductionCompanyRepository productionCompanyRepository, ProductionCompanyMapper productionCompanyMapper, ProductionCompanySearchRepository productionCompanySearchRepository) {
        super(productionCompanyRepository, productionCompanyMapper);
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
        ProductionCompany productionCompany = mapper.toEntity(productionCompanyDTO);
        productionCompany = repository.save(productionCompany);
        ProductionCompanyDTO result = mapper.toDto(productionCompany);
        productionCompanySearchRepository.save(productionCompany);
        return result;
    }

    /**
     * Get all the productionCompanies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductionCompanyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductionCompanies");
        return repository.findAll(pageable)
            .map(mapper::toDto);
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
        return repository.findById(id)
            .map(mapper::toDto);
    }

    /**
     * Delete the productionCompany by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductionCompany : {}", id);
        repository.deleteById(id);
        productionCompanySearchRepository.deleteById(id);
    }

    /**
     * Search for the productionCompany corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductionCompanyDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProductionCompanies for query {}", query);
        return productionCompanySearchRepository.search(queryStringQuery(query), pageable)
            .map(mapper::toDto);
    }
}
