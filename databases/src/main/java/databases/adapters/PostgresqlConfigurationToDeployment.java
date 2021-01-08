package databases.adapters;

import databases.postgresql.PostgresqlConfiguration;
import docker.components.Deployment;
import docker.components.Service;
import docker.fields.NamedVolume;

public class PostgresqlConfigurationToDeployment extends Deployment {
    private PostgresqlConfigurationToDeployment(Deployment.Builder builder) {
        super(builder);
    }

    public PostgresqlConfigurationToDeployment(PostgresqlConfiguration configuration) {
        super(createBuilderForConfiguration(configuration));
    }

    private static Deployment.Builder createBuilderForConfiguration(PostgresqlConfiguration configuration) {
        final Service postgresService = new PostgresConfigurationToService(configuration.getPostgresqlConfiguration());
        final Service pgAdminService = new PgAdminConfigurationToService(configuration.getPgAdminConfiguration());

        final NamedVolume databaseNamedVolume = new NamedVolume("database-data");
        final NamedVolume pgadminNamedVolume = new NamedVolume("pgadmin-data");

        return Deployment.newBuilder()
                .addServices(postgresService, pgAdminService)
                .addNamedVolumes(databaseNamedVolume, pgadminNamedVolume);
    }
}