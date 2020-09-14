package gr.movieinsights.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import gr.movieinsights.domain.enumeration.TmdbEntityType;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteResult {
    @JsonProperty("_")
    protected final List<EntityResult> results;

    public AutoCompleteResult(List<EntityResult> results) {
        this.results = results;
    }

    public List<EntityResult> getResults() {
        return results;
    }

    public static class Builder extends AutoCompleteResult {
        public Builder() {
            super(new ArrayList<>());
        }

        public Builder add(EntityResult result) {
            results.add(result);
            return this;
        }

        public AutoCompleteResult build() {
            return new AutoCompleteResult(results);
        }
    }

    public static class EntityResult {

        EntityResult(TmdbEntityType indexName, List<Object> entities) {
            this.entities = entities;
            this.indexName = indexName;
        }

        @JsonProperty("e")
        protected final List<Object> entities;
        @JsonProperty("i")
        protected final TmdbEntityType indexName;

        public List<Object> getEntities() {
            return entities;
        }

        public TmdbEntityType getIndexName() {
            return indexName;
        }

        public static class Builder extends EntityResult {
            public static Builder of(TmdbEntityType indexName) {
                return new Builder(indexName);
            }

            Builder(TmdbEntityType indexName) {
                super(indexName, new ArrayList<>());
            }

            public Builder add(Object entity) {
                entities.add(entity);
                return this;
            }

            public EntityResult build() {
                return new EntityResult(indexName, entities);
            }

        }

    }

    public static class Entity {
        @JsonProperty("sc")
        private final double score;
        @JsonProperty("r")
        private final Object result;

        public double getScore() {
            return score;
        }

        public Object getResult() {
            return result;
        }

        public Entity(double score, Object result) {
            this.score = score;
            this.result = result;
        }
    }
}
