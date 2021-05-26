package databases.orm;

import databases.crud.core.ResultSetDeserializer;

import java.sql.ResultSet;
import java.util.Optional;

public class GenericDeserializer<T> extends ResultSetDeserializer<T> {
    private final Class<T> modelClass;

    public GenericDeserializer(Class<T> modelClass) {
        this.modelClass = modelClass;
    }

    @Override
    public Optional<T> deserializeResultSet(ResultSet resultSet) {
        // TODO: implement me
        return Optional.empty();
    }

    @Override
    public Class<T> getGenericClassReference() {
        return modelClass;
    }
}
