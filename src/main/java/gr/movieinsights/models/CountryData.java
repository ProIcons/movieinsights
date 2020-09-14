package gr.movieinsights.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import gr.movieinsights.domain.ProductionCountry;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CountryData {
    @JsonProperty("_id")
    private Long id;
    private String name;
    private String iso31661;
    @JsonProperty("value")
    private Integer movieCount;

    public CountryData(Long id, String name, String iso31661, Integer movieCount) {
        this.id = id;
        this.name = name;
        this.iso31661 = iso31661;
        this.movieCount = movieCount;
    }

    public CountryData(ProductionCountry productionCountry, Long movieCount) {
        this.id = productionCountry.getId();
        this.name = productionCountry.getName();
        this.movieCount = movieCount.intValue();
        this.iso31661 = productionCountry.getIso31661();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public Integer getMovieCount() {
        return movieCount;
    }

    public void setMovieCount(Integer movieCount) {
        this.movieCount = movieCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CountryData)) return false;

        CountryData that = (CountryData) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(name, that.name)
            .append(iso31661, that.iso31661)
            .append(movieCount, that.movieCount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(name)
            .append(iso31661)
            .append(movieCount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("name", name)
            .append("iso31661", iso31661)
            .append("movieCount", movieCount)
            .toString();
    }
}
