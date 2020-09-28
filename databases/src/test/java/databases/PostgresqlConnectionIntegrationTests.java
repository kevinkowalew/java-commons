package databases;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class PostgresqlConnectionIntegrationTests {
    @Test
    public void testDockerContainerConnection() {
        // Arrange
        PostgresqlConnection connection = PostgresqlConnection.newBuilder()
                .setHost("localhost")
                .setPort(5432)
                .setDatabaseName("database")
                .setUser("admin")
                .setPassword("admin")
                .build();

        // Act
        Optional<DatabaseController> controller = DatabaseController.connect(connection);

        // Assert
        Assert.assertTrue(controller.isPresent());
        Assert.assertTrue(controller.get().isOpen());
    }
}
