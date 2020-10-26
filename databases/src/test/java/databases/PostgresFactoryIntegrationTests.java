package databases;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Optional;

public class PostgresFactoryIntegrationTests {
    @Test
    public void test_PostgresFactory_Returns_EmptyOptional_ForNullFileArgument() {
        Optional<PostgresqlController> controller = PostgresqlFactory.buildControllerFromFile(null);
        Assert.assertFalse(controller.isPresent());
    }

    @Test
    public void test_PostgresFactory_Returns_EmptyOptional_ForNonexistentFileArgument() {
        Optional<PostgresqlController> controller = PostgresqlFactory.buildControllerFromFile(new File("jibarish"));
        Assert.assertFalse(controller.isPresent());
    }

    @Test
    public void test_PostgresFactory_HappyPath() {
      // Arrange
      final File mockConfig = new File("src/main/resources/test-config.yml");

      // Act
      Optional<PostgresqlController> controller = PostgresqlFactory.buildControllerFromFile(mockConfig);

      // Assert
      Assert.assertTrue(controller.isPresent());
      Assert.assertTrue(controller.get().connect());
      Assert.assertTrue(controller.get().isOpen());
   }

   void test() {

   }
}
