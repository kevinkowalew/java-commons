package databases.postgresql;

import databases.core.DeserializerFactory;
import databases.postgresql.adapters.PostgresqlConfigurationToDeployment;
import databases.postgresql.adapters.PostgresqlConfigurationToPostgresqlConnection;
import databases.sql.OperationTypeFactory;
import databases.sql.SqlStatementFactory;
import docker.components.Deployment;

public class PostgresqlDatabaseFactory {
    private final PostgresqlDeploymentConfiguration configuration;

    public PostgresqlDatabaseFactory(PostgresqlDeploymentConfiguration configuration) {
        this.configuration = configuration;
    }

    public Deployment createDeployment() {
        return new PostgresqlConfigurationToDeployment(configuration);
    }

    public PostgresqlDatabaseController createController(final SqlStatementFactory statementFactory,
                                                         final DeserializerFactory deserializerFactory,
                                                         final OperationTypeFactory operationTypeFactory) {
        return new PostgresqlDatabaseController(createConnection(), statementFactory, deserializerFactory, operationTypeFactory);
    }

    public PostgresqlConnection createConnection() {
        return new PostgresqlConfigurationToPostgresqlConnection(configuration.getPostgresqlConfiguration());
    }
}
