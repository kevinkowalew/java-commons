package testing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Testing {
    /**
     * @param resourceName - desired resource name
     * @return - contents of specified resource with the given name if applicable, otherwise we return an empty string
     */
    public static String loadResource(String resourceName) {
        try {
            Path resourceDirectory = Paths.get("src", "test", "resources");
            String absolutePath = resourceDirectory.toFile().getAbsolutePath();
            return Files.readString(Path.of(absolutePath + "\\" + resourceName));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @param objects - objects we intend to populate the array return value
     * @return a pop
     */
    @SafeVarargs
    public static <T> T[] createArray(final T... objects) {
        T[] returnValue = objects;
        return returnValue;
    }

    /**
     * Generically compares the members in the provided array and list
     *
     * @return True if the contents of the two collections are equal
     */
    public static <T> boolean assertEquals(T[] array, List<T> list) {
        return List.of(array).equals(list);
    }
}
