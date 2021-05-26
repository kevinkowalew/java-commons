package databases.crud.sql.postgresql.configuration.adapters;

import databases.crud.sql.postgresql.configuration.PostgresqlDeploymentConfiguration;
import docker.components.Deployment;
import docker.components.Service;
import docker.fields.NamedVolume;

public class PostgresqlConfigurationToDeployment extends Deployment {
    public PostgresqlConfigurationToDeployment(PostgresqlDeploymentConfiguration configuration) {
        super(createBuilderForConfiguration(configuration));
    }

    private static Deployment.Builder createBuilderForConfiguration(PostgresqlDeploymentConfiguration configuration) {
        final Service postgresService = new PostgresConfigurationToService(configuration.getPostgresqlConfiguration());
        final Service pgAdminService = new PgAdminConfigurationToService(configuration.getPgAdminConfiguration());

        final NamedVolume databaseNamedVolume = new NamedVolume("database-data");
        final NamedVolume pgadminNamedVolume = new NamedVolume("pgadmin-data");

        return Deployment.newBuilder()
                .addServices(postgresService, pgAdminService)
                .addNamedVolumes(databaseNamedVolume, pgadminNamedVolume);
    }
}