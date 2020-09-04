package gr.movieinsights.service.util;

import gr.movieinsights.repository.util.TmdbIdentifiedRepository;
import gr.movieinsights.service.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;

public interface TmdbIdentifiedService<E, D, R extends TmdbIdentifiedRepository<E, Long>, M extends EntityMapper<D, E>> extends IBaseService<E, D, R, M> {

    default List<Long> getAllTmdbIds() {
        return getRepository().getAllTmdbIds();
    }

    default void deleteByTmdbId(Long id) {
        getRepository().deleteByTmdbId(id);
    }

    default Optional<D> findByTmdbId(Long id) {
        return getRepository().findByTmdbId(id)
            .map(getMapper()::toDto);
    }
}

