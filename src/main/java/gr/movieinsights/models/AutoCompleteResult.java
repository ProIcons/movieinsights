package gr.movieinsights.models;

import com.fasterxml.jackson.annotation.JsonProperty;

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

        EntityResult(String indexName, List<Entity> entities) {
            this.entities = entities;
            this.indexName = indexName;
        }

        @JsonProperty("e")
        protected final List<Entity> entities;
        @JsonProperty("i")
        protected final String indexName;

        public List<Entity> getEntities() {
            return entities;
        }

        public String getIndexName() {
            return indexName;
        }

        public static class Builder extends EntityResult {
            public static Builder of(String indexName) {
                return new Builder(indexName);
            }

            Builder(String indexName) {
                super(indexName, new ArrayList<>());
            }

            public Builder add(Entity entity) {
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
