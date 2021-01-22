package databases.postgresql;

import databases.core.DatabaseResponse;

import java.util.Optional;

public class PostgresqlGenericListDeserializerExecutor<T> implements DatabaseResponseDeserializer {

    public PostgresqlGenericListDeserializerExecutor() { }

    @Override
    public Optional<T> deserialize(DatabaseResponse response) {
        return Optional.empty();
    }
}
