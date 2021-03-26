package databases.sql.postgresql.configuration.adapters;


import databases.sql.postgresql.PostgresqlConnection;
import databases.sql.postgresql.configuration.PostgresqlDeploymentConfiguration;

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
