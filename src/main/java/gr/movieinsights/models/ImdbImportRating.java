package gr.movieinsights.models;

import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.dozer.ICsvDozerBeanReader;

import java.io.IOException;

public class ImdbImportRating implements ImdbImportEntity {

    public static String[] getCSVCellHeaders(ICsvDozerBeanReader reader) {
        try {
            return reader.getHeader(true);
        } catch (IOException e) {
            return null;
        }
    }

    private String tconst;
    private Double averageRating;
    private Long numVotes;

    public ImdbImportRating() {
    }

    public String getTconst() {
        return tconst;
    }

    public void setTconst(String tconst) {
        this.tconst = tconst;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Long getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(Long numVotes) {
        this.numVotes = numVotes;
    }
}

