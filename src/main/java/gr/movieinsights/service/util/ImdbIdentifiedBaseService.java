package gr.movieinsights.service.util;

import gr.movieinsights.repository.util.ImdbIdentifiedRepository;
import gr.movieinsights.repository.util.TmdbIdentifiedRepository;
import gr.movieinsights.service.mapper.EntityMapper;

import java.util.List;

public class ImdbIdentifiedBaseService<E, D, R extends ImdbIdentifiedRepository<E, Long> & TmdbIdentifiedRepository<E, Long>, M extends EntityMapper<D, E>> extends TmdbIdentifiedBaseService<E, D, R, M> {

    public ImdbIdentifiedBaseService(R repository, M mapper) {
        super(repository, mapper);
    }

    public List<String> getAllImdbIds() {
        return repository.getAllImdbIds();
    }
}
