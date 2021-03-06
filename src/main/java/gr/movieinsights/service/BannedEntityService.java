package gr.movieinsights.service;

import gr.movieinsights.domain.BannedEntity;
import gr.movieinsights.repository.BannedEntityRepository;
import gr.movieinsights.service.dto.BannedEntityDTO;
import gr.movieinsights.service.mapper.BannedEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BannedEntity}.
 */
@Service
@Transactional
public class BannedEntityService {

    private final Logger log = LoggerFactory.getLogger(BannedEntityService.class);

    private final BannedEntityRepository bannedEntityRepository;

    private final BannedEntityMapper bannedEntityMapper;


    public BannedEntityService(BannedEntityRepository bannedEntityRepository, BannedEntityMapper bannedEntityMapper) {
        this.bannedEntityRepository = bannedEntityRepository;
        this.bannedEntityMapper = bannedEntityMapper;
    }

    /**
     * Save a bannedEntity.
     *
     * @param bannedEntityDTO the entity to save.
     * @return the persisted entity.
     */
    public BannedEntityDTO save(BannedEntityDTO bannedEntityDTO) {
        log.debug("Request to save BannedEntity : {}", bannedEntityDTO);
        BannedEntity bannedEntity = bannedEntityMapper.toEntity(bannedEntityDTO);
        bannedEntity = bannedEntityRepository.save(bannedEntity);
        BannedEntityDTO result = bannedEntityMapper.toDto(bannedEntity);
        return result;
    }

    /**
     * Get all the bannedEntities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BannedEntityDTO> findAll() {
        log.debug("Request to get all BannedEntities");
        return bannedEntityRepository.findAll().stream()
            .map(bannedEntityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one bannedEntity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BannedEntityDTO> findOne(Long id) {
        log.debug("Request to get BannedEntity : {}", id);
        return bannedEntityRepository.findById(id)
            .map(bannedEntityMapper::toDto);
    }

    /**
     * Delete the bannedEntity by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BannedEntity : {}", id);
        bannedEntityRepository.deleteById(id);
    }

}
