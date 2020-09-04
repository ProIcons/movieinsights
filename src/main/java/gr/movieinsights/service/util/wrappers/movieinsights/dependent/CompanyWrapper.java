package gr.movieinsights.service.util.wrappers.movieinsights.dependent;

import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.service.enumeration.MovieInsightsCategory;

import java.util.Comparator;

public class CompanyWrapper extends IdentifiedBaseWrapper<ProductionCompany> {
    private final long movieCount;

    public static Comparator<CompanyWrapper> getComparator() {
        return (CompanyWrapper cw1, CompanyWrapper cw2) -> {
            int result = Double.compare(cw1.getScore(), cw2.getScore());
            if (result == 0) {
                return Long.compare(cw1.movieCount, cw2.movieCount);
            }
            return result;
        };
    }
    public CompanyWrapper(ProductionCompany object, long movieCount) {
        super(object, MovieInsightsCategory.PerCompany);
        this.movieCount = movieCount;
    }
}
