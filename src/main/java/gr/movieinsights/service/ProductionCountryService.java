package gr.movieinsights.service;

import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.models.CountryData;
import gr.movieinsights.models.SearchableEntityMovieCountMap;
import gr.movieinsights.repository.ProductionCountryRepository;
import gr.movieinsights.repository.search.ProductionCountrySearchRepository;
import gr.movieinsights.service.dto.country.BasicProductionCountryDTO;
import gr.movieinsights.service.dto.country.ProductionCountryDTO;
import gr.movieinsights.service.mapper.country.BasicProductionCountryMapper;
import gr.movieinsights.service.mapper.country.ProductionCountryMapper;
import gr.movieinsights.service.util.BaseSearchableService;
import gr.movieinsights.service.util.QueryConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductionCountry}.
 */
@Service
@Transactional
public class ProductionCountryService extends BaseSearchableService<ProductionCountry, gr.movieinsights.domain.elasticsearch.ProductionCountry, ProductionCountryDTO, BasicProductionCountryDTO, ProductionCountryRepository, ProductionCountrySearchRepository, ProductionCountryMapper, BasicProductionCountryMapper> {

    private final List<CountryData> countryDataCache = new ArrayList<>();

    public ProductionCountryService(ProductionCountryRepository productionCountryRepository, ProductionCountryMapper productionCountryMapper, BasicProductionCountryMapper productionCountryBasicMapper, ProductionCountrySearchRepository productionCountrySearchRepository,SearchableEntityMovieCountMap searchableEntityMovieCountMap) {
        super(productionCountryRepository,productionCountrySearchRepository,productionCountryMapper,productionCountryBasicMapper, searchableEntityMovieCountMap);
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
        return getRepository().findByIso31661(iso)
            .map(getMapper()::toDto);
    }


    public List<CountryData> getCountryData() {
        if (countryDataCache.size() <= 0) {
            countryDataCache.addAll(getRepository().getCountryData());
        }
        return countryDataCache;
    }

    @Override
    protected QueryConfiguration queryConfiguration() {
        QueryConfiguration q = QueryConfiguration.CreateDefault();
        q.setBoost(0.9f);
        return q;
    }
    public void clearCache() {
        countryDataCache.clear();
    }
}
