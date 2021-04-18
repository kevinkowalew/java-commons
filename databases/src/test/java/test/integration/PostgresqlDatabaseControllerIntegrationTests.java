package test.integration;

import databases.sql.Column;
import databases.sql.SqlTableController;
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

import java.util.List;
import java.util.Optional;

import static databases.sql.postgresql.statements.WhereClauseOperator.EQUALS;
import static org.junit.Assert.*;
import static test.mocks.MockColumns.EMAIL;
import static test.mocks.MockColumns.ID;

public class PostgresqlDatabaseControllerIntegrationTests {
    private static final Integer MOCK_ID_ONE = 1;
    private static final String MOCK_EMAIL_ONE = "john.doe@gmail.com";

    private static final Integer MOCK_ID_TWO = 2;
    private static final String MOCK_EMAIL_TWO = "jane.doe@gmail.com";

    private static final String MOCK_SALT = "icsfwef91p2;UF!@PUFP!@P";
    private static final String MOCK_HASHED_PASSWORD = "wef 0p1q2q1q;lwelq2jeqwjlqkwjl";

    private static SqlTableController<MockUser> sut = MockDatabaseControllerModule.createController();
    private static InsertStatement.Builder VALID_INSERT_STATEMENT_BUILDER = sut.insertStatementBuilder()
            .insert(MOCK_EMAIL_ONE, EMAIL)
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
    public void test_Insert_SunnyDay() {
        // Arrange...
        final InsertStatement.Builder insertBuilder = sut.insertStatementBuilder()
                .insert(MOCK_EMAIL_ONE, EMAIL)
                .insert(MOCK_SALT, MockColumns.SALT)
                .insert(MOCK_HASHED_PASSWORD, MockColumns.HASHED_PASSWORD);
        dropAndRecreateTable();

        // Act...
        Optional<MockUser> user = sut.insert(insertBuilder);

        // Assert...
        assert (user.isPresent());
        assertEquals(MOCK_ID_ONE, user.get().getId());
        assertEquals(MOCK_EMAIL_ONE, user.get().getEmail());
        assertEquals(MOCK_SALT, user.get().getSalt());
        assertEquals(MOCK_HASHED_PASSWORD, user.get().getHashedPassword());
    }


    @Test
    public void test_Insert_SunnyDay_Return_SpecificValues() {
        // Arrange...
        final InsertStatement.Builder insertBuilder = sut.insertStatementBuilder()
                .insert(MOCK_EMAIL_ONE, EMAIL)
                .insert(MOCK_SALT, MockColumns.SALT)
                .insert(MOCK_HASHED_PASSWORD, MockColumns.HASHED_PASSWORD)
                .returning(ID, EMAIL);
        dropAndRecreateTable();

        // Act...
        Optional<MockUser> user = sut.insert(insertBuilder);

        // Assert...
        assert (user.isPresent());
        assertEquals(MOCK_ID_ONE, user.get().getId());
        assertEquals(MOCK_EMAIL_ONE, user.get().getEmail());
        assertEquals("", user.get().getSalt());
        assertEquals("", user.get().getHashedPassword());
    }

    @Test
    public void test_Insert_RainyDay_MissingValues() {
        // Arrange...
        final InsertStatement.Builder insertBuilder = sut.insertStatementBuilder()
                .insert("154321", ID)
                .insert("john.doe@gmail.com", EMAIL)
                .insert("icsfwef91p2;UF!@PUFP!@P", MockColumns.SALT);
        final SelectStatement.Builder selectStatement = sut.selectStatementBuilder();

        // Act...
        boolean success = sut.insert(insertBuilder).isPresent();

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
        boolean success = sut.insert(insertBuilder).isPresent();

        // Assert...
        assertFalse(success);
    }

    @Test
    public void test_Delete_SunnyDay() {
        // Arrange...
        final InsertStatement.Builder insertBuilder = VALID_INSERT_STATEMENT_BUILDER;
        final WhereClause clause = new WhereClause(ID, EQUALS, MOCK_ID_ONE);
        final SelectStatement.Builder selectBuilder = sut.selectStatementBuilder().where(clause);
        final DeleteStatement.Builder deleteBuilder = sut.deleteStatementBuilder().where(clause);

        // Act...
        boolean insertSuccess = sut.insert(insertBuilder).isPresent();
        Optional<List<MockUser>> firstReadResults = sut.read(selectBuilder);
        boolean deleteSuccess = sut.delete(deleteBuilder);
        Optional<List<MockUser>> secondReadResults = sut.read(selectBuilder);

        // Assert...
        assert (insertSuccess);
        assert (firstReadResults.isPresent());
        assertEquals(firstReadResults.get().size(), 1);
        assert (deleteSuccess);
        assert (secondReadResults.isPresent());
        assert (secondReadResults.get().isEmpty());
    }

    @Test
    public void test_Update_SunnyDay() {
        // Arrange...
        final String updatedSalt = "my new salt";
        final String updatedHashedPassword = "my new hashed password";
        final InsertStatement.Builder insertBuilder = VALID_INSERT_STATEMENT_BUILDER;
        final UpdateStatement.Builder updateBuilder = sut.updateStatementBuilder()
                .where(ID, EQUALS, MOCK_ID_ONE)
                .update(MOCK_EMAIL_TWO, EMAIL)
                .update(updatedSalt, MockColumns.SALT)
                .update(updatedHashedPassword, MockColumns.HASHED_PASSWORD);

        // Act...
        dropAndRecreateTable();
        boolean insertSuccess = sut.insert(insertBuilder).isPresent();
        Optional<List<MockUser>> firstReadResults = sut.read(sut.selectStatementBuilder());
        boolean updateSuccess = sut.update(updateBuilder);
        Optional<List<MockUser>> secondReadResults = sut.read(sut.selectStatementBuilder());

        // Assert...
        assertEquals(firstReadResults.get().size(), 1);
        MockUser user = firstReadResults.get().get(0);
        assertEquals(MOCK_ID_ONE, user.getId());
        assertEquals(MOCK_EMAIL_ONE, user.getEmail());
        assertEquals(MOCK_SALT, user.getSalt());
        assertEquals(MOCK_HASHED_PASSWORD, user.getHashedPassword());

        assert (updateSuccess);

        assertEquals(secondReadResults.get().size(), 1);
        user = secondReadResults.get().get(0);
        assertEquals(MOCK_ID_ONE, user.getId());
        assertEquals(MOCK_EMAIL_TWO, user.getEmail());
        assertEquals(updatedSalt, user.getSalt());
        assertEquals(updatedHashedPassword, user.getHashedPassword());
    }

    @Test
    public void test_Read_WhereClause_OR_Handling() {
        // Arrange...
        insertTwoMockUsers();
        final SelectStatement.Builder selectBuilder = sut.selectStatementBuilder()
                .where(EMAIL, EQUALS, MOCK_EMAIL_ONE)
                .or(EMAIL, EQUALS, MOCK_EMAIL_TWO);

        // Act...
        Optional<List<MockUser>> results = sut.read(selectBuilder);

        // Assert...
        assert (results.isPresent());
        assertEquals(2, results.get().size());

        MockUser user = results.get().get(0);
        assertEquals(MOCK_ID_ONE, user.getId());
        assertEquals(MOCK_EMAIL_ONE, user.getEmail());
        assertEquals(MOCK_SALT, user.getSalt());
        assertEquals(MOCK_HASHED_PASSWORD, user.getHashedPassword());

        user = results.get().get(1);
        assertEquals(MOCK_ID_TWO, user.getId());
        assertEquals(MOCK_EMAIL_TWO, user.getEmail());
        assertEquals(MOCK_SALT, user.getSalt());
        assertEquals(MOCK_HASHED_PASSWORD, user.getHashedPassword());
    }

    @Test
    public void test_Read_WhereClause_AND_Handling() {
        // Arrange...
        insertTwoMockUsers();
        final SelectStatement.Builder selectBuilder = sut.selectStatementBuilder()
                .where(ID, EQUALS, "2")
                .and(EMAIL, EQUALS, "jane.doe@gmail.com");

        // Act...
        Optional<List<MockUser>> results = sut.read(selectBuilder);

        // Assert...
        assert (results.isPresent());
        assertEquals(1, results.get().size());
        MockUser user = results.get().get(0);
        assertEquals(MOCK_ID_TWO, user.getId());
        assertEquals(MOCK_EMAIL_TWO, user.getEmail());
        assertEquals(MOCK_SALT, user.getSalt());
        assertEquals(MOCK_HASHED_PASSWORD, user.getHashedPassword());
    }

    private void insertTwoMockUsers() {
        dropAndRecreateTable();
        final InsertStatement.Builder firstInsert = sut.insertStatementBuilder()
                .insert("john.doe@gmail.com", EMAIL)
                .insert(MOCK_SALT, MockColumns.SALT)
                .insert(MOCK_HASHED_PASSWORD, MockColumns.HASHED_PASSWORD);
        final InsertStatement.Builder secondInsert = sut.insertStatementBuilder()
                .insert("jane.doe@gmail.com", EMAIL)
                .insert(MOCK_SALT, MockColumns.SALT)
                .insert(MOCK_HASHED_PASSWORD, MockColumns.HASHED_PASSWORD);
        assert (sut.insert(firstInsert).isPresent());
        assert (sut.insert(secondInsert).isPresent());
    }

    private void dropAndRecreateTable() {
        if (sut.tableExists()) {
            assert (sut.dropTable());
        }
        assert (sut.createTable());
    }

    private void assertLoggerMessageWasRecorded(final String expectedMessage) {
        System.out.println(expectedMessage);
    }
}