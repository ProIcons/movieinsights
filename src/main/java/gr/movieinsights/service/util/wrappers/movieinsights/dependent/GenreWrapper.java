package gr.movieinsights.service.util.wrappers.movieinsights.dependent;

import gr.movieinsights.domain.Genre;
import gr.movieinsights.service.enumeration.MovieInsightsCategory;

import java.util.Comparator;

public class GenreWrapper extends IdentifiedBaseWrapper<Genre> {
    private final long movieCount;

    public static Comparator<GenreWrapper> getComparator() {
        return (GenreWrapper gw1, GenreWrapper gw2) -> {
            int result = Double.compare(gw1.getScore(), gw2.getScore());
            if (result == 0) {
                return Long.compare(gw1.movieCount, gw2.movieCount);
            }
            return result;
        };
    }

    public GenreWrapper(Genre object, long movieCount) {
        super(object, MovieInsightsCategory.PerGenre);
        this.movieCount = movieCount;
    }
}
