package databases.sql;

import com.google.inject.Inject;
import databases.core.*;
import databases.sql.postgresql.statements.CreateTableStatement;
import databases.sql.postgresql.statements.DatabaseTableSchema;
import databases.sql.postgresql.statements.DropTableStatement;
import databases.sql.postgresql.statements.TableExistsStatement;
import databases.sql.postgresql.statements.builders.InsertStatementBuilder;
import databases.sql.postgresql.statements.builders.SelectStatementBuilder;
import databases.sql.postgresql.statements.builders.UpdateStatementBuilder;
import databases.sql.postgresql.deserializers.TableExistsDeserializer;

import java.util.Optional;

/**
 * Base class for concrete sql data access implementations to subclass
 */
public class SqlDatabaseTableController implements Database {
    @Inject
    private SqlExecutor executor;

    @Inject
    private DatabaseTableSchema schema;

    public boolean createTable() {
        final Optional<String> statement = CreateTableStatement.create(schema);

        if (statement.isEmpty()) {
            return false;
        } else {
            return executeUpdateWithBooleanReturnValue(statement.get(), new SQLUpdateDeserializer());
        }
    }

    public boolean dropTable() {
        final String statement = DropTableStatement.create(schema.getTableName());
        return executeUpdateWithBooleanReturnValue(statement, new SQLUpdateDeserializer());
    }

    public boolean tableExists() {
        final String query = TableExistsStatement.create(schema.getPostgresqlSchemaName(), schema.getTableName());
        return executeQuery(query, new TableExistsDeserializer(), Boolean.class).orElse(false);
    }

    @Override
    public Optional<Object> insert(InsertStatementBuilder insertStatementBuilder) {
        return Optional.empty();
    }

    @Override
    public Optional<Object> read(SelectStatementBuilder selectStatementBuilder) {
        return Optional.empty();
    }

    @Override
    public Optional<Object> update(UpdateStatementBuilder updateStatementBuilder) {
        return Optional.empty();
    }

    @Override
    public Optional<Object> delete(SelectStatementBuilder selectStatementBuilder) {
        return Optional.empty();
    }


    private Boolean executeUpdateWithBooleanReturnValue(final String statement, final Deserializer deserializer) {
        try {
            final DatabaseResponse response = executor.executeUpdate(statement, deserializer);
            return response.getCastedObject(Boolean.class).orElse(false);
        } catch (Exception e) {
            return false;
        }
    }

    private <T> Optional<T> executeQuery(final String query, final Deserializer deserializer, final Class<T> tClass) {
        try {
            return executor.executeQuery(query, deserializer).getCastedObject(tClass);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}