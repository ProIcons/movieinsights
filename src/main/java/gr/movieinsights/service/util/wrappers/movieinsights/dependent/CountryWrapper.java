package gr.movieinsights.service.util.wrappers.movieinsights.dependent;

import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.service.enumeration.MovieInsightsCategory;

import java.util.Comparator;

public class CountryWrapper extends IdentifiedBaseWrapper<ProductionCountry> {
    private final long movieCount;

    public static Comparator<CountryWrapper> getComparator() {
        return (CountryWrapper cw1, CountryWrapper cw2) -> {
            int result = Double.compare(cw1.getScore(), cw2.getScore());
            if (result == 0) {
                return Long.compare(cw1.movieCount, cw2.movieCount);
            }
            return result;
        };
    }

    public CountryWrapper(ProductionCountry object,long movieCount) {
        super(object, MovieInsightsCategory.PerCountry);
        this.movieCount = movieCount;
    }
}
