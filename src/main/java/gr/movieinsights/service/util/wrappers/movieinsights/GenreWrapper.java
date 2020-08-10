package gr.movieinsights.service.util.wrappers.movieinsights;

import gr.movieinsights.domain.Genre;

import java.util.Comparator;

public class GenreWrapper extends IdentifiedBaseWrapper<Genre> {
    public static Comparator<GenreWrapper> getComparator() {
        return (GenreWrapper gw1, GenreWrapper gw2) -> {
            int result = Integer.compare(gw1.count, gw2.count);
            if (result == 0) {
                if (gw1.object.getMovies() == null && gw2.object.getMovies() == null)
                    return 0;
                if (gw1.object.getMovies() == null)
                    return -1;
                if (gw2.object.getMovies() == null)
                    return 1;

                return Integer.compare(gw1.object.getMovies().size(), gw2.object.getMovies().size());
            }
            return result;
        };
    }

    public GenreWrapper(Genre object) {
        super(object);
    }
}
