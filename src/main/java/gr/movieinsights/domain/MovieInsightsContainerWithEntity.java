package gr.movieinsights.domain;

public interface MovieInsightsContainerWithEntity<E> extends MovieInsightsContainer {
    E getEntity();
    void setEntity(E entity);
}
