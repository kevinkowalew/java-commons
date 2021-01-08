package databases.adapters;

import databases.DockerImage;
import databases.DockerServiceName;
import databases.postgresql.*;
import docker.fields.EnvironmentVariable;
import docker.components.Service;
import docker.fields.Port;
import docker.fields.Volume;
import docker.fields.enums.Restart;

public class PostgresConfigurationToService extends Service {

    private PostgresConfigurationToService(Builder builder) {
        super(builder);
    }

    public PostgresConfigurationToService(PostgresqlConfiguration.PostgresConfiguration configuration) {
        super(constructServiceBuilderFromConfiguration(configuration));
    }

    private static Service.Builder constructServiceBuilderFromConfiguration(PostgresqlConfiguration.PostgresConfiguration configuration) {
        final Port port = new Port(configuration.getPort(), configuration.getPort());

        final EnvironmentVariable user = new EnvironmentVariable(PostgresqlEnvironmentVariable.POSTGRES_USER, configuration.getUser());
        final EnvironmentVariable password = new EnvironmentVariable(PostgresqlEnvironmentVariable.POSTGRES_PASSWORD, configuration.getPassword());
        final EnvironmentVariable postgresPort = new EnvironmentVariable(PostgresqlEnvironmentVariable.POSTGRES_PORT, configuration.getPort());

        final Volume databaseBackingData = new Volume("database-data", "/var/lib/postgresql/data/");
        final Volume databaseInitializerScript = new Volume("./init.sql", "/docker-entrypoint-initdb.d/init.sql");

        return Service.newBuilder()
                .setName(DockerServiceName.POSTGRES)
                .setImage(DockerImage.POSTGRES)
                .setRestart(Restart.ALWAYS)
                .setPorts(port)
                .setEnvironmentVariables(user, password, postgresPort)
                .setVolumes(databaseBackingData, databaseInitializerScript);
    }
}
