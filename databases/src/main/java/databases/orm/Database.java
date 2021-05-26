package databases.orm;

import databases.crud.sql.Column;
import databases.crud.sql.SqlTableController;
import databases.crud.sql.postgresql.statements.DatabaseTableSchema;
import databases.crud.sql.postgresql.statements.builders.DeleteStatement;
import databases.crud.sql.postgresql.statements.builders.InsertStatement;
import databases.crud.sql.postgresql.statements.builders.SelectStatement;
import databases.crud.sql.postgresql.statements.builders.UpdateStatement;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Database<T> implements CrudOperable<T> {
    private final Class<T> tClass;
    private final SqlTableController<T> controller;

    private Database(Class<T> tClass) {
        this.tClass = tClass;
        this.controller = createController();
    }

    public static <T> Database<T> storing(Class<T> model) {
        return new Database<>(model);
    }

    @Override
    public Optional<T> insert(T t) {
        createTableIfNeeded();
        final InsertStatement.Builder builder = createInsertStatementBuilder(t);
        return controller.insert(builder);
    }

    @Override
    public Optional<List<T>> read(Filter... filters) {
        final SelectStatement.Builder builder = SelectStatement.newBuilder(getSchema());
        return controller.read(builder);
    }

    @Override
    public boolean update(T t) {
        final UpdateStatement.Builder builder = UpdateStatement.newBuilder(getSchema());

        getAllPersistedFields().forEach(field -> {
            final String fieldName = field.getName();
            final Column column = Helpers.createColumnForField(field, tClass);
            builder.update(fieldName, column);
        });

        return controller.update(builder);
    }

    private List<Field> getAllPersistedFields() {
        return Helpers.getAllPersistedFieldsForClass(tClass);
    }

    @Override
    public boolean delete(T t) {
        final DeleteStatement.Builder builder = DeleteStatement.newBuilder(getSchema());
        // TODO: implement me
        return controller.delete(builder);
    }

    private void createTableIfNeeded() {
        if (!controller.tableExists()) {
            controller.createTable();
        }
    }

    private InsertStatement.Builder createInsertStatementBuilder(T t) {
        final InsertStatement.Builder builder = controller.insertStatementBuilder();

        getAllPersistedFields().stream()
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
}