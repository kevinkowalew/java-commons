package databases.sql;

import databases.core.DatabaseRequest;
import databases.core.OperationType;

import java.util.Optional;

public interface SqlStatementFactory {
    Optional<String> createSqlStatementForRequest(DatabaseRequest request);
}
