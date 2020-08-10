package gr.movieinsights.domain.enumeration;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;

public enum CreditRole {
    ACTOR("Actor"),
    PRODUCER("Producer"),
    DIRECTOR("Director"),
    WRITER("Writer");

    public static CreditRole getCreditRoleByValue(String value) {
        return Arrays.stream(CreditRole.values()).filter(c->c.getRole().equalsIgnoreCase(value)).findFirst().orElse(null);
    }

    String role;

    CreditRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("role", role)
            .toString();
    }


}
