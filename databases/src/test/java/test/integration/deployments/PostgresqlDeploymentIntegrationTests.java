package test.integration.deployments;

import commons.utils.BashExecutor;
import commons.utils.YamlDeserializer;
import databases.Factory;
import databases.postgresql.PostgresqlConfiguration;
import databases.postgresql.PostgresqlConnection;
import databases.postgresql.PostgresqlExecutor;
import docker.components.Deployment;
import docker.controllers.DeploymentController;
import docker.controllers.DeploymentFileWriter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import test.MockUser;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PostgresqlDeploymentIntegrationTests {
    static final String CREATE_TABLE = "CREATE TABLE Persons (Id int, Name varchar(255));";
    static final String DROP_TABLE = "DROP TABLE Persons;";
    static final String INSERT_USER = "INSERT INTO Persons (Id, Name) VALUES (0, 'Kevin');";
    static final String SELECT_ALL = "SELECT * from Persons;";
    private static DeploymentController controller;

    @BeforeClass
    public static void setup() throws InterruptedException {
        final String resourceName = "mock-postgresql-config.yml";
        Optional<PostgresqlConfiguration> configuration = YamlDeserializer.deserializeFromResource(resourceName, PostgresqlConfiguration.class);
        final Deployment deployment = Factory.createDeploymentFromConfiguration(configuration.get());
        final DeploymentFileWriter writer = new DeploymentFileWriter(deployment);
        final String workingDirectory = "src/test/resources";
        final BashExecutor bashExecutor = new BashExecutor();
        controller = new DeploymentController(writer, workingDirectory, "docker-compose.yml", bashExecutor);
        controller.start();
    }

    @AfterClass public static void cleanup() {
        controller.stop();
    }

    @Test
    public void test_ExecuteUpdate() {
        // Arrange
        PostgresqlExecutor sut = createExecutor();

        // Act
        boolean createTableResult = sut.executeUpdate(CREATE_TABLE);
        boolean dropTableResult = sut.executeUpdate(DROP_TABLE);

        // Assert
        assertTrue(createTableResult);
        assertTrue(dropTableResult);
    }

    @Test
    public void test_ExecuteQuery() {
        // Arrange
        PostgresqlExecutor sut = createExecutor();

        // Act
        boolean createTableResult = sut.executeUpdate(CREATE_TABLE);
        MockUser.Deserializer deserializer = new  MockUser.Deserializer();
        boolean insertUserResult = sut.executeUpdate(INSERT_USER);
        Optional<List<MockUser>> users = sut.execute(SELECT_ALL, deserializer);
        boolean dropTableResult = sut.executeUpdate(DROP_TABLE);

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

    private PostgresqlExecutor createExecutor() {
        PostgresqlConnection connection = PostgresqlConnection.newBuilder()
                .setHost("localhost")
                .setPort(5432)
                .setDatabaseName("postgres")
                .setUser("admin")
                .setPassword("password")
                .build();
        return new PostgresqlExecutor(connection);
    }
}
