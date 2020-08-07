package gr.movieinsights.service;

import gr.movieinsights.domain.BannedPersistentEntity;
import gr.movieinsights.repository.BannedPersistentEntityRepository;
import gr.movieinsights.repository.search.BannedPersistentEntitySearchRepository;
import gr.movieinsights.service.dto.BannedPersistentEntityDTO;
import gr.movieinsights.service.mapper.BannedPersistentEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link BannedPersistentEntity}.
 */
@Service
@Transactional
public class BannedPersistentEntityService {

    private final Logger log = LoggerFactory.getLogger(BannedPersistentEntityService.class);

    private final BannedPersistentEntityRepository bannedPersistentEntityRepository;

    private final BannedPersistentEntityMapper bannedPersistentEntityMapper;

    private final BannedPersistentEntitySearchRepository bannedPersistentEntitySearchRepository;

    public BannedPersistentEntityService(BannedPersistentEntityRepository bannedPersistentEntityRepository, BannedPersistentEntityMapper bannedPersistentEntityMapper, BannedPersistentEntitySearchRepository bannedPersistentEntitySearchRepository) {
        this.bannedPersistentEntityRepository = bannedPersistentEntityRepository;
        this.bannedPersistentEntityMapper = bannedPersistentEntityMapper;
        this.bannedPersistentEntitySearchRepository = bannedPersistentEntitySearchRepository;
    }

    /**
     * Save a bannedPersistentEntity.
     *
     * @param bannedPersistentEntityDTO the entity to save.
     * @return the persisted entity.
     */
    public BannedPersistentEntityDTO save(BannedPersistentEntityDTO bannedPersistentEntityDTO) {
        log.debug("Request to save BannedPersistentEntity : {}", bannedPersistentEntityDTO);
        BannedPersistentEntity bannedPersistentEntity = bannedPersistentEntityMapper.toEntity(bannedPersistentEntityDTO);
        bannedPersistentEntity = bannedPersistentEntityRepository.save(bannedPersistentEntity);
        BannedPersistentEntityDTO result = bannedPersistentEntityMapper.toDto(bannedPersistentEntity);
        bannedPersistentEntitySearchRepository.save(bannedPersistentEntity);
        return result;
    }

    /**
     * Get all the bannedPersistentEntities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BannedPersistentEntityDTO> findAll() {
        log.debug("Request to get all BannedPersistentEntities");
        return bannedPersistentEntityRepository.findAll().stream()
            .map(bannedPersistentEntityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  Get all the bannedPersistentEntities where BannedEntity is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<BannedPersistentEntityDTO> findAllWhereBannedEntityIsNull() {
        log.debug("Request to get all bannedPersistentEntities where BannedEntity is null");
        return StreamSupport
            .stream(bannedPersistentEntityRepository.findAll().spliterator(), false)
            .filter(bannedPersistentEntity -> bannedPersistentEntity.getBannedEntity() == null)
            .map(bannedPersistentEntityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bannedPersistentEntity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BannedPersistentEntityDTO> findOne(Long id) {
        log.debug("Request to get BannedPersistentEntity : {}", id);
        return bannedPersistentEntityRepository.findById(id)
            .map(bannedPersistentEntityMapper::toDto);
    }

    /**
     * Delete the bannedPersistentEntity by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BannedPersistentEntity : {}", id);
        bannedPersistentEntityRepository.deleteById(id);
        bannedPersistentEntitySearchRepository.deleteById(id);
    }

    /**
     * Search for the bannedPersistentEntity corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BannedPersistentEntityDTO> search(String query) {
        log.debug("Request to search BannedPersistentEntities for query {}", query);
        return StreamSupport
            .stream(bannedPersistentEntitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(bannedPersistentEntityMapper::toDto)
        .collect(Collectors.toList());
    }
}
