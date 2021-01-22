package databases.sql;

import databases.core.DatabaseRequest;

public interface SqlStatementFactory {
    String createSqlStatementForDatabaseRequest(DatabaseRequest request);
}
