package gr.movieinsights.web.rest;

import gr.movieinsights.service.ProductionCountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional(readOnly = true)
@RequestMapping({"/entities/companies", "/e/cn"})
public class ProductionCountryResource extends BaseResource {
    private static final String ENTITY_NAME = "countries";


    private final ProductionCountryService productionCountryService;

    public ProductionCountryResource(ProductionCountryService productionCountryService) {
        this.productionCountryService = productionCountryService;
    }

    /**
     * {@code GET  /} : get all the movieInsightsGenerals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movieInsightsGenerals in body.
     */
    //TODO: REIMPLEMENT THIS
    @GetMapping
    public List<Object[]> get() {
        log.debug("REST request to get all ProductionCountries");
        return productionCountryService.getCountryData().parallelStream().map((countryData) -> new Object[]{countryData.getId(),countryData.getName(), countryData.getIso31661(), countryData.getMovieCount()}).collect(Collectors.toList());//.sorted(Comparator.comparingInt(CountryData::getMovieCount).reversed()).collect(Collectors.toList());
    }
}
