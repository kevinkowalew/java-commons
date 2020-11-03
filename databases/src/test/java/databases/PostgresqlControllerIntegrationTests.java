package databases;

import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PostgresqlControllerIntegrationTests {
    static final String CREATE_TABLE = "CREATE TABLE Persons (Id int, Name varchar(255));";
    static final String DROP_TABLE = "DROP TABLE Persons;";
    static final String INSERT_USER = "INSERT INTO Persons (Id, Name) VALUES (0, 'Kevin');";
    static final String SELECT_ALL = "SELECT * from Persons;";

    @Test
    public void test_ExecuteUpdate() {
        // Arrange
        PostgresqlController controller = createController();

        // Act
        boolean createTableResult = controller.executeUpdate(CREATE_TABLE);
        boolean dropTableResult = controller.executeUpdate(DROP_TABLE);

        // Assert
        assertTrue(createTableResult);
        assertTrue(dropTableResult);
    }

    @Test
    public void test_ExecuteQuery() {
        // Arrange
        PostgresqlController controller = createController();

        // Act
        boolean createTableResult = controller.executeUpdate(CREATE_TABLE);
        MockUser.Deserializer deserializer = new  MockUser.Deserializer();
        boolean insertUserResult = controller.executeUpdate(INSERT_USER);
        Optional<List<MockUser>> users = controller.execute(SELECT_ALL, deserializer);
        boolean dropTableResult = controller.executeUpdate(DROP_TABLE);

        // Assert
        assertTrue(createTableResult);
        assertTrue(insertUserResult);
        assertTrue(dropTableResult);
        assertTrue(users.isPresent());
        assertEquals(users.stream().count(), 1);
        MockUser user = users.get().stream().findFirst().get();
        assertEquals(user.getId(), 0);
        assertEquals(user.getName(), "Kevin");
    }

    private PostgresqlController createController() {
        PostgresqlConnection connection = PostgresqlConnection.newBuilder()
                .setHost("localhost")
                .setPort(5432)
                .setDatabaseName("database")
                .setUser("admin")
                .setPassword("password")
                .build();
        return new PostgresqlController(connection);
    }
}
