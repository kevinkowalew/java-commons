package databases.crud.sql.postgresql.executors;

import commons.OptionalProvider;
import databases.crud.core.DatabaseResponse;
import databases.crud.core.Deserializer;
import databases.crud.sql.SqlExecutorException;
import databases.crud.sql.SqlExecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class PostgresqlExecutor implements SqlExecutor {
    OptionalProvider<Connection> connectionProvider;

    public PostgresqlExecutor(OptionalProvider<Connection> connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public DatabaseResponse executeUpdate(String update, Deserializer deserializer) throws Exception {
        return executeSql(update, PostgresqlStatementExecutors.UPDATE_EXECUTOR, deserializer);
    }

    @Override
    public DatabaseResponse executeQuery(String query, Deserializer deserializer) throws Exception {
        return executeSql(query, PostgresqlStatementExecutors.QUERY_EXECUTOR, deserializer);
    }

    private DatabaseResponse executeSql(String sql,
                                        PostgresqlStatementExecutor statementExecutor,
                                        Deserializer deserializer) throws Exception {
        Optional<Connection> connection = connectionProvider.get();

        if (connection.isEmpty())
            throw SqlExecutorException.FAILED_TO_OPEN_DATABASE_CONNECTION;

        Optional<Statement> statement = createSqlStatementWithConnection(connection.get());

        if (statement.isEmpty())
            throw SqlExecutorException.FAILED_TO_OPEN_STATEMENT_ENTRY_POINT;

        try {
            Object results = statementExecutor.apply(statement.get(), sql);
            Object deserializedResults = deserializer.deserialize(results);

            return DatabaseResponse.newBuilder()
                    .setObject(deserializedResults)
                    .build();
        } catch (Exception e) {
            throw SqlExecutorException.FAILED_TO_DESERIALIZE_DATABASE_RESULTS;
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

    private Optional<Statement> createSqlStatementWithConnection(Connection connection) {
        try {
            return Optional.of(connection.createStatement());
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}

