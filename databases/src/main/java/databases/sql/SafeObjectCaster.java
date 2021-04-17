package databases.sql;

import java.util.Optional;

public interface SafeObjectCaster<T> {
    Optional<T> cast(Object object);
}
