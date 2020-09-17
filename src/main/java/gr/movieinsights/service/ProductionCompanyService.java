package gr.movieinsights.service;

import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.models.SearchableEntityMovieCountMap;
import gr.movieinsights.repository.ProductionCompanyRepository;
import gr.movieinsights.repository.search.ProductionCompanySearchRepository;
import gr.movieinsights.service.dto.company.BasicProductionCompanyDTO;
import gr.movieinsights.service.dto.company.ProductionCompanyDTO;
import gr.movieinsights.service.mapper.company.BasicProductionCompanyMapper;
import gr.movieinsights.service.mapper.company.ProductionCompanyMapper;
import gr.movieinsights.service.util.BaseSearchableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductionCompany}.
 */
@Service
@Transactional
public class ProductionCompanyService extends BaseSearchableService<ProductionCompany, gr.movieinsights.domain.elasticsearch.ProductionCompany, ProductionCompanyDTO, BasicProductionCompanyDTO, ProductionCompanyRepository, ProductionCompanySearchRepository, ProductionCompanyMapper, BasicProductionCompanyMapper> {
    public ProductionCompanyService(ProductionCompanyRepository productionCompanyRepository, ProductionCompanySearchRepository productionCompanySearchRepository, ProductionCompanyMapper productionCompanyMapper, BasicProductionCompanyMapper basicProductionCompanyMapper, SearchableEntityMovieCountMap searchableEntityMovieCountMap) {
        super(productionCompanyRepository, productionCompanySearchRepository, productionCompanyMapper,basicProductionCompanyMapper,searchableEntityMovieCountMap);
    }
}
