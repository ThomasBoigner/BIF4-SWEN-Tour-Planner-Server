package at.fhtw.tourplanner.infrastructure.persistence.jpa.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractEntityMapper<D, E> {
    public abstract D toDomainObject(E entity);
    public abstract E toEntity(D domainObject);

    public List<D> toDomainObjects(Collection<E> entities) {
        List<D> domainObjects = new ArrayList<>();
        entities.forEach(entity -> {
            domainObjects.add(toDomainObject(entity));
        });
        return domainObjects;
    }

    public List<E> toEntities(Collection<D> domainObjects) {
        List<E> entities = new ArrayList<>();
        domainObjects.forEach(entity -> {
            entities.add(toEntity(entity));
        });
        return entities;
    }
}
