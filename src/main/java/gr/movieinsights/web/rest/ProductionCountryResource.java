package gr.movieinsights.web.rest;

import gr.movieinsights.repository.ProductionCountryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional(readOnly = true)
@RequestMapping("/entities/companies")
public class ProductionCountryResource extends BaseResource {
    private static final String ENTITY_NAME = "countries";

    private final ProductionCountryRepository productionCountryRepository;

    public ProductionCountryResource(ProductionCountryRepository productionCountryRepository) {
        this.productionCountryRepository = productionCountryRepository;
    }

    /**
     * {@code GET  /} : get all the movieInsightsGenerals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movieInsightsGenerals in body.
     *//*
    @GetMapping
    public List<ProductionCountryRepository.CountryWMC> get() {
        log.debug("REST request to get all ProductionCountries");
        return productionCountryRepository.getAllWithMovieCount();
    }*/
}
