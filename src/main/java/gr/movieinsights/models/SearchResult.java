package gr.movieinsights.models;

import gr.movieinsights.domain.elasticsearch.Genre;
import gr.movieinsights.domain.elasticsearch.Person;
import gr.movieinsights.domain.elasticsearch.ProductionCompany;
import org.springframework.data.domain.Page;

public class SearchResult {
    public static SearchResult of(Page<Genre> gn, Page<ProductionCompany> cm, Page<Person> p) {
        return new SearchResult(gn, cm, p);
    }

    private final PageResult<Genre> gn;
    private final PageResult<ProductionCompany> cm;
    private final PageResult<Person> p;

    public PageResult<Genre> getGn() {
        return gn;
    }

    public PageResult<ProductionCompany> getCm() {
        return cm;
    }

    public PageResult<Person> getP() {
        return p;
    }

    public SearchResult(Page<Genre> gn, Page<ProductionCompany> cm, Page<Person> p) {
        this.gn = new PageResult<>(gn.getContent(), gn.getPageable().getOffset(), gn.getPageable().getPageSize(), gn.getTotalElements());
        this.cm = new PageResult<>(cm.getContent(), cm.getPageable().getOffset(), cm.getPageable().getPageSize(), cm.getTotalElements());
        this.p = new PageResult<>(p.getContent(), p.getPageable().getOffset(), p.getPageable().getPageSize(), p.getTotalElements());
    }
}
