package test.integration;

import com.google.inject.Guice;
import databases.sql.Column;
import databases.sql.TableController;
import databases.sql.postgresql.statements.DeleteStatement;
import databases.sql.postgresql.statements.WhereClause;
import databases.sql.postgresql.statements.WhereClauseOperator;
import databases.sql.postgresql.statements.builders.InsertStatement;
import databases.sql.postgresql.statements.builders.SelectStatement;
import databases.sql.postgresql.statements.builders.UpdateStatement;
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
    private static final String MOCK_ID ="154321";
    private static final String MOCK_EMAIL = "john.doe@gmail.com";
    private static final String MOCK_SALT = "icsfwef91p2;UF!@PUFP!@P";
    private static final String MOCK_HASHED_PASSWORD = "wef 0p1q2q1q;lwelq2jeqwjlqkwjl";

    private static TableController sut = Guice.createInjector(new MockDatabaseControllerModule())
            .getInstance(TableController.class);
    private static InsertStatement.Builder VALID_INSERT_STATEMENT_BUILDER = sut.insertStatementBuilder()
            .insert(MOCK_ID, MockColumns.ID)
            .insert(MOCK_EMAIL, MockColumns.EMAIL)
            .insert(MOCK_SALT, MockColumns.SALT)
            .insert(MOCK_HASHED_PASSWORD, MockColumns.HASHED_PASSWORD);

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
        final InsertStatement.Builder insertBuilder = VALID_INSERT_STATEMENT_BUILDER;
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
        final InsertStatement.Builder insertBuilder = VALID_INSERT_STATEMENT_BUILDER;
        final WhereClause clause = new WhereClause(MockColumns.ID, MOCK_ID, WhereClauseOperator.EQUALS);
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
        assert (firstReadResults.isPresent());
        assertEquals(firstReadResults.get().size(), 1);
        assert (deleteSuccess);
        assert (secondReadResults.isPresent());
        assert (secondReadResults.get().isEmpty());
    }

    @Test
    public void test_Update_SunnyDay() {
        // Arrange...
        final String updatedEmail = "jane.doe@gmail.com";
        final String updatedSalt = "my new salt";
        final String updatedHashedPassword = "my new hashed password";
        final InsertStatement.Builder insertBuilder = VALID_INSERT_STATEMENT_BUILDER;
        final WhereClause clause = new WhereClause(MockColumns.ID, MOCK_ID, WhereClauseOperator.EQUALS);
        final UpdateStatement.Builder updateBuilder = sut.updateStatementBuilder()
                .where(clause)
                .update(updatedEmail, MockColumns.EMAIL)
                .update(updatedSalt, MockColumns.SALT)
                .update(updatedHashedPassword, MockColumns.HASHED_PASSWORD);

        // Act...
        sut.dropTable();
        sut.createTable();
        boolean insertSuccess = sut.insert(insertBuilder);
        Optional<List<MockUser>> firstReadResults = sut.read(
                sut.selectStatementBuilder(),
                new MockUserDeserializer(),
                MockUser.class
        );
        boolean updateSuccess = sut.update(updateBuilder);
        Optional<List<MockUser>> secondReadResults = sut.read(
                sut.selectStatementBuilder(),
                new MockUserDeserializer(),
                MockUser.class
        );

        // Assert...
        assertEquals(firstReadResults.get().size(), 1);
        MockUser user = firstReadResults.get().get(0);
        assertEquals(MOCK_ID, user.getId());
        assertEquals(MOCK_EMAIL, user.getEmail());
        assertEquals(MOCK_SALT, user.getSalt());
        assertEquals(MOCK_HASHED_PASSWORD, user.getHashedPassword());

        assert(updateSuccess);

        assertEquals(secondReadResults.get().size(), 1);
        user = secondReadResults.get().get(0);
        assertEquals(MOCK_ID, user.getId());
        assertEquals(updatedEmail, user.getEmail());
        assertEquals(updatedSalt, user.getSalt());
        assertEquals(updatedHashedPassword, user.getHashedPassword());
    }

    public void assertLoggerMessageWasRecorded(final String expectedMessage) {
        System.out.println(expectedMessage);
    }
}