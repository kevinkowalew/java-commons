package test.integration;

import databases.core.DatabaseRequest;
import databases.core.DatabaseResponse;
import databases.sql.SqlDatabaseController;
import docker.controllers.DeploymentRunner;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SqlDatabaseControllerIntegrationTests {
    private Factory factory;
    private ErrorCaseOrchestrator errorCaseOrchestrator;
    private SqlDatabaseController sut;

    @Before
    public void setup() {
        PostgresqlIntegrationTestFactory postgresql = new PostgresqlIntegrationTestFactory();
        this.factory = postgresql;
        this.errorCaseOrchestrator = postgresql;
        sut = factory.getSqlDatabaseController();
    }

    @Test
    public void test_HappyPath() {
        // Arrange...
        List<DatabaseRequest> requests = factory.getHappyPathRequests();

        // Act...
        List<Object> actualResponseObjects = requests.stream()
                .map(sut::processRequest)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(DatabaseResponse::getObject)
                .collect(Collectors.toList());

        // Assert...
        List<Object> expectedObjectValues = factory.getHappyPathExpectedObjectValues();
        assertEquals(actualResponseObjects, expectedObjectValues);
    }

    @Test
    public void test_ErrorMessages() {
        // Arrange...
        List<DatabaseRequest> requests = factory.getErrorRequests();

        // Act...
        List<String> actualErrorMessages = requests.stream()
                .map(this::executeErrorRequest)
                .map(response -> response.getErrorMessage().orElse(""))
                .collect(Collectors.toList());

        // Assert...
        List<String> expectedErrorMessages = factory.getExpectedErrorMessages();
        for(int i = 0; i < actualErrorMessages.size(); i++) {
            assertTrue(actualErrorMessages.get(i).contains(expectedErrorMessages.get(i)));
        }
    }

    private DatabaseResponse executeErrorRequest(DatabaseRequest request) {
        errorCaseOrchestrator.setupForErrorCaseRequest(request);
        Optional<DatabaseResponse> response = sut.processRequest(request);
        errorCaseOrchestrator.cleanupForErrorCaseRequest(request);
        return response.get();
    }

    interface Factory {

        SqlDatabaseController getSqlDatabaseController();

        List<DatabaseRequest> getHappyPathRequests();

        List<Object> getHappyPathExpectedObjectValues();

        List<DatabaseRequest> getErrorRequests();

        List<String> getExpectedErrorMessages();
    }

    interface ErrorCaseOrchestrator {
        void setupForErrorCaseRequest(DatabaseRequest request);

        void cleanupForErrorCaseRequest(DatabaseRequest request);
    }
}