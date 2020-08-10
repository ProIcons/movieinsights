package gr.movieinsights.service;

public interface TmdbIdContainer<T> {
    T findByTmdbId(Long tmdbId);
}
