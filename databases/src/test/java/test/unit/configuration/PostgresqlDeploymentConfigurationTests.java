package test.unit.configuration;

import com.google.common.io.Resources;
import commons.utils.YamlDeserializer;
import databases.sql.postgresql.configuration.PostgresqlDeploymentConfiguration;
import org.junit.Test;

import java.io.File;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PostgresqlDeploymentConfigurationTests {
    @Test
    public void test_LoadFromResource_ReturnsEmptyOptional_For_NullArgument() {
        // Arrange
        File file = null;

        // Act
        Optional<PostgresqlDeploymentConfiguration> configuration = YamlDeserializer.deserializeFromFile(file, PostgresqlDeploymentConfiguration.class);

        // Assert
        assertFalse(configuration.isPresent());
    }

    @Test
    public void test_LoadFromResource_ReturnsEmptyOptional_For_EmptyStringArgument() {
        // Arrange
        File file = new File("");

        // Act
        Optional<PostgresqlDeploymentConfiguration> configuration = YamlDeserializer.deserializeFromFile(file, PostgresqlDeploymentConfiguration.class);

        // Assert
        assertFalse(configuration.isPresent());
    }

    @Test
    public void test_Deserializes_Successfully_FromYml() {
        // Arrange
        final String pathname = Resources.getResource("mock-postgresql-config.yml").getFile();
        File file = new File(pathname);

        // Act
        Optional<PostgresqlDeploymentConfiguration> configuration = YamlDeserializer.deserializeFromFile(file, PostgresqlDeploymentConfiguration.class);

        // Assert
        assertEquals("172.19.0.3", configuration.get().getPostgresqlConfiguration().getHost());
        assertEquals(5432, configuration.get().getPostgresqlConfiguration().getPort());
        assertEquals("admin", configuration.get().getPostgresqlConfiguration().getUser());
        assertEquals("password", configuration.get().getPostgresqlConfiguration().getPassword());
        assertEquals("postgres", configuration.get().getPostgresqlConfiguration().getDatabaseName());

        assertEquals("admin@gmail.com", configuration.get().getPgAdminConfiguration().getEmail());
        assertEquals("secret", configuration.get().getPgAdminConfiguration().getPassword());
        assertEquals(80, configuration.get().getPgAdminConfiguration().getPort());
    }
}
