package databases.postgresql;

import java.sql.SQLException;
import java.sql.Statement;

public class PostgresqlStatementExecutors {
    public static PostgresqlStatementExecutor QUERY_EXECUTOR = new PostgresqlStatementExecutor() {
        @Override
        public Object apply(Statement statement, String sql) throws SQLException {
            return statement.executeQuery(sql);
        }
    };

    public static PostgresqlStatementExecutor UPDATE_EXECUTOR = new PostgresqlStatementExecutor() {
        @Override
        public Object apply(Statement statement, String sql) throws SQLException {
            return statement.executeUpdate(sql);
        }
    };
}
