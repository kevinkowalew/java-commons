package test.integration;

import databases.core.DatabaseRequest;
import databases.core.DatabaseResponse;
import databases.postgresql.PostgresqlDatabaseFactory;
import databases.sql.SqlDatabaseController;
import test.OperationResult;
import test.mocks.MockDeserializerFactory;
import test.mocks.MockOperationTypeFactory;
import test.mocks.MockPostgresqlDatabaseFactory;
import test.mocks.MockSqlStatementFactory;
import test.mocks.MockSqlStatementType;
import test.mocks.MockUser;

import java.util.List;
import java.util.Optional;

public class PostgresqlIntegrationTestFactory implements SqlDatabaseControllerIntegrationTests.Factory,
        SqlDatabaseControllerIntegrationTests.ErrorCaseOrchestrator {

    @Override
    public SqlDatabaseController getSqlDatabaseController() {
        PostgresqlDatabaseFactory databaseFactory = new MockPostgresqlDatabaseFactory();
        MockSqlStatementFactory statementFactory = new MockSqlStatementFactory();
        MockDeserializerFactory deserializerFactory = new MockDeserializerFactory();
        MockOperationTypeFactory mockOperationTypeFactory = new MockOperationTypeFactory();
        return databaseFactory.createController(statementFactory, deserializerFactory, mockOperationTypeFactory);
    }

    @Override
    public List<DatabaseRequest> getHappyPathRequests() {
        return List.of(
                createRequest(MockSqlStatementType.CREATE_TABLE),
                createRequest(MockSqlStatementType.TABLE_EXISTS),
                createRequest(MockSqlStatementType.INSERT_USER),
                createRequest(MockSqlStatementType.SELECT_ALL),
                createRequest(MockSqlStatementType.DROP_TABLE)
        );
    }

    @Override
    public List<Object> getHappyPathExpectedObjectValues() {
        List<MockUser> EXPECTED_USERS = List.of(new MockUser(0, "Kevin"));
        return List.of(
                OperationResult.SUCCESS,
                true,
                OperationResult.SUCCESS,
                EXPECTED_USERS,
                OperationResult.SUCCESS
        );
    }

    @Override
    public List<DatabaseRequest> getErrorRequests() {
        return List.of(
                createRequest(MockSqlStatementType.CREATE_TABLE),
                createRequest(MockSqlStatementType.INSERT_USER),
                createRequest(MockSqlStatementType.SELECT_ALL),
                createRequest(MockSqlStatementType.DROP_TABLE)
        );
    }

    @Override
    public List<String> getExpectedErrorMessages() {
        return List.of(
                "ERROR: relation \"persons\" already exists",
                "ERROR: relation \"persons\" does not exist",
                "ERROR: relation \"persons\" does not exist",
                "ERROR: table \"persons\" does not exist"
        );
    }


    public void createTable() {
        DatabaseRequest request = createRequest(MockSqlStatementType.CREATE_TABLE);
        Optional<DatabaseResponse> createTableResponse = getSqlDatabaseController().processRequest(request);
    }

    public void dropTable() {
        DatabaseRequest request = createRequest(MockSqlStatementType.DROP_TABLE);
        Optional<DatabaseResponse> createTableResponse = getSqlDatabaseController().processRequest(request);
    }

    private DatabaseRequest createRequest(MockSqlStatementType type) {
        return DatabaseRequest.newBuilder()
                .setIdentifier(type.getIdentifier())
                .build();
    }

    @Override
    public void setupForErrorCaseRequest(DatabaseRequest request) {
        if(request.getIdentifier().equals("CREATE_TABLE")) {
            createTable();
        } else if (request.getIdentifier().equals("DROP_TABLE")) {
            dropTable();
        }
    }

    @Override
    public void cleanupForErrorCaseRequest(DatabaseRequest request) {
        if(request.getIdentifier().equals("CREATE_TABLE")) {
            dropTable();
        }
    }
}
