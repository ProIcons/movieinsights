package gr.movieinsights.service.util.wrappers.movieinsights.dependent;

import gr.movieinsights.service.enumeration.MovieInsightsCategory;

public class YearWrapper extends BaseWrapper<Integer> {
    public YearWrapper(Integer object) {
        super(object, MovieInsightsCategory.PerYear);
    }
}
