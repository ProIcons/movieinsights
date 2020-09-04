package gr.movieinsights.service.dto.company;

import gr.movieinsights.service.dto.BaseNamedDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

public class BasicProductionCompanyDTO extends BaseNamedDTO {

    @NotNull
    private Long tmdbId;

    private String logoPath;

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public Long getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BasicProductionCompanyDTO)) return false;

        BasicProductionCompanyDTO that = (BasicProductionCompanyDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(tmdbId, that.tmdbId)
            .append(logoPath,that.logoPath)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(tmdbId)
            .append(logoPath)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("name", getName())
            .append("tmdbId", tmdbId)
            .append("logoPath",logoPath)
            .toString();
    }
}
