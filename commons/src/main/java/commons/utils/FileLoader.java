package commons.utils;

import com.google.common.io.Resources;

import java.io.File;
import java.net.URL;
import java.util.Optional;

public class FileLoader {
    public static Optional<File> loadResource(String resourceName) {
        final URL url = Resources.getResource(resourceName);
        final String resourcePath = String.valueOf(url);
        final File file = new File(resourcePath);
        return Optional.of(file);
    }
}
