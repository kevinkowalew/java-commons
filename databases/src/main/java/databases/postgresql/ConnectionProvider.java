package databases.postgresql;

import java.util.Optional;

public interface ConnectionProvider<T> {
    Optional<T> getConnection();
}
