package gr.movieinsights.models;

import org.supercsv.io.dozer.ICsvDozerBeanReader;

import java.io.IOException;
import java.util.Arrays;

public class ImdbImportMovie implements ImdbImportEntity {
    public static String[] getCSVCellHeaders(ICsvDozerBeanReader reader) {
        try {
            return Arrays.copyOf(new String[]{
                    "tconst",
                    "titleType"},
                reader.getHeader(true).length);
        } catch (IOException e) {
            return null;
        }
    }

    public ImdbImportMovie() {
    }

    private String tconst;
    private String titleType;

    @Override
    public String getTconst() {
        return tconst;
    }

    public void setTconst(String tconst) {
        this.tconst = tconst;
    }

    public String getTitleType() {
        return titleType;
    }

    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }

}
