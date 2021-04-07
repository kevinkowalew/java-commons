package databases.sql;

import databases.core.DatabaseResponse;
import databases.core.Deserializer;

public interface SqlExecutor {
    DatabaseResponse executeUpdate(String update, Deserializer deserializer) throws Exception;

    DatabaseResponse executeQuery(String query, Deserializer deserializer) throws Exception;
}

