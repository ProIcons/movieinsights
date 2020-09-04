package gr.movieinsights.service.dto.person;

import gr.movieinsights.service.dto.BaseNamedDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

public class BasicPersonDTO extends BaseNamedDTO {
    @NotNull
    private Long tmdbId;

    private String imdbId;

    private String profilePath;

    public Long getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }



    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BasicPersonDTO)) return false;

        BasicPersonDTO that = (BasicPersonDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(tmdbId, that.tmdbId)
            .append(imdbId, that.imdbId)
            .append(profilePath, that.profilePath)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(tmdbId)
            .append(imdbId)
            .append(profilePath)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("name", getName())
            .append("tmdbId", tmdbId)
            .append("imdbId", imdbId)
            .append("profilePath", profilePath)
            .toString();
    }
}
