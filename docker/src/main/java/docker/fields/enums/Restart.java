package docker.fields.enums;

import docker.FieldConstructable;
import docker.fields.Field;

import java.util.Optional;

public enum Restart implements Field, FieldConstructable {
    NO("no"),
    ON_FAILURE("on-failure"),
    UNLESS_STOPPED("unless-stopped"),
    ALWAYS("always");

    private String rawValue;

    Restart(String rawValue) { this.rawValue = rawValue; }

    @Override
    public String getDescription() {
        return rawValue;
    }

    @Override
    public String toString() {
        return rawValue;
    }

    public static Restart fromString(String value) {
        for (Restart field: values()) {
            if (field.rawValue.equals(value)) {
                return field;
            }
        }
        return null;
    }

    public Optional fromField(Field field) {
        return Optional.empty();
    }
}

