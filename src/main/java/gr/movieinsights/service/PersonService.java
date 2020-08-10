package gr.movieinsights.service;

import gr.movieinsights.domain.Person;
import gr.movieinsights.repository.PersonRepository;
import gr.movieinsights.repository.search.PersonSearchRepository;
import gr.movieinsights.service.dto.PersonDTO;
import gr.movieinsights.service.mapper.PersonMapper;
import gr.movieinsights.service.util.ImdbIdentifiedBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Person}.
 */
@Service
@Transactional
public class PersonService extends ImdbIdentifiedBaseService<Person, PersonDTO, PersonRepository, PersonMapper> {

    private final Logger log = LoggerFactory.getLogger(PersonService.class);

    private final PersonSearchRepository personSearchRepository;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper, PersonSearchRepository personSearchRepository) {
        super(personRepository, personMapper);
        this.personSearchRepository = personSearchRepository;
    }

    /**
     * Save a person.
     *
     * @param personDTO the entity to save.
     * @return the persisted entity.
     */
    public PersonDTO save(PersonDTO personDTO) {
        log.debug("Request to save Person : {}", personDTO);
        Person person = mapper.toEntity(personDTO);
        person = repository.save(person);
        PersonDTO result = mapper.toDto(person);
        personSearchRepository.save(person);
        return result;
    }

    /**
     * Get all the people.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all People");
        return repository.findAll(pageable)
            .map(mapper::toDto);
    }


    /**
     * Get one person by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PersonDTO> findOne(Long id) {
        log.debug("Request to get Person : {}", id);
        return repository.findById(id)
            .map(mapper::toDto);
    }

    /**
     * Delete the person by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Person : {}", id);
        repository.deleteById(id);
        personSearchRepository.deleteById(id);
    }

    /**
     * Search for the person corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of People for query {}", query);
        return personSearchRepository.search(queryStringQuery(query), pageable)
            .map(mapper::toDto);
    }
}
