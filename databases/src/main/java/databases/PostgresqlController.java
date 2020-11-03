package databases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgresqlController implements SQLExecutor {
    PostgresqlConnection connection;

    public PostgresqlController(PostgresqlConnection connection) {
        this.connection = connection;
    }

    @Override
    public boolean executeUpdate(String update) {
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            statement.execute(update);
            commitAndClose(connection, statement);
            return true;
        } catch (Exception e) {
           return false;
        }
    }

    @Override
    public <T> Optional<List<T>> execute(String query, ResultSetDeserializer<T> deserializer) {
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<T> returnValue = parseResults(resultSet, deserializer);
            commitAndClose(connection, statement);
            return Optional.of(returnValue);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Connection connect() throws Exception {
        Connection connection = this.connection.connect().orElseThrow(Exception::new);
        connection.setAutoCommit(false);
        return connection;
    }

    private <T> List<T> parseResults(ResultSet resultSet, ResultSetDeserializer<T> deserializer) throws SQLException {
        List<T> returnValue = new ArrayList<>();

        while(resultSet.next()) {
            T deserializedObject = deserializer.deserialize(resultSet);
            returnValue.add(deserializedObject);
        }

        return returnValue;
    }

    private void commitAndClose(Connection connection, Statement statement) throws SQLException {
        connection.commit();
        statement.close();
        connection.close();
    }
}
