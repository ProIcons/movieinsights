package gr.movieinsights.repository;

import gr.movieinsights.domain.Genre;

import gr.movieinsights.repository.util.TmdbIdentifiedRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Genre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenreRepository extends TmdbIdentifiedRepository<Genre, Long> {
}
