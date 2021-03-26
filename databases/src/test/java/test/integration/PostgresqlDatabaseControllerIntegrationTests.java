package test.integration;

import com.google.inject.Guice;
import com.google.inject.Injector;
import databases.sql.SqlDatabaseTableController;
import org.junit.Before;
import org.junit.Test;
import test.mocks.MockDatabaseControllerModule;

import static org.junit.Assert.assertFalse;

public class PostgresqlDatabaseControllerIntegrationTests {
    private SqlDatabaseTableController sut;

    @Before
    public void setup() {
        Injector injector = Guice.createInjector(new MockDatabaseControllerModule());
        sut = injector.getInstance(SqlDatabaseTableController.class);
    }

    @Test
    public void test_TableExists_ReturnsTrue_For_Existing_Table() {
        // Arrange...
        sut.createTable();

        // Act...
        boolean tableExists = sut.tableExists();

        // Assert...
        assert(tableExists);
    }

    @Test
    public void test_TableExists_ReturnsFalse_For_Nonexistent_Table() {
        // Arrange...
        sut.dropTable();

        // Act...
        boolean tableExists = sut.tableExists();

        // Assert...
        assertFalse(tableExists);
    }

    @Test
    public void test_CreateTable_HappyPath() {
        // Arrange...
        sut.dropTable();

        // Act...
        boolean success = sut.createTable();

        // Assert...
        assert(success);
    }

    @Test
    public void test_CreateTable_RainyDay_ExistingTable_ReturnsFalse() {
        // Arrange...
        sut.dropTable();

        // Act...
        boolean tableExists = sut.tableExists();

        // Assert...
        assertFalse(tableExists);
    }
}