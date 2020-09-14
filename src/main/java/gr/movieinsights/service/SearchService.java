package gr.movieinsights.service;

import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;
import com.github.vanroy.springdata.jest.mapper.JestResultsExtractor;
import com.google.gson.JsonObject;
import gr.movieinsights.domain.IdentifiedEntity;
import gr.movieinsights.domain.enumeration.TmdbEntityType;
import gr.movieinsights.models.AutoCompleteResult;
import gr.movieinsights.service.util.BaseSearchableService;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Map<Class<?>, BaseSearchableService<?, ?, ?, ?, ?, ?, ?, ?>> searchableServiceMap;
    private final Map<String, Class<?>> indexMap;

    private final org.springframework.data.elasticsearch.core.EntityMapper entityMapper;
    private final JestElasticsearchTemplate jestElasticsearchTemplate;

    public SearchService(JestElasticsearchTemplate jestElasticsearchTemplate, org.springframework.data.elasticsearch.core.EntityMapper entityMapper) {
        this.jestElasticsearchTemplate = jestElasticsearchTemplate;
        this.entityMapper = entityMapper;
        this.indexMap = new HashMap<>();
        this.searchableServiceMap = new HashMap<>();
    }

    public void register(Class<?> entityClass, BaseSearchableService<?, ?, ?, ?, ?, ?, ?, ?> service) {
        searchableServiceMap.put(entityClass, service);
        indexMap.put(service.getIndexName(), entityClass);
    }

    public AutoCompleteResult autocomplete(String query) {
        return autocomplete(query, false);
    }

    public AutoCompleteResult autocomplete(String query, Boolean extended) {

        List<String> indices = new ArrayList<>();

        BoolQueryBuilder parentQueryBuilder = QueryBuilders.boolQuery();

        for (Map.Entry<Class<?>, BaseSearchableService<?, ?, ?, ?, ?, ?, ?, ?>> entry : searchableServiceMap.entrySet()) {
            indices.add(entry.getValue().getIndexName());
            QueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("_index", entry.getValue().getIndexName());
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder
                .must(entry.getValue().getQuery(query))
                .filter(termsQueryBuilder);
            parentQueryBuilder.should(boolQueryBuilder);
            try {
                entry.getKey().getDeclaredField("popularity");
            } catch (NoSuchFieldException ignored) {
            }
        }

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
            .withFields("id", "name", "popularity", "score", "logoPath", "profilePath")
            .withIndices(indices.toArray(String[]::new))
            .withPageable(PageRequest.of(0, (indices.size() + 1) * 2))
            .withQuery(parentQueryBuilder)
            .build();


        //SearchRequest searchRequest = new SearchRequest(indices.toArray(String[]::new));
     /*   SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(parentQueryBuilder);
        searchRequest.source(searchSourceBuilder)*/
        ;
       /* log.debug("SR  -> {}", searchRequest);
        log.debug("SRB -> {}", searchSourceBuilder);*/

        return jestElasticsearchTemplate.query(nativeSearchQuery, (JestResultsExtractor<AutoCompleteResult>) searchResult -> {
            AutoCompleteResult.Builder builder = new AutoCompleteResult.Builder();
            Map<String, AutoCompleteResult.EntityResult.Builder> builderMap = new HashMap<>();
            List<? extends SearchResult.Hit<?, Void>> hits = searchResult.getHits(JsonObject.class);
            if (hits.size() > 0) {
                for (SearchResult.Hit<?, Void> hit : hits) {
                    AutoCompleteResult.EntityResult.Builder resultBuilder = AutoCompleteResult.EntityResult.Builder.of(typeFromIndex(hit.index));
                    AutoCompleteResult.EntityResult.Builder _resultBuilder;
                    if ((_resultBuilder = builderMap.putIfAbsent(hit.index, resultBuilder)) != null) {
                        resultBuilder = _resultBuilder;
                    }
                    try {
                        Object entity = entityMapper.mapToObject(hit.source.toString(), indexMap.get(hit.index));
                        if (!extended) {
                            try {
                                Field field = indexMap.get(hit.index).getSuperclass().getDeclaredField("score");
                                field.setAccessible(true);
                                field.set(entity,null);
                                field.setAccessible(false);
                            } catch (NoSuchFieldException | IllegalAccessException e) {
                                e.printStackTrace();
                            }

                            resultBuilder.add(entity);
                        } else
                            resultBuilder.add(new AutoCompleteResult.Entity(hit.score, entity));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                builderMap.values().forEach(builder::add);
            }
            return builder.build();
        });
    }

    private TmdbEntityType typeFromIndex(String index) {
        return switch (index) {
            case "person" -> TmdbEntityType.PERSON;
            case "company" -> TmdbEntityType.COMPANY;
            case "genre" -> TmdbEntityType.GENRE;
            default -> null;
        };
    }

    private BaseSearchableService<?, ?, ?, ?, ?, ?, ?, ?> getService(Class<?> entityClass) {
        return searchableServiceMap.getOrDefault(entityClass, null);
    }

    public <T extends IdentifiedEntity, D> Page<D> search(Class<T> entityClass, String query, Pageable pageable) {
        return (Page<D>) getService(entityClass).search(query, pageable);
    }
}
