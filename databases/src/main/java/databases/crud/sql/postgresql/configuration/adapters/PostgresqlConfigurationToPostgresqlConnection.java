package databases.crud.sql.postgresql.configuration.adapters;


import databases.crud.sql.postgresql.PostgresqlConnection;
import databases.crud.sql.postgresql.configuration.PostgresqlDeploymentConfiguration;

public class PostgresqlConfigurationToPostgresqlConnection extends PostgresqlConnection {

    public PostgresqlConfigurationToPostgresqlConnection(PostgresqlDeploymentConfiguration.PostgresqlConfiguration configuration) {
        super(
                PostgresqlConnection.newBuilder()
                        .setHost(configuration.getHost())
                        .setPort(configuration.getPort())
                        .setUser(configuration.getUser())
                        .setPassword(configuration.getPassword())
                        .setDatabaseName(configuration.getDatabaseName())
        );
    }
}
