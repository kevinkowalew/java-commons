package databases.crud.sql.postgresql.configuration.adapters;

import databases.crud.sql.postgresql.docker.DockerImage;
import databases.crud.sql.postgresql.docker.DockerServiceName;
import databases.crud.sql.postgresql.configuration.PostgresqlDeploymentConfiguration;
import databases.crud.sql.postgresql.configuration.PostgresqlEnvironmentVariable;
import docker.components.Service;
import docker.fields.EnvironmentVariable;
import docker.fields.Link;
import docker.fields.Port;
import docker.fields.Volume;
import docker.fields.enums.Restart;

public class PgAdminConfigurationToService extends Service {
    public PgAdminConfigurationToService(PostgresqlDeploymentConfiguration.PgAdminConfiguration configuration) {
        super(constructServiceBuilderFromConfiguration(configuration));
    }

    private static Service.Builder constructServiceBuilderFromConfiguration(PostgresqlDeploymentConfiguration.PgAdminConfiguration configuration) {
        final Port port = new Port(configuration.getPort(), 80);

        final EnvironmentVariable defaultEmail = new EnvironmentVariable(PostgresqlEnvironmentVariable.PGADMIN_DEFAULT_EMAIL, configuration.getEmail());
        final EnvironmentVariable defaultPassword = new EnvironmentVariable(PostgresqlEnvironmentVariable.PGADMIN_DEFAULT_PASSWORD, configuration.getPassword());
        final EnvironmentVariable listenPort = new EnvironmentVariable(PostgresqlEnvironmentVariable.PGADMIN_LISTEN_PORT, configuration.getPort());

        final Volume pgAdminData = new Volume("pgadmin-data", "/var/lib/pgadmin");
        final Volume serversConfiguration = new Volume("./servers.json", "/pgadmin4/servers.json");

        final Link databaseLink = new Link("database", "pgsql-server");

        return Service.newBuilder()
                .setName(DockerServiceName.PGADMIN)
                .setImage(DockerImage.PGADMIN)
                .setRestart(Restart.ALWAYS)
                .setPorts(port)
                .setEnvironmentVariables(defaultEmail, defaultPassword, listenPort)
                .setLinks(databaseLink)
                .setVolumes(pgAdminData, serversConfiguration);
    }
}