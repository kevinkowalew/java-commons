package databases.crud.sql.postgresql.configuration.adapters;

import databases.crud.sql.postgresql.docker.DockerImage;
import databases.crud.sql.postgresql.docker.DockerServiceName;
import databases.crud.sql.postgresql.configuration.PostgresqlDeploymentConfiguration;
import databases.crud.sql.postgresql.configuration.PostgresqlEnvironmentVariable;
import docker.fields.EnvironmentVariable;
import docker.components.Service;
import docker.fields.Port;
import docker.fields.Volume;
import docker.fields.enums.Restart;

import java.util.ArrayList;
import java.util.List;

public class PostgresConfigurationToService extends Service {
    public PostgresConfigurationToService(PostgresqlDeploymentConfiguration.PostgresqlConfiguration configuration) {
        super(constructServiceBuilderFromConfiguration(configuration));
    }

    private static Service.Builder constructServiceBuilderFromConfiguration(PostgresqlDeploymentConfiguration.PostgresqlConfiguration configuration) {
        final Port port = new Port(configuration.getPort(), configuration.getPort());

        final EnvironmentVariable user = new EnvironmentVariable(PostgresqlEnvironmentVariable.POSTGRES_USER, configuration.getUser());
        final EnvironmentVariable password = new EnvironmentVariable(PostgresqlEnvironmentVariable.POSTGRES_PASSWORD, configuration.getPassword());
        final EnvironmentVariable postgresPort = new EnvironmentVariable(PostgresqlEnvironmentVariable.POSTGRES_PORT, configuration.getPort());

        final List<Volume> volumes = new ArrayList<>();

        if (configuration.getInitScriptName() != null) {
            final Volume databaseInitializerScript = new Volume("./init.sql", "/docker-entrypoint-initdb.d/init.sql");
            volumes.add(databaseInitializerScript);
        }

        final Volume namedDatabaseVolume = new Volume("database-data", "/var/lib/postgresql/data/");
        volumes.add(namedDatabaseVolume);

        return Service.newBuilder()
                .setName(DockerServiceName.POSTGRES)
                .setImage(DockerImage.POSTGRES)
                .setRestart(Restart.ALWAYS)
                .setPorts(port)
                .setEnvironmentVariables(user, password, postgresPort)
                .setVolumes(volumes.toArray(Volume[]::new));
    }
}
