package gr.movieinsights.service.util;

import gr.movieinsights.domain.IdentifiedEntity;
import gr.movieinsights.models.SearchableEntityMovieCountMap;
import gr.movieinsights.repository.util.BaseRepository;
import gr.movieinsights.repository.util.BaseSearchRepository;
import gr.movieinsights.repository.util.BaseSearchableEntityNonSearchableRepository;
import gr.movieinsights.service.SearchService;
import gr.movieinsights.service.mapper.EntityMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.query.functionscore.ScriptScoreFunctionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.util.Streamable;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseSearchableService<TEntity extends IdentifiedEntity, TESEntity, TDTO, TBasicDTO, TRepository extends BaseRepository<TEntity, Long> & BaseSearchableEntityNonSearchableRepository<TEntity, TESEntity, Long>, TSearchRepository extends BaseSearchRepository<TESEntity, Long>, TMapper extends EntityMapper<TDTO, TEntity>, TBasicMapper extends EntityMapper<TBasicDTO, TEntity>> extends BaseService<TEntity, TDTO, TRepository, TMapper> implements IBasicDataProviderService<TEntity, TDTO, TBasicDTO, TRepository, TMapper, TBasicMapper> {
    private final TSearchRepository searchRepository;
    private final TBasicMapper basicMapper;
    private final SearchableEntityMovieCountMap searchableEntityMovieCountMap;
    private final Class<TEntity> entityClass;
    private final Class<TESEntity> esEntityClass;
    private final String indexName;
    private final String typeName;
    private final QueryConfiguration queryConfiguration;

    @Autowired
    private SearchService searchService = null;


    public BaseSearchableService(TRepository repository, TSearchRepository searchRepository, TMapper mapper, TBasicMapper basicMapper, SearchableEntityMovieCountMap searchableEntityMovieCountMap) {
        super(repository, mapper);
        this.searchRepository = searchRepository;
        this.basicMapper = basicMapper;
        ResolvableType resolvableType = ResolvableType.forClass(getClass()).as(BaseSearchableService.class);
        entityClass = (Class<TEntity>) resolvableType.getGenerics()[0].getType();
        esEntityClass = (Class<TESEntity>) resolvableType.getGenerics()[1].getType();
        //searchableEntityMovieCountMap.register(entityClass, repository::findAllWithMovieCounts);
        this.searchableEntityMovieCountMap = searchableEntityMovieCountMap;



        if (esEntityClass.getAnnotation(Document.class) != null) {
            indexName = typeName = esEntityClass.getAnnotation(Document.class).indexName();
        } else {
            throw new UnsupportedOperationException(getClass().getSimpleName() + " cannot register " + esEntityClass.getCanonicalName() + " as a searchable entity because " + Document.class.getCanonicalName() + " annotation is missing from it's declaration");
        }

        queryConfiguration = queryConfiguration();

    }

    @PostConstruct
    public void init() {
        searchService.register(esEntityClass, this);
    }

    @Override
    public TBasicMapper getBasicMapper() {
        return basicMapper;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getTypeName() {
        return typeName;
    }

    /**
     * @return the SearchRepository
     */
    public TSearchRepository getSearchRepository() {
        return searchRepository;
    }

    /**
     * Get all the entities.
     *
     * @return the page of entities.
     */
    @Transactional(readOnly = true)
    public Page<TDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Movies");
        return repository.findAll(pageable)
            .map(mapper::toDto);

    }

    /**
     * Clears table by truncating it, cascading it in the process if needed.
     */
    @Override
    public void clear() {
        super.clear();
        searchRepository.deleteAll();
    }

    /**
     * Delete the entity by id.
     *
     * @param id
     *     the id of the entity.
     */
    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
        searchRepository.deleteById(id);
    }


    /**
     * Save an entity.
     *
     * @param entity
     *     the entity to save.
     *
     * @return the persisted entity.
     */
    @Override
    public TEntity save(TEntity entity) {
        entity = super.save(entity);
        //searchRepository.save(entity);
        return entity;
    }

    /**
     * Save an entity.
     *
     * @param entityDTO
     *     the entityDTO to save.
     *
     * @return the persisted entity.
     */
    @Override
    public TDTO saveDTO(TDTO entityDTO) {
        entityDTO = super.saveDTO(entityDTO);
        //searchRepository.save(mapper.toEntity(entityDTO));
        return entityDTO;
    }


    public QueryBuilder getQuery(String query) {
        ScriptScoreFunctionBuilder scoreQuery = ScoreFunctionBuilders
            .scriptFunction(queryConfiguration.getFunctionScript())
            .setWeight(queryConfiguration.getFunctionWeight());

        QueryBuilder indexMatchQuery = QueryBuilders
            .matchQuery("name", query)
            .analyzer("autocomplete_index")
            .operator(queryConfiguration.getIndexOperator())
            .fuzziness(queryConfiguration.getIndexFuzziness())
            .boost(queryConfiguration.getIndexBoost());

        QueryBuilder searchMachQuery = QueryBuilders
            .matchQuery("name", query)
            .analyzer("autocomplete_search")
            .operator(queryConfiguration.getSearchOperator())
            .fuzziness(queryConfiguration.getSearchFuzziness())
            .boost(queryConfiguration.getSearchBoost());

        BoolQueryBuilder mainQuery = QueryBuilders
            .boolQuery()
            .should(indexMatchQuery)
            .should(searchMachQuery);

        QueryBuilder parentQuery = QueryBuilders
            .functionScoreQuery(mainQuery, scoreQuery)
            .boostMode(queryConfiguration.getFunctionBoostMode())
            .setMinScore(queryConfiguration.getMinimumScore())
            .boost(queryConfiguration.getBoost());

        return parentQuery;
    }


    /**
     * Search for the entity corresponding to the query.
     *
     * @param query
     *     the query of the search.
     * @param pageable
     *     the pagination information.
     *
     * @return the page of entities.
     */
    @Transactional(readOnly = true)
    public Page<TESEntity> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
            .withFields("id", "name", "popularity", "score", "logoPath", "profilePath")
            .withIndices(indexName)
            .withQuery(getQuery(query))
            .build();

        Streamable<TESEntity> rawSearchResult = searchRepository.search(nativeSearchQuery);
        long count = rawSearchResult.stream().count();
        log.debug("Request to search for a page of entities for query {}", query);
        List<TESEntity> limitedSearchResult = rawSearchResult
            .stream()
            .skip(pageable.getOffset())
            .limit(pageable.getPageSize())
            .collect(Collectors.toList());


        return new PageImpl<>(limitedSearchResult, pageable, count);
    }


    protected QueryConfiguration queryConfiguration() {
        return QueryConfiguration.CreateDefault();
    }

}
