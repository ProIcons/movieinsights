package gr.movieinsights.domain.enumeration;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;

public enum CreditRole {
    ACTOR("Actor", 0),
    PRODUCER("Producer", 1),
    DIRECTOR("Director", 2),
    WRITER("Writer", 3);

    public static CreditRole getCreditRoleByValue(String value) {
        return Arrays.stream(CreditRole.values()).filter(c -> c.getRole().equalsIgnoreCase(value)).findFirst().orElse(null);
    }

    String role;
    int index;

    CreditRole(String role, int index) {
        this.index = index;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public int getIndex() {
        return index;
    }

    public static int getSize() {
        return (int)Arrays.stream(CreditRole.values()).count();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("role", role)
            .toString();
    }


}
