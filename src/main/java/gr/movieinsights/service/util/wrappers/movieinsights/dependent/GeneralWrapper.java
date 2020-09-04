package gr.movieinsights.service.util.wrappers.movieinsights.dependent;

import gr.movieinsights.service.enumeration.MovieInsightsCategory;

import java.io.Serializable;

public class GeneralWrapper extends BaseWrapper<Object> {
    public GeneralWrapper() {
        super(new Serializable() {}, MovieInsightsCategory.General);
    }
}
