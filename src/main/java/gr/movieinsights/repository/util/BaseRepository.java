package gr.movieinsights.repository.util;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<E,K> extends JpaRepository<E,K> {
    void clear();

    @Query("SELECT distinct mi FROM #{#entityName} mi")
    Optional<E> findFirst();

}
