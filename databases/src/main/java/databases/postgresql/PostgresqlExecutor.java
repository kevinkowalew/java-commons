package databases.postgresql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgresqlExecutor implements databases.core.Executor {
    PostgresqlConnection connection;

    public PostgresqlExecutor(PostgresqlConnection connection) {
        this.connection = connection;
    }

    @Override
    public boolean executeUpdate(String update) {
        try {
            java.sql.Connection connection = connect();
            Statement statement = connection.createStatement();
            statement.execute(update);
            commitAndClose(connection, statement);
            return true;
        } catch (Exception e) {
           return false;
        }
    }

    @Override
    public <T> Optional<List<T>> execute(String query, PostgresqlDeserializer<T> deserializer) {
        try {
            java.sql.Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<T> returnValue = parseResults(resultSet, deserializer);
            commitAndClose(connection, statement);
            return Optional.of(returnValue);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private java.sql.Connection connect() throws Exception {
        java.sql.Connection connection = this.connection.connect().orElseThrow(Exception::new);
        connection.setAutoCommit(false);
        return connection;
    }

    private <T> List<T> parseResults(ResultSet resultSet, PostgresqlDeserializer<T> deserializer) throws SQLException {
        List<T> returnValue = new ArrayList<>();

        while(resultSet.next()) {
            T deserializedObject = deserializer.deserialize(resultSet);
            returnValue.add(deserializedObject);
        }

        return returnValue;
    }

    private void commitAndClose(java.sql.Connection connection, Statement statement) throws SQLException {
        connection.commit();
        statement.close();
        connection.close();
    }
}
