package gr.movieinsights.service.enumeration;

import java.util.Arrays;

public enum MovieInsightsCategory {
    General(0),
    PerCompany(1),
    PerCountry(2),
    PerYear(3),
    PerGenre(4),
    PerPerson(5);

    int index;

    MovieInsightsCategory(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static int getSize() {
        return (int) Arrays.stream(MovieInsightsCategory.values()).count();
    }
}
