package gr.movieinsights.models;

import com.google.gson.annotations.SerializedName;
import gr.movieinsights.domain.enumeration.TmdbEntityType;

import java.util.List;

public class TmdbImportEntity {
    private List<Entity> results;

    public List<Entity> getResults() {
        return results;
    }

    public static class Entity {
        @SerializedName(value = "name", alternate = {"original_title", "original_name"})
        private String name;
        private Long id;
        private TmdbEntityType entityType;

        public String getName() {
            return name;
        }

        public Long getId() {
            return id;
        }

        public TmdbEntityType getEntityType() {
            return entityType;
        }

        public void setEntityType(TmdbEntityType entityType) {
            this.entityType = entityType;
        }
    }
}
