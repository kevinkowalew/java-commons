package test.unit.configuration;

import databases.sql.postgresql.docker.DockerImage;
import databases.sql.postgresql.docker.DockerServiceName;
import databases.sql.postgresql.configuration.adapters.PostgresqlConfigurationToDeployment;
import databases.sql.postgresql.configuration.PostgresqlDeploymentConfiguration;
import databases.sql.postgresql.configuration.PostgresqlEnvironmentVariable;
import docker.components.Deployment;
import docker.components.Service;
import docker.fields.EnvironmentVariable;
import docker.fields.Link;
import docker.fields.NamedVolume;
import docker.fields.Port;
import docker.fields.Volume;
import docker.fields.enums.Restart;
import org.junit.Test;
import test.mocks.AbstractMockDatabaseControllerModule;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PostgresqlDeploymentConfigurationToDeploymentTests {

    @Test
    public void testConfigurationToDockerDeployment() throws IOException {
        // Arrange..
        PostgresqlDeploymentConfiguration mockConfiguration = AbstractMockDatabaseControllerModule.getMockConfiguration();

        // Act..
        PostgresqlConfigurationToDeployment deployment = new PostgresqlConfigurationToDeployment(mockConfiguration);

        // Assert..
        assertEquals(expectedDeployment(), deployment);
    }

    private Deployment expectedDeployment() {
        final NamedVolume databaseData = new NamedVolume("database-data");
        final NamedVolume pgadminData = new NamedVolume("pgadmin-data");

        return Deployment.newBuilder()
                .setVersion("3")
                .addServices(expectedPostgresService(), expectedPgAdminService())
                .addNamedVolumes(databaseData, pgadminData)
                .build();
    }

    private Service expectedPostgresService() {
        final EnvironmentVariable username = new EnvironmentVariable(PostgresqlEnvironmentVariable.POSTGRES_USER, "admin");
        final EnvironmentVariable password = new EnvironmentVariable(PostgresqlEnvironmentVariable.POSTGRES_PASSWORD, "password");
        final EnvironmentVariable port = new EnvironmentVariable(PostgresqlEnvironmentVariable.POSTGRES_PORT, 5432);

        final Volume databaseData = new Volume("database-data", "/var/lib/postgresql/data/");

        return Service.newBuilder()
                .setName(DockerServiceName.POSTGRES)
                .setImage(DockerImage.POSTGRES)
                .setRestart(Restart.ALWAYS)
                .setPorts(new Port(5432, 5432))
                .setEnvironmentVariables(username, password, port)
                .setVolumes(databaseData)
                .build();
    }

    private Service expectedPgAdminService() {
        final EnvironmentVariable email = new EnvironmentVariable(PostgresqlEnvironmentVariable.PGADMIN_DEFAULT_EMAIL, "admin@gmail.com");
        final EnvironmentVariable password = new EnvironmentVariable(PostgresqlEnvironmentVariable.PGADMIN_DEFAULT_PASSWORD, "secret");
        final EnvironmentVariable port = new EnvironmentVariable(PostgresqlEnvironmentVariable.PGADMIN_LISTEN_PORT, 80);

        final Volume pgadminData = new Volume("pgadmin-data", "/var/lib/pgadmin");
        final Volume serverConfiguration = new Volume("./servers.json", "/pgadmin4/servers.json");

        final Link databaseLink = new Link("database", "pgsql-server");

        return Service.newBuilder()
                .setName(DockerServiceName.PGADMIN)
                .setImage(DockerImage.PGADMIN)
                .setRestart(Restart.ALWAYS)
                .setPorts(new Port(80, 80))
                .setEnvironmentVariables(email, password, port)
                .setVolumes(pgadminData, serverConfiguration)
                .setLinks(databaseLink)
                .build();
    }
}