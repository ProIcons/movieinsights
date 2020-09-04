package gr.movieinsights.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PageResult<T> {
    @JsonProperty("e")
    private final List<T> entities;
    @JsonProperty("o")
    private final long offset;
    @JsonProperty("s")
    private final long size;
    @JsonProperty("t")
    private final long total;

    public PageResult(List<T> entities, long o, long size, long t) {
        this.entities = entities;
        this.offset = o;
        this.size = size;
        this.total = t;
    }

    public List<T> getEntities() {
        return entities;
    }

    public long getOffset() {
        return offset;
    }

    public long getSize() {
        return size;
    }

    public long getTotal() {
        return total;
    }
}
