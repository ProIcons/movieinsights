package gr.movieinsights.service.dto.credit;

import gr.movieinsights.domain.enumeration.CreditRole;
import gr.movieinsights.service.dto.BaseDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

public abstract class BaseCreditDTO extends BaseDTO {
    @NotNull
    private CreditRole role;

    public CreditRole getRole() {
        return role;
    }

    public void setRole(CreditRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BaseCreditDTO)) return false;

        BaseCreditDTO that = (BaseCreditDTO) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(role, that.role)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(role)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("role", role)
            .toString();
    }
}
