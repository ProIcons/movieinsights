package gr.movieinsights.service.dto.country;

import gr.movieinsights.service.dto.BaseNamedDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

public class BasicProductionCountryDTO extends BaseNamedDTO {
    @NotNull
    private String iso31661;

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BasicProductionCountryDTO)) return false;

        BasicProductionCountryDTO that = (BasicProductionCountryDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(iso31661, that.iso31661)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(iso31661)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("name", getName())
            .append("iso31661", iso31661)
            .toString();
    }
}
