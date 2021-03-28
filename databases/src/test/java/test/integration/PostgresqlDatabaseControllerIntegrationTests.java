package test.integration;

import com.google.inject.Guice;
import com.google.inject.Injector;
import databases.sql.Column;
import databases.sql.SqlTableController;
import databases.sql.postgresql.statements.DeleteStatement;
import databases.sql.postgresql.statements.WhereClause;
import databases.sql.postgresql.statements.WhereClauseOperator;
import databases.sql.postgresql.statements.builders.InsertStatement;
import databases.sql.postgresql.statements.builders.SelectStatement;
import org.junit.Before;
import org.junit.Test;
import test.mocks.MockColumns;
import test.mocks.MockDatabaseControllerModule;
import test.mocks.MockUser;
import test.mocks.MockUserDeserializer;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PostgresqlDatabaseControllerIntegrationTests {
    private SqlTableController sut;

    @Before
    public void setup() {
        Injector injector = Guice.createInjector(new MockDatabaseControllerModule());
        sut = injector.getInstance(SqlTableController.class);
    }

    @Test
    public void test_TableExists_ReturnsTrue_For_Existing_Table() {
        // Arrange...
        sut.createTable();

        // Act...
        boolean tableExists = sut.tableExists();

        // Assert...
        assert (tableExists);
        final String tableName = MockDatabaseControllerModule.getSchema().getTableName();
        final String expectedMessage = String.format("Created table with name %s", tableName);
        assertLoggerMessageWasRecorded(expectedMessage);
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
        assert (success);
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

    @Test
    public void test_Insert_and_Read_SunnyDay() {
        // Arrange...
        final InsertStatement.Builder insertBuilder = sut.insertStatementBuilder()
                .insert("154321", MockColumns.ID)
                .insert("john.doe@gmail.com", MockColumns.EMAIL)
                .insert("icsfwef91p2;UF!@PUFP!@P", MockColumns.SALT)
                .insert("wef 0p1q2q1q;lwelq2jeqwjlqkwjl", MockColumns.HASHED_PASSWORD);
        final SelectStatement.Builder selectStatement = sut.selectStatementBuilder();
        sut.dropTable();
        sut.createTable();

        // Act...
        boolean success = sut.insert(insertBuilder);
        Optional<List<MockUser>> results = sut.read(selectStatement, new MockUserDeserializer(), MockUser.class);

        // Assert...
        assert (success);
        assertEquals(results.get().size(), 1);
        MockUser user = results.get().get(0);
        assertEquals("154321", user.getId());
        assertEquals("john.doe@gmail.com", user.getEmail());
        assertEquals("icsfwef91p2;UF!@PUFP!@P", user.getSalt());
        assertEquals("wef 0p1q2q1q;lwelq2jeqwjlqkwjl", user.getHashedPassword());
    }

    @Test
    public void test_Insert_RainyDay_MissingValues() {
        // Arrange...
        final InsertStatement.Builder insertBuilder = sut.insertStatementBuilder()
                .insert("154321", MockColumns.ID)
                .insert("john.doe@gmail.com", MockColumns.EMAIL)
                .insert("icsfwef91p2;UF!@PUFP!@P", MockColumns.SALT);
        final SelectStatement.Builder selectStatement = sut.selectStatementBuilder();

        // Act...
        boolean success = sut.insert(insertBuilder);

        // Assert...
        assertFalse(success);
    }

    @Test
    public void test_Insert_RainyDay_NonexistentColumn() {
        // Arrange...
        final Column missingColumn = Column.with("Missing Column", Column.Type.VARCHAR_255, false);
        final InsertStatement.Builder insertBuilder = sut.insertStatementBuilder()
                .insert("154321", missingColumn);
        final SelectStatement.Builder selectStatement = sut.selectStatementBuilder();

        // Act...
        boolean success = sut.insert(insertBuilder);

        // Assert...
        assertFalse(success);
    }

    @Test
    public void test_Delete_SunnyDay() {
        // Arrange...
        final InsertStatement.Builder insertBuilder = sut.insertStatementBuilder()
                .insert("154321", MockColumns.ID)
                .insert("john.doe@gmail.com", MockColumns.EMAIL)
                .insert("icsfwef91p2;UF!@PUFP!@P", MockColumns.SALT)
                .insert("wef 0p1q2q1q;lwelq2jeqwjlqkwjl", MockColumns.HASHED_PASSWORD);
        final SelectStatement.Builder selectBuilder = sut.selectStatementBuilder();
        final WhereClause clause = new WhereClause(MockColumns.ID, "154321", WhereClauseOperator.EQUALS);
        final DeleteStatement.Builder deleteBuilder = sut.deleteStatementBuilder().where(clause);

        // Act...
        boolean insertSuccess = sut.insert(insertBuilder);
        Optional<List<MockUser>> firstReadResults = sut.read(
                sut.selectStatementBuilder(),
                new MockUserDeserializer(),
                MockUser.class
        );
        boolean deleteSuccess = sut.delete(deleteBuilder);
        Optional<List<MockUser>> secondReadResults = sut.read(
                sut.selectStatementBuilder(),
                new MockUserDeserializer(),
                MockUser.class
        );

        // Assert...
        assert(insertSuccess);
        assert(firstReadResults.isPresent());
        assertEquals(firstReadResults.get().size(), 1);
        assert(deleteSuccess);
        assert(secondReadResults.isPresent());
        assert(secondReadResults.get().isEmpty());
    }

    public void assertLoggerMessageWasRecorded(final String expectedMessage) {
        System.out.println(expectedMessage);
    }
}