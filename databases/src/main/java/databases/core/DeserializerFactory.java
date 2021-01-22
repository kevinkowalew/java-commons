package databases.core;

import databases.core.DatabaseRequest;
import databases.core.Deserializer;

public interface DeserializerFactory {
    Deserializer getDeserializerForRequest(DatabaseRequest request);
}
