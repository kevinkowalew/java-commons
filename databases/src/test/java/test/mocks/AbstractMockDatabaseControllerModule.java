package test.mocks;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import commons.utils.YamlDeserializer;
import databases.core.ResultSetDeserializer;
import databases.sql.SqlExecutor;
import databases.sql.SqlTableController;
import databases.sql.postgresql.PostgresqlConnection;
import databases.sql.postgresql.configuration.PostgresqlDeploymentConfiguration;
import databases.sql.postgresql.configuration.adapters.PostgresqlConfigurationToPostgresqlConnection;
import databases.sql.postgresql.executors.PostgresqlExecutor;
import databases.sql.postgresql.statements.DatabaseTableSchema;

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