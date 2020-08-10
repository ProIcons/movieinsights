package gr.movieinsights.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;
import gr.movieinsights.domain.Genre;
import gr.movieinsights.domain.Person;
import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.domain.User;
import gr.movieinsights.repository.GenreRepository;
import gr.movieinsights.repository.MovieRepository;
import gr.movieinsights.repository.PersonRepository;
import gr.movieinsights.repository.ProductionCompanyRepository;
import gr.movieinsights.repository.ProductionCountryRepository;
import gr.movieinsights.repository.UserRepository;
import gr.movieinsights.repository.search.GenreSearchRepository;
import gr.movieinsights.repository.search.MovieSearchRepository;
import gr.movieinsights.repository.search.PersonSearchRepository;
import gr.movieinsights.repository.search.ProductionCompanySearchRepository;
import gr.movieinsights.repository.search.ProductionCountrySearchRepository;
import gr.movieinsights.repository.search.UserSearchRepository;
import org.elasticsearch.ResourceAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
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
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ElasticsearchIndexService {

    private static final Lock reindexLock = new ReentrantLock();

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    private final GenreRepository genreRepository;

    private final GenreSearchRepository genreSearchRepository;

    private final MovieRepository movieRepository;

    private final MovieSearchRepository movieSearchRepository;

    private final PersonRepository personRepository;

    private final PersonSearchRepository personSearchRepository;

    private final ProductionCompanyRepository productionCompanyRepository;

    private final ProductionCompanySearchRepository productionCompanySearchRepository;

    private final ProductionCountryRepository productionCountryRepository;

    private final ProductionCountrySearchRepository productionCountrySearchRepository;

    private final UserRepository userRepository;

    private final UserSearchRepository userSearchRepository;

    private final JestElasticsearchTemplate jestElasticsearchTemplate;

    public ElasticsearchIndexService(GenreRepository genreRepository, GenreSearchRepository genreSearchRepository, MovieRepository movieRepository, MovieSearchRepository movieSearchRepository, PersonRepository personRepository, PersonSearchRepository personSearchRepository, ProductionCompanyRepository productionCompanyRepository, ProductionCompanySearchRepository productionCompanySearchRepository, ProductionCountryRepository productionCountryRepository, ProductionCountrySearchRepository productionCountrySearchRepository, UserRepository userRepository, UserSearchRepository userSearchRepository, JestElasticsearchTemplate jestElasticsearchTemplate) {
        this.genreRepository = genreRepository;
        this.genreSearchRepository = genreSearchRepository;
        this.movieRepository = movieRepository;
        this.movieSearchRepository = movieSearchRepository;
        this.personRepository = personRepository;
        this.personSearchRepository = personSearchRepository;
        this.productionCompanyRepository = productionCompanyRepository;
        this.productionCompanySearchRepository = productionCompanySearchRepository;
        this.productionCountryRepository = productionCountryRepository;
        this.productionCountrySearchRepository = productionCountrySearchRepository;
        this.userRepository = userRepository;
        this.userSearchRepository = userSearchRepository;
        this.jestElasticsearchTemplate = jestElasticsearchTemplate;
    }

    @Async
    public void reindexAll() {
        if (reindexLock.tryLock()) {
            try {
                reindexForClass(User.class, userRepository, userSearchRepository);
                reindexForClass(Genre.class, genreRepository, genreSearchRepository);
                reindexForClass(Person.class, personRepository, personSearchRepository);
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

    private <T, ID extends Serializable> void reindexForClass(Class<T> entityClass, JpaRepository<T, ID> jpaRepository,
                                                              ElasticsearchRepository<T, ID> elasticsearchRepository) {
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
                        return new PropertyDescriptor(field.getName(), entityClass).getReadMethod();
                    } catch (IntrospectionException e) {
                        log.error("Error retrieving getter for class {}, field {}. Field will NOT be indexed",
                            entityClass.getSimpleName(), field.getName(), e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

            int size = 100;
            for (int i = 0; i <= jpaRepository.count() / size; i++) {
                Pageable page = PageRequest.of(i, size);
                log.info("Indexing page {} of {}, size {}", i, jpaRepository.count() / size, size);
                Page<T> results = jpaRepository.findAll(page);
                results.map(result -> {
                    // if there are any relationships to load, do it now
                    relationshipGetters.forEach(method -> {
                        try {
                            // eagerly load the relationship set
                            ((Set) method.invoke(result)).size();
                        } catch (Exception ex) {
                            log.error(ex.getMessage());
                        }
                    });
                    return result;
                });
                elasticsearchRepository.saveAll(results.getContent());
            }
        }
        log.info("Elasticsearch: Indexed all rows for {}", entityClass.getSimpleName());
    }
}
