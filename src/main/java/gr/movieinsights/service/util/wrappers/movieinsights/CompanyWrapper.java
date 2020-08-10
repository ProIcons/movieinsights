package gr.movieinsights.service.util.wrappers.movieinsights;

import gr.movieinsights.domain.ProductionCompany;

import java.util.Comparator;

public class CompanyWrapper extends IdentifiedBaseWrapper<ProductionCompany> {
    public static Comparator<CompanyWrapper> getComparator() {
        return (CompanyWrapper cw1, CompanyWrapper cw2) -> {
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
    public CompanyWrapper(ProductionCompany object) {
        super(object);
    }
}
