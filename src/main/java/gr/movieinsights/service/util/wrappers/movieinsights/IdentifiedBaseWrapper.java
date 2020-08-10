package gr.movieinsights.service.util.wrappers.movieinsights;

import gr.movieinsights.domain.IdentifiedEntity;

public abstract class IdentifiedBaseWrapper<T extends IdentifiedEntity> extends BaseWrapper<T> {
    public IdentifiedBaseWrapper(T object) {
        super(object);
    }
}
