package docker.fields.deserializers;

import docker.fields.Field;

public interface FieldDeserializer {
    Object deserialize(Field field);
}
