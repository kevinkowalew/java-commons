package docker.fields.deserializers;

import docker.fields.Field;

public class StringDeserializer implements FieldDeserializer {
    @Override
    public String deserialize(Field input) {
        return input.getDescription();
    }
}
