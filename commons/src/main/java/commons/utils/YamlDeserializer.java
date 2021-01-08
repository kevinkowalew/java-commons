package commons.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.io.Resources;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class YamlDeserializer {
    public static <T> Optional<T> deserializeFromResource(String resourceName, Class tClass) {
        final String path = Resources.getResource(resourceName).getFile();
        final File file = new File(path);
        return deserializeFromFile(file, tClass);
    }

    public static <T> Optional<T> deserializeFromFile(File file, Class tClass) {
        if(file == null || !file.canRead()) {
            return Optional.empty();
        }

        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            T mappedObject = (T) mapper.readValue(file, tClass);
            return Optional.of(mappedObject);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}

