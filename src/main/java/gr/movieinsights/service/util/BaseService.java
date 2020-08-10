package gr.movieinsights.service.util;

import gr.movieinsights.repository.util.BaseRepository;
import gr.movieinsights.service.mapper.EntityMapper;

public abstract class BaseService<E, K, D, R extends BaseRepository<E, K>, M extends EntityMapper<D, E>> {
    protected final R repository;
    protected final M mapper;

    public BaseService(R repository, M mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Clears table by truncating it, cascading it in the process if needed.
     */
    public void clear() {
        this.repository.clear();
    }
}
