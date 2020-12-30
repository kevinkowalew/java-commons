package docker.fields.deserializers;

import docker.fields.Field;
import docker.fields.enums.Restart;

public class RestartFieldDeserizalier implements FieldDeserializer {
    @Override
    public Object deserialize(Field field) {
        return Restart.fromString(field.getDescription());
    }
}
