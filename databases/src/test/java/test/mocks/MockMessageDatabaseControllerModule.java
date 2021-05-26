package test.mocks;

import com.google.inject.Guice;
import com.google.inject.Injector;
import databases.crud.sql.Column;
import databases.crud.sql.SqlTableController;
import databases.crud.sql.postgresql.statements.DatabaseTableSchema;

import java.util.Set;

public class MockMessageDatabaseControllerModule extends AbstractMockDatabaseControllerModule {
    @Override
    public DatabaseTableSchema getSchema() {
        return MockMessageDatabaseControllerModule.getDatabaseSchema();
    }

    public static DatabaseTableSchema getDatabaseSchema() {
        Set<Column> columnList = Set.of(
                MockMessageColumn.ID,
                MockMessageColumn.SENDER_ID,
                MockMessageColumn.RECIPIENT_ID,
                MockMessageColumn.TEXT
        );
        return new DatabaseTableSchema("Messages", columnList);
    }

    public static SqlTableController<MockMessage> createController() {
        Injector injector = Guice.createInjector(new MockMessageDatabaseControllerModule());
        final SqlTableController controller = injector.getInstance(SqlTableController.class);
        controller.setDeserializer(new MockMessageDeserializer());
        return controller;
    }
}