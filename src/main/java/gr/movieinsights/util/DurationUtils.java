package gr.movieinsights.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

public class DurationUtils {
    public final static DateFormat DURATION_FORMATTER = new SimpleDateFormat("HH:mm:ss.SSS");

    static {
        DURATION_FORMATTER.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static Long getDurationInMillis(Instant instant) {
        return Duration.between(instant,Instant.now()).toMillis();
    }

    public static String getDurationInTimeFormat(Instant instant) {
        return DURATION_FORMATTER.format(new Date(getDurationInMillis(instant)));
    }

}
