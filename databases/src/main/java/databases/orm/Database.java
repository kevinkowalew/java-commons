package databases.orm;

import databases.crud.sql.Column;
import databases.crud.sql.SqlTableController;
import databases.crud.sql.postgresql.statements.DatabaseTableSchema;
import databases.crud.sql.postgresql.statements.WhereClause;
import databases.crud.sql.postgresql.statements.builders.DeleteStatement;
import databases.crud.sql.postgresql.statements.builders.InsertStatement;
import databases.crud.sql.postgresql.statements.builders.SelectStatement;
import databases.crud.sql.postgresql.statements.builders.UpdateStatement;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Database<T> implements CrudOperable<T> {
    private final Class<T> tClass;
    private final SqlTableController<T> controller;
    private final List<Database<?>> nestedFieldDatabases;

    private Database(Class<T> tClass) {
        this.tClass = tClass;
        this.controller = createController();
        this.nestedFieldDatabases = createNestedObjectDatabases();
    }

    public static <T> Database<T> storing(Class<T> model) {
        return new Database<>(model);
    }

    @Override
    public Optional<T> insert(T t) {
        createTablesIfNeeded();
        final InsertStatement.Builder builder = createInsertStatementBuilder(t);
        return controller.insert(builder);
    }

    @Override
    public Optional<List<T>> read(Filter... filters) {
        final SelectStatement.Builder builder = SelectStatement.newBuilder(getSchema());

        for (Filter filter : filters) {
            Optional<WhereClause> whereClause = createWhereClauseForFilter(filter);
            whereClause.ifPresent(builder::where);
        }

        return controller.read(builder);
    }

    @Override
    public boolean update(T t, Filter... filters) {
        final UpdateStatement.Builder builder = UpdateStatement.newBuilder(getSchema());


        return controller.update(builder);
    }

    private Set<Column> getColumnsForPersistedFields() {
        return Helpers.getColumnsForObject(tClass);
    }

    private Set<Field> getPersistedPrimitiveFields() {
        List<Field> nestedFieldPrimaryKeys = generateFieldsForNestedObjectPrimaryKeys();
        return Helpers.getPersistedPrimitiveFields(tClass);
    }

    private List<Field> generateFieldsForNestedObjectPrimaryKeys() {
        return Helpers.getPersistedObjectFields(tClass);
    }

    @Override
    public boolean delete(T t, Filter... filters) {
        final DeleteStatement.Builder builder = DeleteStatement.newBuilder(getSchema());
        // TODO: implement me
        return controller.delete(builder);
    }

    private void createTablesIfNeeded() {
        if (!controller.tableExists()) {
            controller.createTable();
        }

        nestedFieldDatabases.stream()
                .map
    }

    private InsertStatement.Builder createInsertStatementBuilder(T t) {
        final InsertStatement.Builder builder = controller.insertStatementBuilder();

        getPersistedPrimitiveFields().stream()
                .filter(Predicate.not(Helpers::isPrimaryKey))
                .forEach(field -> {
                    field.setAccessible(true);
                    final Optional<String> valueToInsert = Helpers.extractValueFromField(field, t);

                    if (valueToInsert.isEmpty()) {
                        return;
                    }

                    final Column column = Helpers.createColumnForField(field, tClass);
                    builder.insert(valueToInsert.get(), column);
                });

        return builder;
    }

    public void clearAllData() {
        controller.dropTable(true);
        controller.createTable();
    }

    private DatabaseTableSchema getSchema() {
        return new DatabaseTableSchema(tClass.getName(), Helpers.getColumnsForObject(tClass));
    }

    private SqlTableController<T> createController() {
        final GenericDatabaseControllerModule<T> module = new GenericDatabaseControllerModule<>(this.tClass);
        return module.create();
    }

    private <FieldType> Optional<WhereClause> createWhereClauseForFilter(Filter<T, FieldType> filter) {
        if (filter.getFieldName() == null ||
                filter.getOperator() == null ||
                filter.getExpected() == null ||
                filter.getFieldClassType() == null) {
            return Optional.empty();
        } else {
            final Column column = getColumnForFilter(filter);
            final WhereClause clause = new WhereClause(column, filter.getOperator(), filter.getExpected());
            return Optional.of(clause);
        }
    }

    private Column getColumnForFilter(Filter filter) {
        // TODO: add validation & logging here
        return Column.newBuilder()
                .parentTableName(getClass().getName())
                .type(getColumnTypeForFilter(filter))
                .named(filter.getFieldName())
                .build();
    }

    private Column.Type getColumnTypeForFilter(Filter filter) {
        return Column.Type.VARCHAR_255;
    }

    public Filter.Builder newFilterBuilder() {
        return Filter.newBuilder(tClass);
    }

    private List<Database<?>> createNestedObjectDatabases() {
        return Helpers.getPersistedObjectFields(tClass).stream()
                .map(Object::getClass)
                .map(Database::storing)
                .collect(Collectors.toList());
    }
}