package test.mocks;

import com.google.inject.Guice;
import com.google.inject.Injector;
import databases.crud.sql.Column;
import databases.crud.sql.SqlTableController;
import databases.crud.sql.postgresql.statements.DatabaseTableSchema;

import java.util.Set;

public class MockUserDatabaseControllerModule extends AbstractMockDatabaseControllerModule {
    @Override
    public DatabaseTableSchema getSchema() {
        final Set<Column> columnList = Set.of(
                MockUsersColumn.ID,
                MockUsersColumn.EMAIL,
                MockUsersColumn.SALT,
                MockUsersColumn.HASHED_PASSWORD
        );
        return new DatabaseTableSchema("Users", columnList);
    }

    public static SqlTableController<MockUser> createController() {
        Injector injector = Guice.createInjector(new MockUserDatabaseControllerModule());
        final SqlTableController controller = injector.getInstance(SqlTableController.class);
        controller.setDeserializer(new MockUserDeserializer());
        return controller;
    }
}