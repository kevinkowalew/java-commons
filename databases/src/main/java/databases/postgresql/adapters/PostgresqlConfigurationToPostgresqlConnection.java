package databases.postgresql.adapters;


import databases.postgresql.PostgresqlDeploymentConfiguration;
import databases.postgresql.PostgresqlConnection;

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
