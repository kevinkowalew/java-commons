package test.mocks;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import commons.utils.YamlDeserializer;
import databases.sql.Column;
import databases.sql.SqlExecutor;
import databases.sql.postgresql.PostgresqlConnection;
import databases.sql.postgresql.configuration.PostgresqlDeploymentConfiguration;
import databases.sql.postgresql.configuration.adapters.PostgresqlConfigurationToPostgresqlConnection;
import databases.sql.postgresql.executors.PostgresqlExecutor;
import databases.sql.postgresql.statements.DatabaseTableSchema;

import java.util.Set;

public class MockDatabaseControllerModule extends AbstractModule {
    public static PostgresqlDeploymentConfiguration getMockConfiguration() {
        final String resourceName = "mock-postgresql-config.yml";
        return (PostgresqlDeploymentConfiguration) YamlDeserializer.deserializeFromResource(resourceName, PostgresqlDeploymentConfiguration.class).get();
    }

    public static PostgresqlConnection getConnection() {
        return new PostgresqlConfigurationToPostgresqlConnection(getMockConfiguration().getPostgresqlConfiguration());
    }

    @Provides
    public static SqlExecutor getExecutor() {
        return new PostgresqlExecutor(getConnection());
    }

    @Provides
    public static DatabaseTableSchema getSchema() {
        Set<Column> columnList = Set.of(
                Column.with("Id", Column.Type.SERIAL_PRIMARY_KEY, false),
                Column.with("Email", Column.Type.VARCHAR_255, true),
                Column.with("Salt", Column.Type.VARCHAR_255, true),
                Column.with("Hashed Password", Column.Type.VARCHAR_255, true)
        );

        return new DatabaseTableSchema("Users", columnList);
    }

    @Override
    protected void configure() {
    }
}