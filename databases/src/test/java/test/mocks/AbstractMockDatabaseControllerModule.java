package test.mocks;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import commons.utils.YamlDeserializer;
import databases.crud.sql.SqlExecutor;
import databases.crud.sql.postgresql.PostgresqlConnection;
import databases.crud.sql.postgresql.configuration.PostgresqlDeploymentConfiguration;
import databases.crud.sql.postgresql.configuration.adapters.PostgresqlConfigurationToPostgresqlConnection;
import databases.crud.sql.postgresql.executors.PostgresqlExecutor;
import databases.crud.sql.postgresql.statements.DatabaseTableSchema;

public abstract class AbstractMockDatabaseControllerModule extends AbstractModule {
    public static PostgresqlDeploymentConfiguration getMockConfiguration() {
        final String resourceName = "mock-postgresql-config.yml";
        return (PostgresqlDeploymentConfiguration) YamlDeserializer.deserializeFromResource(resourceName, PostgresqlDeploymentConfiguration.class).get();
    }

    public static PostgresqlConnection getConnection() {
        return new PostgresqlConfigurationToPostgresqlConnection(getMockConfiguration().getPostgresqlConfiguration());
    }

    @Provides
    public SqlExecutor getExecutor() {
        return new PostgresqlExecutor(getConnection());
    }

    @Provides
    public abstract DatabaseTableSchema getSchema();

    @Override
    protected void configure() {
    }
}