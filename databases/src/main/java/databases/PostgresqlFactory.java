package databases;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.util.Optional;

public class PostgresqlFactory {
    public static Optional<PostgresqlController> buildControllerFromFile(final File file) {
        Optional<PostgresqlConnectionConfiguration> configuration = loadConfigurationFromYaml(file);

        if (!configuration.isPresent()) {
            return Optional.empty();
        }

        PostgresqlConnection connection = new PostgresqlConnection(configuration.get());
        PostgresqlController controller = new PostgresqlController(connection);
        return Optional.of(controller);
    }

    private static Optional<PostgresqlConnectionConfiguration> loadConfigurationFromYaml(final File file) {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            PostgresqlConnectionConfiguration configuration = mapper.readValue(file, PostgresqlConnectionConfiguration.class);
            return Optional.of(configuration);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
