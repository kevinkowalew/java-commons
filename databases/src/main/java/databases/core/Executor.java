package databases.core;

import databases.postgresql.PostgresqlDeserializer;

import java.util.List;
import java.util.Optional;

public interface Executor {
    boolean executeUpdate(String update);
    <T> Optional<List<T>> execute(String query, PostgresqlDeserializer<T> deserializer);
}
