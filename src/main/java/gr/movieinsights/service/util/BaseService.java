package gr.movieinsights.service.util;

import gr.movieinsights.repository.util.BaseRepository;
import gr.movieinsights.service.mapper.EntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseService<TEntity, TDTO, TRepository extends BaseRepository<TEntity, Long>, TMapper extends EntityMapper<TDTO, TEntity>> implements IBaseService<TEntity, TDTO, TRepository, TMapper> {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final TRepository repository;
    protected final TMapper mapper;

    private final String ENTITY_NAME;

    protected Logger getLogger() {
        return log;
    }

    public BaseService(TRepository repository, TMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        ENTITY_NAME = getClass().getSimpleName().replace("Service", "");
    }

    public TRepository getRepository() {
        return repository;
    }

    public TMapper getMapper() {
        return mapper;
    }

    /**
     * Clears table by truncating it, cascading it in the process if needed.
     */
    public void clear() {
        log.debug("Request to clear {}", ENTITY_NAME);
        this.repository.clear();
    }


    /**
     * Get all the entities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TDTO> findAll() {
        log.debug("Request to get all {}", ENTITY_NAME);
        return repository.findAll().stream()
            .map(mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Save an entity.
     *
     * @param entity
     *     the entity to save.
     *
     * @return the persisted entity.
     */
    public TEntity save(TEntity entity) {
        log.debug("Request to save {} : {}", ENTITY_NAME, entity);
        entity = repository.save(entity);
        return entity;
    }

    /**
     * Save an entity.
     *
     * @param entityDTO
     *     the entityDTO to save.
     *
     * @return the persisted entity.
     */
    public TDTO saveDTO(TDTO entityDTO) {
        log.debug("Request to save {} : {}", ENTITY_NAME, entityDTO);
        TEntity entity = mapper.toEntity(entityDTO);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }


    /**
     * Get one entity by id.
     *
     * @param id
     *     the id of the entity.
     *
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TDTO> findOne(Long id) {
        log.debug("Request to get {} : {}", ENTITY_NAME, id);
        return repository.findById(id)
            .map(mapper::toDto);
    }

    /**
     * Get first entity.
     *
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TDTO> findFirst() {
        log.debug("Request to get {} : first", ENTITY_NAME);
        return repository.findFirst()
            .map(mapper::toDto);
    }

    /**
     * Delete the entity by id.
     *
     * @param id
     *     the id of the entity.
     */
    public void deleteById(Long id) {
        log.debug("Request to delete {} : {}", ENTITY_NAME, id);
        repository.deleteById(id);
    }
}
