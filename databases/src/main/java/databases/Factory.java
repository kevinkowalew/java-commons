package databases;

import commons.utils.YamlDeserializer;
import databases.adapters.PostgresqlConfigurationToDeployment;
import databases.postgresql.PostgresqlConfiguration;
import docker.components.Deployment;

import java.io.File;
import java.util.Optional;

public class Factory {
    public static Optional<Deployment> createDeploymentFromFileAtPath(final String path) {
        Optional<PostgresqlConfiguration> configuration = loadFileAtPath(path, PostgresqlConfiguration.class);

        if(configuration.isPresent()) {
            Deployment deployment = new PostgresqlConfigurationToDeployment(configuration.get());
            return Optional.of(deployment);
        } else {
            return Optional.empty();
        }
    }

    public static Deployment createDeploymentFromConfiguration(PostgresqlConfiguration configuration) {
        Deployment deployment = new PostgresqlConfigurationToDeployment(configuration);
        return deployment;
    }

   private static <T> Optional<T> loadFileAtPath(String path, Class tClass) {
        File file = new File(path);
        return YamlDeserializer.deserializeFromFile(file, tClass);
    }
}