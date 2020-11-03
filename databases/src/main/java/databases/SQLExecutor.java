package databases;

import java.util.List;
import java.util.Optional;

public interface SQLExecutor {
    boolean executeUpdate(String update);
    <T> Optional<List<T>> execute(String query, ResultSetDeserializer<T> deserializer);
}
