package gr.movieinsights.service.util.wrappers.movieinsights;

import gr.movieinsights.domain.ProductionCountry;

import java.util.Comparator;

public class CountryWrapper extends IdentifiedBaseWrapper<ProductionCountry> {
    public static Comparator<CountryWrapper> getComparator() {
        return (CountryWrapper cw1, CountryWrapper cw2) -> {
            int result = Integer.compare(cw1.count, cw2.count);
            if (result == 0) {
                if (cw1.object.getMovies() == null && cw2.object.getMovies() == null)
                    return 0;
                if (cw1.object.getMovies() == null)
                    return -1;
                if (cw2.object.getMovies() == null)
                    return 1;

                return Integer.compare(cw1.object.getMovies().size(), cw2.object.getMovies().size());
            }
            return result;
        };
    }

    public CountryWrapper(ProductionCountry object) {
        super(object);
    }
}
