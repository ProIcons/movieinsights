package gr.movieinsights.service.util;

import gr.movieinsights.domain.IdentifiedEntity;
import gr.movieinsights.repository.util.BaseMovieInsightsRepository;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import gr.movieinsights.service.mapper.EntityMapper;

import java.util.Optional;

public abstract class BaseMovieInsightsService<TEntity extends IdentifiedEntity, TDTO, TBasicDTO, TRepository extends BaseMovieInsightsRepository<TEntity, Long>, TMapper extends EntityMapper<TDTO, TEntity>, TBasicMapper extends EntityMapper<TBasicDTO, TEntity>>
    extends BaseService<TEntity, TDTO, TRepository, TMapper>
    implements IBasicDataProviderService<TEntity, TDTO, TBasicDTO, TRepository, TMapper, TBasicMapper> {

    public BaseMovieInsightsService(TRepository repository, TMapper mapper) {
        super(repository, mapper);
    }

    public Optional<MovieInsightsPerYearDTO> findByYear(TEntity entity, int year) {
        return findByYear(entity.getId(), year);
    }

    public abstract Optional<MovieInsightsPerYearDTO> findByYear(long id, int year);
}
