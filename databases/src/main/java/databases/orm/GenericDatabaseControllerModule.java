package databases.orm;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import commons.utils.YamlDeserializer;
import databases.crud.sql.Column;
import databases.crud.sql.SqlExecutor;
import databases.crud.sql.SqlTableController;
import databases.crud.sql.postgresql.PostgresqlConnection;
import databases.crud.sql.postgresql.configuration.PostgresqlDeploymentConfiguration;
import databases.crud.sql.postgresql.configuration.adapters.PostgresqlConfigurationToPostgresqlConnection;
import databases.crud.sql.postgresql.executors.PostgresqlExecutor;
import databases.crud.sql.postgresql.statements.DatabaseTableSchema;

import java.util.Set;
import java.util.stream.Collectors;

public class GenericDatabaseControllerModule<T> extends AbstractModule {
    private final Class<T> tClass;

    public GenericDatabaseControllerModule(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    protected void configure() {
    }

    @Provides
    private DatabaseTableSchema getSchema() {
        final Set<Column> columnList = Helpers.createPersistedFieldColumns(tClass);
        columnList.addAll(Helpers.createPrimaryKeyColumns(tClass));
        final String tableName = tClass.getName();
        return new DatabaseTableSchema(tableName, columnList);
    }

    public SqlTableController<T> create() {
        Injector injector = Guice.createInjector(this);
        final SqlTableController controller = injector.getInstance(SqlTableController.class);
        final GenericResultSetDeserializer<T> deserializer = new GenericResultSetDeserializer<>(tClass);
        controller.setDeserializer(deserializer);
        return controller;
    }

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
}