package databases.crud.sql;

import databases.crud.core.DatabaseResponse;
import databases.crud.core.Deserializer;

public interface SqlExecutor {
    DatabaseResponse executeUpdate(String update, Deserializer deserializer) throws Exception;

    DatabaseResponse executeQuery(String query, Deserializer deserializer) throws Exception;
}

