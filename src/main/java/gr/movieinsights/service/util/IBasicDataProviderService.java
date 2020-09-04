package gr.movieinsights.service.util;

import gr.movieinsights.repository.util.BaseRepository;
import gr.movieinsights.service.mapper.EntityMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface IBasicDataProviderService<
    TEntity,
    TDTO,
    TBasicDTO,
    TRepository extends BaseRepository<TEntity, Long>,
    TMapper extends EntityMapper<TDTO, TEntity>,
    TBasicMapper extends EntityMapper<TBasicDTO, TEntity>
    > extends IBaseService<TEntity, TDTO, TRepository, TMapper> {
    TBasicMapper getBasicMapper();

    /**
     * Get one entity's basic dto by id.
     *
     * @param id
     *     the id of the entity.
     *
     * @return the basic entity dto.
     */
    @Transactional(readOnly = true)
    default Optional<TBasicDTO> findOneBasic(Long id) {
        return getRepository().findById(id).map(getBasicMapper()::toDto);
    }

    /**
     * Get first entity.
     *
     * @return the entity.
     */
    @Transactional(readOnly = true)
    default Optional<TBasicDTO> findFirstBasic() {
        return getRepository().findFirst()
            .map(getBasicMapper()::toDto);
    }

    /**
     * Get all entities basic dto by id.
     *
     * @return all the basic entity dtos.
     */
    @Transactional(readOnly = true)
    default List<TBasicDTO> findAllBasic() {
        return getRepository().findAll().stream()
            .map(getBasicMapper()::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all entities basic dto by id.
     *
     * @return the page of basic entity dtos.
     */
    @Transactional(readOnly = true)
    default Page<TBasicDTO> findAllBasic(Pageable pageable) {
        return getRepository().findAll(pageable)
            .map(getBasicMapper()::toDto);
    }
}
