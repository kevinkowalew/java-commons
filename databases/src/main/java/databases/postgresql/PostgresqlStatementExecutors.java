package databases.postgresql;

import java.sql.SQLException;
import java.sql.Statement;

public class PostgresqlStatementExecutors {
    public static PostgresqlStatementExecutor QUERY_EXECUTOR = new PostgresqlStatementExecutor() {
        @Override
        public Object apply(Statement statement, String sql) {
            try {
                return statement.executeQuery(sql);
            } catch (SQLException throwables) {
                return null;
            }
        }
    };

    public static PostgresqlStatementExecutor UPDATE_EXECUTOR = new PostgresqlStatementExecutor() {
        @Override
        public Object apply(Statement statement, String sql) {
            try {
                return statement.executeUpdate(sql);
            } catch (SQLException throwables) {
                return null;
            }
        }
    };
}
