package gr.movieinsights.domain.enumeration;

/**
 * The BanReason enumeration.
 */
public enum BanReason {
    NOTFOUND("Not Found"),
    UNDEFINED("Undefined"),
    NOBUDGET("No Budget"),
    NOREVENUE("No Revenue"),
    NORELEASEDATE("No Release Date"),
    NOVOTE("No Vote Data"),
    NOUSABLEDATA("No Usable Data"),
    CUSTOM("Custom");

    String banReason;

    BanReason(String banReason) {
        this.banReason = banReason;
    }

    public String getReason() {
        return banReason;
    }
}
