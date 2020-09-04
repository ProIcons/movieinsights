package gr.movieinsights.repository.util;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.util.Streamable;

public interface BaseSearchRepository<E,K> extends ElasticsearchRepository<E,K> {
    @Override
    Streamable<E> search(QueryBuilder query);
}
