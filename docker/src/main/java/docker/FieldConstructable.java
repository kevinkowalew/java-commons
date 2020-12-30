package docker;

import docker.fields.Field;

import java.util.Optional;

public interface FieldConstructable<T> {
    Optional<T> fromField(Field field);
}
