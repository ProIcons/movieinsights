package gr.movieinsights.web.rest;


import gr.movieinsights.config.converters.ExtendedParam;
import gr.movieinsights.domain.elasticsearch.ProductionCountry;
import gr.movieinsights.domain.elasticsearch.Genre;
import gr.movieinsights.domain.elasticsearch.Person;
import gr.movieinsights.domain.elasticsearch.ProductionCompany;
import gr.movieinsights.models.AutoCompleteResult;
import gr.movieinsights.models.SearchResult;
import gr.movieinsights.security.AuthoritiesConstants;
import gr.movieinsights.security.SecurityUtils;
import gr.movieinsights.service.ElasticsearchIndexService;
import gr.movieinsights.service.GenreService;
import gr.movieinsights.service.PersonService;
import gr.movieinsights.service.ProductionCompanyService;
import gr.movieinsights.service.ProductionCountryService;
import gr.movieinsights.service.SearchService;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping({"/search", "/s"})
public class SearchResource extends BaseResource {

    private final ElasticsearchIndexService elasticsearchIndexService;
    private final GenreService genreService;
    private final PersonService personService;
    private final ProductionCompanyService productionCompanyService;
    private final ProductionCountryService productionCountryService;
    private final SearchService searchService;

    public SearchResource(ElasticsearchIndexService elasticsearchIndexService, GenreService genreService, PersonService personService, ProductionCompanyService productionCompanyService, ProductionCountryService productionCountryService, SearchService searchService) {
        this.elasticsearchIndexService = elasticsearchIndexService;
        this.genreService = genreService;
        this.personService = personService;
        this.productionCompanyService = productionCompanyService;
        this.productionCountryService = productionCountryService;
        this.searchService = searchService;
    }


    /**
     * {@code SEARCH  /?q=:query} : search for the genre corresponding to the query.
     *
     * @param query
     *     the query of the genre search.
     *
     * @return the result of the search.
     */
    @GetMapping
    public ResponseEntity<SearchResult> search(@RequestParam("q") String query) {
        log.debug("REST request to search for a page for query {}", query);
        Pageable pageable = PageRequest.of(0,5);
        Page<Genre> genrePage = genreService.search(query, pageable);
        Page<ProductionCompany> commpanyPage = productionCompanyService.search(query, pageable);
        //Page<BasicProductionCountryDTO> countryPage = productionCountryService.search(query, pageable);
        Page<Person> personPage = personService.search(query, pageable);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().body(SearchResult.of(genrePage, commpanyPage, personPage));
    }

    /**
     * {@code SEARCH  /?q=:query} : search for the genre corresponding to the query.
     *
     * @param query
     *     the query of the genre search.
     *
     * @return the result of the search.
     */
    @GetMapping("ac")
    public ResponseEntity<AutoCompleteResult> autocomplete(@RequestParam("q") String query,@ExtendedParam boolean extended) {
        log.debug("REST request to search for a page for query {}", query);
        AutoCompleteResult autoComplete = searchService.autocomplete(query,extended);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().body(autoComplete);
    }

    /**
     * {@code SEARCH  /genres?query=:query} : search for the genre corresponding to the query.
     *
     * @param query
     *     the query of the genre search.
     * @param pageable
     *     the pagination information.
     *
     * @return the result of the search.
     */
    @GetMapping({"/genres", "/gn"})
    public ResponseEntity<List<Genre>> searchGenres(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Genres for query {}", query);
        Page<Genre> page = genreService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code SEARCH  /people?query=:query} : search for the person corresponding to the query.
     *
     * @param query
     *     the query of the person search.
     * @param pageable
     *     the pagination information.
     *
     * @return the result of the search.
     */
    @GetMapping({"/people", "/p"})
    public ResponseEntity<List<Person>> searchPeople(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of People for query {}", query);
        Page<Person> page = personService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code SEARCH  /companies?query=:query} : search for the productionCompany corresponding to the query.
     *
     * @param query
     *     the query of the productionCompany search.
     * @param pageable
     *     the pagination information.
     *
     * @return the result of the search.
     */
    @GetMapping({"/companies", "/cm"})
    public ResponseEntity<List<ProductionCompany>> searchProductionCompanies(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProductionCompanies for query {}", query);
        Page<ProductionCompany> page = productionCompanyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code SEARCH  /countries?query=:query} : search for the productionCountry corresponding to the query.
     *
     * @param query
     *     the query of the productionCountry search.
     * @param pageable
     *     the pagination information.
     *
     * @return the result of the search.
     */
    @GetMapping({"/countries", "/cn"})
    public ResponseEntity<List<ProductionCountry>> searchProductionCountries(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProductionCountries for query {}", query);
        Page<ProductionCountry> page = productionCountryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * POST  /admin/reindex -> Reindex all Elasticsearch documents
     */
    @Secured(AuthoritiesConstants.ADMIN)
    @PostMapping("/admin/reindex")
    public ResponseEntity<Void> reindexAll() {
        log.info("REST request to reindex Elasticsearch by user : {}", SecurityUtils.getCurrentUserLogin());
        elasticsearchIndexService.reindexAll();
        return ResponseEntity.accepted()
            .headers(HeaderUtil.createAlert("mono", "elasticsearch.reindex.accepted", ""))
            .build();
    }
}
