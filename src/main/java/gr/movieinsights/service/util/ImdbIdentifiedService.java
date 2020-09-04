package gr.movieinsights.service.util;

import gr.movieinsights.repository.util.ImdbIdentifiedRepository;
import gr.movieinsights.service.mapper.EntityMapper;

import java.util.List;

public interface ImdbIdentifiedService<E, D, R extends ImdbIdentifiedRepository<E, Long>, M extends EntityMapper<D, E>> extends IBaseService<E, D, R, M> {
    default List<String> getAllImdbIds() {
        return getRepository().getAllImdbIds();
    }
}
