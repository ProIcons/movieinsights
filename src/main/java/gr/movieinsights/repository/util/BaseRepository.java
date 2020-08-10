package gr.movieinsights.repository.util;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<E,K> extends JpaRepository<E,K> {
    void clear();
}
