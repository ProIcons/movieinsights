package gr.movieinsights.service.util;

import gr.movieinsights.repository.util.BaseRepository;
import gr.movieinsights.service.mapper.EntityMapper;

import java.util.Optional;

public interface IBaseService<TEntity, TDTO, TRepository extends BaseRepository<TEntity, Long>, TMapper extends EntityMapper<TDTO, TEntity>> {
    TRepository getRepository();
    TMapper getMapper();
    Optional<TDTO> findOne(Long id);
    Optional<TDTO> findFirst();
    TEntity save(TEntity entity);
    void deleteById(Long id);
    void clear();

}
