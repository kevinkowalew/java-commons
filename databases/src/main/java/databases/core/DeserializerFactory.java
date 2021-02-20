package databases.core;

import java.util.Optional;

public interface DeserializerFactory {
    Optional<Deserializer> getDeserializerForRequest(DatabaseRequest request);
}
