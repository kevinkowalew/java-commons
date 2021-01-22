package databases.postgresql;

import databases.core.DatabaseResponse;

public interface DatabaseResponseDeserializer {
    Object deserialize(DatabaseResponse response);
}
