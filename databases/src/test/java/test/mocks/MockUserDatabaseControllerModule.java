package test.mocks;

import com.google.inject.Guice;
import com.google.inject.Injector;
import databases.sql.Column;
import databases.sql.SqlTableController;
import databases.sql.postgresql.statements.DatabaseTableSchema;

import java.util.Set;


public class MockUserDatabaseControllerModule extends AbstractMockDatabaseControllerModule {
    @Override
    public DatabaseTableSchema getSchema() {
        Set<Column> columnList = Set.of(
                MockUsersColumn.ID,
                MockUsersColumn.EMAIL,
                MockUsersColumn.SALT,
                MockUsersColumn.HASHED_PASSWORD
        );
        return new DatabaseTableSchema("Users", columnList);
    }

    public static SqlTableController<MockUser> createController() {
        Injector injector = Guice.createInjector(new MockUserDatabaseControllerModule());
        final SqlTableController controller =  injector.getInstance(SqlTableController.class);
        controller.setDeserializer(new MockUserDeserializer());
        return controller;
    }
}
