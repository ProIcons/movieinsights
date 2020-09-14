package gr.movieinsights.service;

import gr.movieinsights.domain.Person;
import gr.movieinsights.models.SearchableEntityMovieCountMap;
import gr.movieinsights.repository.PersonRepository;
import gr.movieinsights.repository.search.PersonSearchRepository;
import gr.movieinsights.service.dto.person.BasicPersonDTO;
import gr.movieinsights.service.dto.person.PersonDTO;
import gr.movieinsights.service.mapper.person.BasicPersonMapper;
import gr.movieinsights.service.mapper.person.PersonMapper;
import gr.movieinsights.service.util.BaseSearchableService;
import gr.movieinsights.service.util.IBasicDataProviderService;
import gr.movieinsights.service.util.ImdbIdentifiedService;
import gr.movieinsights.service.util.QueryConfiguration;
import gr.movieinsights.service.util.TmdbIdentifiedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Person}.
 */
@Service
@Transactional
public class PersonService
    extends BaseSearchableService<Person, gr.movieinsights.domain.elasticsearch.Person, PersonDTO, BasicPersonDTO, PersonRepository, PersonSearchRepository, PersonMapper, BasicPersonMapper> implements
    IBasicDataProviderService<Person, PersonDTO, BasicPersonDTO, PersonRepository, PersonMapper, BasicPersonMapper>,
    TmdbIdentifiedService<Person, PersonDTO, PersonRepository, PersonMapper>,
    ImdbIdentifiedService<Person, PersonDTO, PersonRepository, PersonMapper> {

    private final BasicPersonMapper basicPersonMapper;

    public PersonService(PersonRepository personRepository, PersonSearchRepository personSearchRepository, PersonMapper personMapper, BasicPersonMapper basicPersonMapper, SearchableEntityMovieCountMap searchableEntityMovieCountMap) {
        super(personRepository, personSearchRepository, personMapper, basicPersonMapper, searchableEntityMovieCountMap);
        this.basicPersonMapper = basicPersonMapper;
    }

    @Override
    public BasicPersonMapper getBasicMapper() {
        return basicPersonMapper;
    }

    /**
     * Get all the Persons with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PersonDTO> findAllWithEagerRelationships(Pageable pageable) {
        return repository.findAllWithEagerRelationships(pageable).map(mapper::toDto);
    }

    /**
     * Get all the Persons with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<BasicPersonDTO> findAllWithEagerRelationshipsBasic(Pageable pageable) {
        return repository.findAllWithEagerRelationships(pageable).map(basicPersonMapper::toDto);
    }

    @Override
    protected QueryConfiguration queryConfiguration() {
        QueryConfiguration q = QueryConfiguration.CreateDefault();
        q.setBoost(0.8f);
        return q;
    }
}
