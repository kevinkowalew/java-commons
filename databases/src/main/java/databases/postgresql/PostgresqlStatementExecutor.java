package databases.postgresql;

import java.sql.SQLException;
import java.sql.Statement;

public interface PostgresqlStatementExecutor {
    Object apply(Statement statement, String sql) throws SQLException;
}
