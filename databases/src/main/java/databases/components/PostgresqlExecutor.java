package databases.components;

import databases.postgresql.PostgresqlDeserializer;

import java.util.List;
import java.util.Optional;

public interface PostgresqlExecutor {
    boolean executeUpdate(String var1);

    <T> Optional<List<T>> execute(String var1, PostgresqlDeserializer<T> var2);
}
