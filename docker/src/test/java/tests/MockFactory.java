package tests;

import docker.components.Deployment;
import docker.components.Service;
import docker.fields.EnvironmentVariable;
import docker.fields.NamedVolume;
import docker.fields.Port;
import docker.fields.Volume;
import docker.fields.enums.Restart;
import testing.Testing;

public class MockFactory {
    /**
     * Constants
     */
    private final static Port[] PORT_ARRAY = Testing.createArray(
            new Port(5432, 5432),
            new Port(2345, 2345)
    );

    private final static EnvironmentVariable[] ENV_VAR_ARRAY = Testing.createArray(
            new EnvironmentVariable("key", 1),
            new EnvironmentVariable("yek", 2)
    );

    private final static Volume[] VOLUME_ARRAY = Testing.createArray(
            new Volume("database-data", "/var/lib/postgresql/data/"),
            new Volume("pgadmin-data", "/var/lib/pgadmin/data/")
    );

    private final static NamedVolume[] NAMED_VOLUME_ARRAY = Testing.createArray(
            new NamedVolume("database-data"),
            new NamedVolume("pgadmin-data")
    );


    /**
     * @param name  - desired service name in auto-generated docker-compose file.
     * @param image - desired docker image to use
     * @return Service instance, populated with the provided image name a host of other random configuration values used for teti
     */
    public static Service createServiceWithNameAndImage(String serviceName, String serviceImage) {
        return Service.newBuilder()
                .setName(serviceName)
                .setImage(serviceImage)
                .setRestart(Restart.ALWAYS)
                .setPorts(PORT_ARRAY)
                .setEnvironmentVariables(ENV_VAR_ARRAY)
                .setVolumes(VOLUME_ARRAY)
                .build();
    }

    /**
     * Creates a Service instance with a host of random configuration values
     */
    public static Service createMockService() {
        return createServiceWithNameAndImage("myService", "serviceImage");
    }

    /**
     * Creates a Deployment instance with two mock services and volumes
     */
    public static Deployment createMockDeployment() {
        return Deployment.newBuilder()
                .addServices(
                        createServiceWithNameAndImage("server", "dropwizard:latest"),
                        createServiceWithNameAndImage("database", "postgres"))
                .addNamedVolumes(NAMED_VOLUME_ARRAY)
                .build();
    }

    /**
     * Array Getters
     */
    public static Port[] getPorts() {
        return PORT_ARRAY;
    }

    public static EnvironmentVariable[] getEnvironmentVariables() {
        return ENV_VAR_ARRAY;
    }

    public static Volume[] getVolumes() {
        return VOLUME_ARRAY;
    }
}