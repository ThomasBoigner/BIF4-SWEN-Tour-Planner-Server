package at.fhtw.tourplanner.application.service.mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractDtoMapper<D, T> {
    public abstract T toDto(D domainObject);

    public List<T> toDtos(Collection<D> domainObjects) {
        List<T> dtos = new ArrayList<>();
        domainObjects.forEach(entity -> {
            dtos.add(toDto(entity));
        });
        return dtos;
    }
}
