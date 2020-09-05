package gr.movieinsights.service.util;

import gr.movieinsights.domain.IdentifiedEntity;
import gr.movieinsights.repository.util.BaseMovieInsightsRepository;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public abstract class BaseMovieInsightsService<TEntity extends IdentifiedEntity, TDTO, TBasicDTO, TRepository extends BaseMovieInsightsRepository<TEntity, Long>, TMapper extends EntityMapper<TDTO, TEntity>, TBasicMapper extends EntityMapper<TBasicDTO, TEntity>>
    extends BaseService<TEntity, TDTO, TRepository, TMapper>
    implements IBasicDataProviderService<TEntity, TDTO, TBasicDTO, TRepository, TMapper, TBasicMapper> {

    public BaseMovieInsightsService(TRepository repository, TMapper mapper) {
        super(repository, mapper);
        this.yearMap = new HashMap<>();
    }

    private final Object constantYearMapKey = new Object();

    protected final HashMap<Object, List<Integer>> yearMap;

    protected List<Integer> getYears(@Nullable Long id) {
        List<Integer> years;
        Object _id = id == null ? constantYearMapKey : id;
        synchronized (yearMap) {
            if (!yearMap.containsKey(_id)) {
                yearMap.put(_id, (years = _id == constantYearMapKey ? repository.getYears() : repository.getYears((Long) _id)));
            } else {
                years = yearMap.get(_id);
            }
        }
        return years;
    }


    @Override
    public void clear() {
        super.clear();
        synchronized (yearMap) {
            yearMap.clear();
        }
    }

    public Optional<MovieInsightsPerYearDTO> findByYear(TEntity entity, int year) {
        return findByYear(entity.getId(), year);
    }

    public abstract Optional<MovieInsightsPerYearDTO> findByYear(long id, int year);
}
