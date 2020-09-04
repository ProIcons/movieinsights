package gr.movieinsights.models;

import gr.movieinsights.domain.IdentifiedEntity;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Component
@DependsOn("liquibase")
public class SearchableEntityMovieCountMap {
    private final Map<Class<?>, Map<Long, Long>> entitiesMap;
    private final Map<Class<?>, Supplier<Map<Long, Long>>> entitiesSupplierMap;

    SearchableEntityMovieCountMap() {
        entitiesMap = new HashMap<>();
        entitiesSupplierMap = new HashMap<>();
    }

    @Async
    public void update() {
        synchronized (entitiesSupplierMap) {
            entitiesSupplierMap.keySet().forEach(this::update);
        }
    }

    @Async
    public void update(Class<?> clazz) {
        synchronized (entitiesMap) {
            if (entitiesMap.containsKey(clazz)) {
                entitiesMap.replace(clazz, entitiesSupplierMap.get(clazz).get());
            } else {
                entitiesMap.put(clazz, entitiesSupplierMap.get(clazz).get());
            }
        }
    }

    public <E> void register(Class<E> clazz, Supplier<Map<Long, Long>> supplier) {
        synchronized (entitiesSupplierMap) {
            entitiesSupplierMap.put(clazz, supplier);
            update(clazz);
        }
    }

    public void clear() {
        synchronized (entitiesMap) {
            entitiesMap.values().forEach(Map::clear);
        }
    }

    public <T extends IdentifiedEntity> Long getMovieCount(@NotNull T entity) {
        if (entity == null)
            throw new NullPointerException();
        synchronized (entitiesMap) {
            if (entitiesMap.containsKey(entity.getClass())) {
                return entitiesMap.get(entity.getClass()).getOrDefault(entity.getId(), null);
            }
        }
        throw new RuntimeException("This class " + entity.getClass().getName() + " is not registered");
    }
}
