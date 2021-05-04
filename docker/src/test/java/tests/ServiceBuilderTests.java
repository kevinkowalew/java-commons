package tests;

import docker.components.Service;
import docker.fields.enums.Restart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceBuilderTests {
    @Test
    public void test_ServiceBuilder_Injections() {
        // Arrange...
        final String NAME = "database";
        final String IMAGE = "postgres";

        // Act...
        final Service service = Service.newBuilder()
                .setName(NAME)
                .setImage(IMAGE)
                .setRestart(Restart.ALWAYS)
                .setPorts(MockFactory.getPorts())
                .setEnvironmentVariables(MockFactory.getEnvironmentVariables())
                .setVolumes(MockFactory.getVolumes())
                .build();

        // Assert...
        assertEquals(NAME, service.getName());
        assertEquals(IMAGE, service.getImage());
        assertEquals(Restart.ALWAYS, service.getRestart());
        Testing.assertEquals(MockFactory.getPorts(), service.getPorts());
        Testing.assertEquals(MockFactory.getEnvironmentVariables(), service.getEnvironmentVariables());
        Testing.assertEquals(MockFactory.getVolumes(), service.getVolumes());
    }
}
