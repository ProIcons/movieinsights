package gr.movieinsights.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;
import gr.movieinsights.domain.elasticsearch.Genre;
import gr.movieinsights.domain.elasticsearch.Person;
import gr.movieinsights.domain.elasticsearch.ProductionCompany;
import gr.movieinsights.domain.elasticsearch.ProductionCountry;
import gr.movieinsights.repository.CreditRepository;
import gr.movieinsights.repository.GenreRepository;
import gr.movieinsights.repository.MovieRepository;
import gr.movieinsights.repository.PersonRepository;
import gr.movieinsights.repository.ProductionCompanyRepository;
import gr.movieinsights.repository.ProductionCountryRepository;
import gr.movieinsights.repository.UserRepository;
import gr.movieinsights.repository.search.CreditSearchRepository;
import gr.movieinsights.repository.search.GenreSearchRepository;
import gr.movieinsights.repository.search.MovieSearchRepository;
import gr.movieinsights.repository.search.PersonSearchRepository;
import gr.movieinsights.repository.search.ProductionCompanySearchRepository;
import gr.movieinsights.repository.search.ProductionCountrySearchRepository;
import gr.movieinsights.repository.search.UserSearchRepository;
import gr.movieinsights.repository.util.BaseSearchableEntityRepository;
import org.elasticsearch.ResourceAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.ManyToMany;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ElasticsearchIndexService {

    private static final Lock reindexLock = new ReentrantLock();

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    private final GenreRepository genreRepository;
    private final CreditRepository creditRepository;
    private final MovieRepository movieRepository;


    private final GenreSearchRepository genreSearchRepository;

    private final PersonRepository personRepository;

    private final PersonSearchRepository personSearchRepository;
    private final CreditSearchRepository creditSearchRepository;
    private final MovieSearchRepository movieSearchRepository;

    private final ProductionCompanyRepository productionCompanyRepository;

    private final ProductionCompanySearchRepository productionCompanySearchRepository;

    private final ProductionCountryRepository productionCountryRepository;

    private final ProductionCountrySearchRepository productionCountrySearchRepository;

    private final UserRepository userRepository;

    private final UserSearchRepository userSearchRepository;

    private final JestElasticsearchTemplate jestElasticsearchTemplate;

    public ElasticsearchIndexService(GenreRepository genreRepository, CreditRepository creditRepository, MovieRepository movieRepository, GenreSearchRepository genreSearchRepository, PersonRepository personRepository, PersonSearchRepository personSearchRepository, CreditSearchRepository creditSearchRepository, MovieSearchRepository movieSearchRepository, ProductionCompanyRepository productionCompanyRepository, ProductionCompanySearchRepository productionCompanySearchRepository, ProductionCountryRepository productionCountryRepository, ProductionCountrySearchRepository productionCountrySearchRepository, UserRepository userRepository, UserSearchRepository userSearchRepository, JestElasticsearchTemplate jestElasticsearchTemplate) {
        this.genreRepository = genreRepository;
        this.creditRepository = creditRepository;
        this.movieRepository = movieRepository;
        this.genreSearchRepository = genreSearchRepository;
        this.personRepository = personRepository;
        this.personSearchRepository = personSearchRepository;
        this.creditSearchRepository = creditSearchRepository;
        this.movieSearchRepository = movieSearchRepository;
        this.productionCompanyRepository = productionCompanyRepository;
        this.productionCompanySearchRepository = productionCompanySearchRepository;
        this.productionCountryRepository = productionCountryRepository;
        this.productionCountrySearchRepository = productionCountrySearchRepository;
        this.userRepository = userRepository;
        this.userSearchRepository = userSearchRepository;
        this.jestElasticsearchTemplate = jestElasticsearchTemplate;
    }


    @Async
    public void deleteAll() {
        jestElasticsearchTemplate.deleteIndex(Person.class);
        jestElasticsearchTemplate.deleteIndex(Genre.class);
        jestElasticsearchTemplate.deleteIndex(ProductionCountry.class);
        jestElasticsearchTemplate.deleteIndex(ProductionCompany.class);
    }

    @Async
    public void reindexAll() {
        if (reindexLock.tryLock()) {
            try {
                //reindexForClass(User.class, userRepository, userSearchRepository);

                reindexForClass(Person.class, personRepository, personSearchRepository);
                reindexForClass(Genre.class, genreRepository, genreSearchRepository);

                reindexForClass(ProductionCompany.class, productionCompanyRepository, productionCompanySearchRepository);
                reindexForClass(ProductionCountry.class, productionCountryRepository, productionCountrySearchRepository);
                log.info("Elasticsearch: Successfully performed reindexing");
            } finally {
                reindexLock.unlock();
            }
        } else {
            log.info("Elasticsearch: concurrent reindexing attempt");
        }
    }

    private <T,TES, ID extends Serializable> void reindexForClass(Class<TES> entityClass, BaseSearchableEntityRepository<T, TES, ID> jpaRepository,
                                                              ElasticsearchRepository<TES, ID> elasticsearchRepository) {
        reindexForClass(entityClass, jpaRepository, elasticsearchRepository, null, null);
    }

    private <T, TES, ID extends Serializable> void reindexForClass(Class<TES> entityClass, BaseSearchableEntityRepository<T, TES, ID> jpaRepository,
                                                              ElasticsearchRepository<TES, ID> elasticsearchRepository, Function<TES, TES> entityManipulator, Supplier<List<TES>> entitySupplier) {
        jestElasticsearchTemplate.deleteIndex(entityClass);
        try {
            jestElasticsearchTemplate.createIndex(entityClass);
        } catch (ResourceAlreadyExistsException e) {
            // Do nothing. Index was already concurrently recreated by some other service.
        }
        jestElasticsearchTemplate.putMapping(entityClass);
        if (jpaRepository.count() > 0) {
            // if a JHipster entity field is the owner side of a many-to-many relationship, it should be loaded manually
            List<Method> relationshipGetters = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.getType().equals(Set.class))
                .filter(field -> field.getAnnotation(ManyToMany.class) != null)
                .filter(field -> field.getAnnotation(ManyToMany.class).mappedBy().isEmpty())
                .filter(field -> field.getAnnotation(JsonIgnore.class) == null)
                .map(field -> {
                    try {
                        return new PropertyDescriptor((String) field.getName(), entityClass).getReadMethod();
                    } catch (IntrospectionException e) {
                        log.error("Error retrieving getter for class {}, field {}. Field will NOT be indexed",
                            entityClass.getSimpleName(), (String) field.getName(), e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

           /* List<T> results = jpaRepository.findAll();
            results.parallelStream().forEach(result -> {
                // if there are any relationships to load, do it now
                relationshipGetters.parallelStream().forEach(method -> {
                    try {
                        // eagerly load the relationship set
                        ((Set) method.invoke(result)).size();
                    } catch (Exception ex) {
                        log.error(ex.getMessage());
                    }
                });
            });
            if (results.size() > 0)
                elasticsearchRepository.saveAll(results);*/
            List<TES> _results = jpaRepository.findAllEntitiesAsElasticSearchIndices();
            int size = 5000;
            for (int i = 0; i <= _results.size() / size; i++) {
                Pageable page = PageRequest.of(i, size);
                log.info("Indexing {} page {} of {}, size {}", entityClass.getSimpleName(), i, _results.size() / size, size);
                Page<TES> results = new PageImpl<TES>(_results.stream().skip(page.getOffset()).limit(page.getPageSize()).collect(Collectors.toList()), page, _results.size());


                results.map(result -> {
                    // if there are any relationships to load, do it now
                    if (entityManipulator != null)
                        entityManipulator.apply(result);
                    relationshipGetters.parallelStream().forEach(method -> {
                        try {
                            // eagerly load the relationship set
                            ((Set) method.invoke(result)).size();
                        } catch (Exception ex) {
                            log.error(ex.getMessage());
                        }
                    });
                    return result;
                });
                List<TES> content = results.getContent();
                if (content.size() > 0)
                    elasticsearchRepository.saveAll(content);

            }
        }
        log.info("Elasticsearch: Indexed all rows for {}", entityClass.getSimpleName());
    }
}
