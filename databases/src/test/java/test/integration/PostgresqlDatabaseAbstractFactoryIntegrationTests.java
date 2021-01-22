package test.integration;

import databases.core.DatabaseRequest;
import databases.core.DatabaseResponse;
import databases.core.OperationType;
import databases.postgresql.PostgresqlDatabaseController;
import databases.postgresql.PostgresqlDatabaseFactory;
import org.junit.Before;
import org.junit.Test;
import test.OperationResult;
import test.mocks.MockUser;
import test.mocks.MockDeserializerFactory;
import test.mocks.MockPostgresqlDatabaseFactory;
import test.mocks.MockSqlStatementFactory;
import test.mocks.MockSqlStatementType;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class PostgresqlDatabaseAbstractFactoryIntegrationTests {
    private final List<MockUser> EXPECTED_USERS = Collections.singletonList(new MockUser(0, "Kevin"));
    private final String CREATE_TABLE_ERROR = "ERROR: relation \"persons\" already exists";
    private final String INSERT_USER_ERROR = "ERROR: relation \"persons\" does not exist";
    private final String SELECT_ALL_ERROR = "ERROR: relation \"persons\" does not exist";
    private final String DROP_TABLE_ERROR = "ERROR: relation \"persons\" does not exist";
    private PostgresqlDatabaseController sut;

    @Before
    public void setup() {
        PostgresqlDatabaseFactory databaseFactory = new MockPostgresqlDatabaseFactory();
        MockSqlStatementFactory statementFactory = new MockSqlStatementFactory();
        MockDeserializerFactory deserializerFactory = new MockDeserializerFactory();
        sut = databaseFactory.createController(statementFactory, deserializerFactory);
    }

    @Test
    public void test_HappyPath() {
        // Arrange...
        DatabaseRequest createTableRequest = createRequest(MockSqlStatementType.CREATE_TABLE);
        DatabaseRequest insertUserRequest = createRequest(MockSqlStatementType.INSERT_USER);
        DatabaseRequest tableExistsRequest = createRequest(MockSqlStatementType.TABLE_EXISTS);
        DatabaseRequest selectAllRequest = createRequest(MockSqlStatementType.SELECT_ALL);
        DatabaseRequest dropTableRequest = createRequest(MockSqlStatementType.DROP_TABLE);

        // Act...
        DatabaseResponse createTableResponse = sut.processRequest(createTableRequest);
        DatabaseResponse tableExistsResponse = sut.processRequest(tableExistsRequest);
        DatabaseResponse insertUserResponse = sut.processRequest(insertUserRequest);
        DatabaseResponse selectAllUsersResponse = sut.processRequest(selectAllRequest);
        DatabaseResponse dropTableResponse = sut.processRequest(dropTableRequest);

        // Assert...
        assertResponseObjectEqualsExpected(createTableResponse, OperationResult.SUCCESS);
        assertResponseObjectEqualsExpected(tableExistsResponse, true);
        assertResponseObjectEqualsExpected(insertUserResponse,OperationResult.SUCCESS);
        assertResponseObjectEqualsExpected(selectAllUsersResponse, EXPECTED_USERS);
        assertResponseObjectEqualsExpected(dropTableResponse, OperationResult.SUCCESS);
    }

    @Test
    public void test_ErrorMessages() {
        // Arrange...
        DatabaseRequest createTableRequest = createRequest(MockSqlStatementType.CREATE_TABLE);
        DatabaseRequest insertUserRequest = createRequest(MockSqlStatementType.INSERT_USER);
        DatabaseRequest tableExistsRequest = createRequest(MockSqlStatementType.TABLE_EXISTS);
        DatabaseRequest selectAllRequest = createRequest(MockSqlStatementType.SELECT_ALL);
        DatabaseRequest dropTableRequest = createRequest(MockSqlStatementType.DROP_TABLE);

        // Act...
        DatabaseResponse selectAllUsersResponse = sut.processRequest(selectAllRequest);
        DatabaseResponse dropTableResponse = sut.processRequest(dropTableRequest);
        DatabaseResponse insertUserResponse = sut.processRequest(insertUserRequest);
        DatabaseResponse tableExistsResponse = sut.processRequest(tableExistsRequest);
        createTable();
        DatabaseResponse createTableResponse = sut.processRequest(createTableRequest);
        dropTable();

        // Assert...
//        assertErrorMessageEqualsExpected(createTableResponse, CREATE_TABLE_ERROR);
//        assertErrorMessageEqualsExpected(insertUserResponse, INSERT_USER_ERROR);
        assertErrorMessageEqualsExpected(selectAllUsersResponse, SELECT_ALL_ERROR);
//        assertErrorMessageEqualsExpected(dropTableResponse, DROP_TABLE_ERROR);
    }

    private DatabaseResponse createError(String message) {
        return DatabaseResponse.error()
                .setObject(message)
                .build();
    }

    private void createTable() {
        DatabaseRequest request = createRequest(MockSqlStatementType.CREATE_TABLE);
        DatabaseResponse createTableResponse = sut.processRequest(request);
    }

    private void dropTable() {
        DatabaseRequest request = createRequest(MockSqlStatementType.DROP_TABLE);
        DatabaseResponse ignoredResponse = sut.processRequest(request);
    }

    private DatabaseRequest createRequest(MockSqlStatementType type) {
        return DatabaseRequest.newBuilder()
                .setIdentifier(type.getIdentifier())
                .setOperationType(type.getOperationType())
                .build();
    }

    private <T> void assertResponseObjectEqualsExpected(DatabaseResponse response, T expected) {
        T actual = (T) response.getObject();
        assertEquals(expected, actual);
    }

    private void assertErrorMessageEqualsExpected(DatabaseResponse response, String expected) {
        Optional<String> actual = response.getErrorMessage();

        assert(actual.isPresent());
        assert(actual.get().contains(expected));
    }
}