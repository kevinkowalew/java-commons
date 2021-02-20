package test.mocks;

import commons.utils.YamlDeserializer;
import databases.core.DeserializerFactory;
import databases.postgresql.PostgresqlDatabaseController;
import databases.postgresql.PostgresqlDatabaseFactory;
import databases.postgresql.PostgresqlDeploymentConfiguration;
import databases.sql.OperationTypeFactory;
import databases.sql.SqlStatementFactory;

public class MockPostgresqlDatabaseFactory extends PostgresqlDatabaseFactory {
    private MockPostgresqlDatabaseFactory(PostgresqlDeploymentConfiguration configuration) {
        super(configuration);
    }

    public MockPostgresqlDatabaseFactory() {
        super(getMockConfiguration());
    }

    public static PostgresqlDeploymentConfiguration getMockConfiguration() {
        final String resourceName = "mock-postgresql-config.yml";
        return (PostgresqlDeploymentConfiguration) YamlDeserializer.deserializeFromResource(resourceName, PostgresqlDeploymentConfiguration.class).get();
    }

    public PostgresqlDatabaseController createController(SqlStatementFactory statementFactory,
                                                         DeserializerFactory deserializerFactory,
                                                         OperationTypeFactory operationTypeFactory) {
        return new PostgresqlDatabaseController(createConnection(), statementFactory, deserializerFactory, operationTypeFactory);
    }
}

