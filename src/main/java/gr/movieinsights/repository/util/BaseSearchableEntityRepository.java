package gr.movieinsights.repository.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseSearchableEntityRepository<TEntity,TESEntity,TKey> extends BaseRepository<TEntity,TKey> {
    List<TEntity> findAllWithEagerRelationships();
    Page<TEntity> findAllWithEagerRelationships(Pageable pageable);

    List<TESEntity> findAllEntitiesAsElasticSearchIndices();
    List<TESEntity> findOneAsElasticSearchIndex(Long id);
}
