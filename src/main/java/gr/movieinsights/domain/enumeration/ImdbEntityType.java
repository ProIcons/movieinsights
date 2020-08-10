package gr.movieinsights.domain.enumeration;


import gr.movieinsights.models.ImdbImportMovie;
import gr.movieinsights.models.ImdbImportRating;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.dozer.ICsvDozerBeanReader;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

public enum ImdbEntityType {
    NAMEBASICS("name.basics", Object.class, null),
    TITLEAKAS("title.akas", Object.class, null),
    TITLEBASICS("title.basics", ImdbImportMovie.class, ImdbImportMovie::getCSVCellHeaders),
    TITLECREW("title.crew", Object.class, null),
    TITLEEPISODE("title.episode", Object.class, null),
    TITLEPRINCIPALS("title.principals", Object.class, null),
    TITLERATINGS("title.ratings", ImdbImportRating.class, ImdbImportRating::getCSVCellHeaders);

    String identifier;
    Function<ICsvDozerBeanReader, String[]> cellHeaderProvider;
    Class<?> type;

    public static ImdbEntityType getImdbEntityTypeByClass(Class<?> objectClass) {
        return Arrays.stream(ImdbEntityType.values()).filter(e -> e.type == objectClass).findFirst().orElse(null);
    }

    ImdbEntityType(String identifier, Class<?> type, Function<ICsvDozerBeanReader, String[]> cellHeaderProvider) {
        this.cellHeaderProvider = cellHeaderProvider;
        this.identifier = identifier;
        this.type = type;
    }

    public Function<ICsvDozerBeanReader, String[]> getCellHeaderProvider() {
        return cellHeaderProvider;
    }

    public Class<?> getInvocationType() {
        return type;
    }

    public String getExportIdentifier() {
        return identifier;
    }
}
