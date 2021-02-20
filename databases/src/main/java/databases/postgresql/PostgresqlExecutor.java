package databases.postgresql;

import databases.core.DatabaseResponse;
import databases.core.QueryParameterOperator;
import databases.core.Deserializer;
import databases.sql.SqlExecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class PostgresqlExecutor implements SqlExecutor {
    ConnectionProvider<Connection> connectionProvider;

    public PostgresqlExecutor(ConnectionProvider<Connection> connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public DatabaseResponse executeUpdate(String update, Deserializer deserializer) {
        return executeSql(update, PostgresqlStatementExecutors.UPDATE_EXECUTOR, deserializer);
    }

    @Override
    public DatabaseResponse executeQuery(String query, Deserializer deserializer) {
        return executeSql(query, PostgresqlStatementExecutors.QUERY_EXECUTOR, deserializer);
    }

    private DatabaseResponse executeSql(String sql,
                                        PostgresqlStatementExecutor statementExecutor,
                                        Deserializer deserializer) {
        Optional<Connection> connection = connectionProvider.getConnection();

        if (connection.isEmpty())
            return QueryParameterOperator.PostgresqlErrorResponse.CONNECTION_ERROR;

        Optional<Statement> statement = createSqlStatementWithConnection(sql, connection.get());

        if (statement.isEmpty())
            return QueryParameterOperator.PostgresqlErrorResponse.STATEMENT_ERROR;

        try {
            Object results = statementExecutor.apply(statement.get(), sql);
            Object deserializedResults = deserializer.deserialize(results);

            return DatabaseResponse.success()
                    .setObject(deserializedResults)
                    .build();
        } catch (Exception e) {
            return DatabaseResponse.error()
                    .setObject(e)
                    .build();
        } finally {
            try {
                connection.get().commit();
                statement.get().close();
                connection.get().close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private Optional<Statement> createSqlStatementWithConnection(String sql, Connection connection) {
        try {
            return Optional.of(connection.createStatement());
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}

