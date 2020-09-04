package gr.movieinsights.service.dto.movieinsights;

public interface IMovieInsightsContainerCategorizedDTO<E> extends IMovieInsightsContainerDTO {

    E getEntity();
    void setEntity(E entity);
}
