package tests;

import docker.Formatter;
import docker.components.Deployment;
import docker.components.Service;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatterTests {

    @Test
    public void test_CreateDescriptionForService_HappyPath() {
        // Arrange...
        final Service MOCK_SERVICE = MockFactory.createMockService();

        // Act...
        final String description = Formatter.createDescriptionForService(MOCK_SERVICE);

        // Assert...
        final String expected = Testing.loadResource("expected_service_description.yml");
        assertEquals(description, expected);
    }

    @Test
    public void test_CreateDescriptionForDeployment_HappyPath() throws IOException {
        // Arrange...
        final Deployment mockDeployment = MockFactory.createMockDeployment();

        // Act...
        final String actual = Formatter.createDescriptionForDeployment(mockDeployment);

        // Assert...
        final String expected = Testing.loadResource("expected_deployment_description.yml");
        assertEquals(expected, actual);
    }

}