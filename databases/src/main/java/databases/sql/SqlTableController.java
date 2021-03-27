package databases.sql;

import com.google.inject.Inject;
import databases.core.*;
import databases.sql.postgresql.statements.CreateTableStatement;
import databases.sql.postgresql.statements.DatabaseTableSchema;
import databases.sql.postgresql.statements.DropTableStatement;
import databases.sql.postgresql.statements.TableExistsStatement;
import databases.sql.postgresql.statements.builders.InsertStatement;
import databases.sql.postgresql.statements.builders.SelectStatement;
import databases.sql.postgresql.statements.builders.UpdateStatementBuilder;
import databases.sql.postgresql.deserializers.TableExistsDeserializer;

import java.util.*;
import org.slf4j.Logger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Base class for concrete sql data access implementations to subclass
 */
public class SqlTableController implements Database {
    @Inject
    private SqlExecutor executor;

    @Inject
    private DatabaseTableSchema schema;

    @Inject
    private Logger logger;

    public boolean createTable() {
        final Optional<String> statement = CreateTableStatement.create(schema);

        if (statement.isEmpty()) {
            // TODO: Add logging here
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
        try {
            final DatabaseResponse response = executor.executeQuery(query, new TableExistsDeserializer());
            return response.getCastedObject(Boolean.class).orElse(false);
        } catch (Exception e) {
            // TODO: add error logging here
            e.printStackTrace();
            return false;
        }
    }

    public InsertStatement.Builder insertStatementBuilder() {
        return InsertStatement.newBuilder(schema);
    }

    public SelectStatement.Builder selectStatementBuilder() {
        return SelectStatement.newBuilder(schema);
    }

    @Override
    public boolean insert(InsertStatement.Builder builder) {
        try {
            if (insertBuilderIsMissingRequiredFields(builder)) {
                // TODO: Add logging here
            }

            final String statement = builder.build();
            return executeUpdateWithBooleanReturnValue(statement, new SQLUpdateDeserializer());
        } catch (InsertStatement.Builder.Exception e) {
            // TODO: Add logging here
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public <T> Optional<List<T>> read(SelectStatement.Builder builder, Deserializer deserializer, Class<T> tClass) {
        try {
            final String query = builder.build();
            return executeQueryWithListReturnValue(query, deserializer, tClass);
        } catch (Exception e){
            // TODO: Add logging here
            return Optional.empty();
        }
    }

    @Override
    public Boolean update(UpdateStatementBuilder updateStatementBuilder) {
        return false;
    }

    @Override
    public Boolean delete(SelectStatement.Builder builder) {
        return false;
    }

    private Boolean executeUpdateWithBooleanReturnValue(final String statement, final Deserializer deserializer) {
        try {
            final DatabaseResponse response = executor.executeUpdate(statement, deserializer);
            return response.getCastedObject(Boolean.class).orElse(false);
        } catch (Exception e) {
            // TODO: Add logging here
            return false;
        }
    }

    private <T> Optional<List<T>> executeQueryWithListReturnValue(final String query, final Deserializer deserializer, final Class<T> tClass) {
        try {
            Optional<List> response = executor.executeQuery(query, deserializer).getCastedObject(List.class);

            if (response.isEmpty()) {
                return Optional.empty();
            }

            final List<T> returnValue = new ArrayList<>(response.get().size());

            for(Object object : response.get()) {
                if (!tClass.isInstance(object)) {
                    // TODO: add logging here
                    continue;
                } else {
                    returnValue.add(tClass.cast(object));
                }
            }

            return Optional.of(returnValue);
        } catch (Exception e) {
            // TODO: add logging here
            return Optional.empty();
        }
    }

    private Boolean insertBuilderIsMissingRequiredFields(InsertStatement.Builder builder) {
        final Set<Column> insertBuilderRequestsColumns = builder.getColumnValuePairs().stream()
                .map(ColumnValuePair::getColumn)
                .collect(Collectors.toSet());
        return schema.getColumns().stream()
                .filter(Column::isRequired)
                .anyMatch(Predicate.not(insertBuilderRequestsColumns::contains));
    }
}