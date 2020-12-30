package docker.fields;

import java.util.Objects;

public class EnvironmentVariable implements Field {
    private final String key;
    private final Object value;

    public EnvironmentVariable(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getDescription() {
        return this.key + "=" + this.value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnvironmentVariable that = (EnvironmentVariable) o;
        return Objects.equals(key, that.key) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
