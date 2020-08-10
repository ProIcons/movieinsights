package gr.movieinsights.service.util;

import gr.movieinsights.repository.util.TmdbIdentifiedRepository;
import gr.movieinsights.service.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;

public class TmdbIdentifiedBaseService<E, D, R extends TmdbIdentifiedRepository<E, Long>, M extends EntityMapper<D, E>> extends BaseService<E, Long, D, R, M> {

    public TmdbIdentifiedBaseService(R repository, M mapper) {
        super(repository,mapper);
    }

    public List<Long> getAllTmdbIds() {
        return repository.getAllTmdbIds();
    }


    public void deleteByTmdbId(Long id) {
        repository.deleteByTmdbId(id);
    }

    public Optional<D> findByTmdbId(Long id) {
        return repository.findByTmdbId(id)
            .map(mapper::toDto);
    }
}

