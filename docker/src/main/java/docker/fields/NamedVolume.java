package docker.fields;

import java.util.Objects;

public class NamedVolume implements Field {
    private final String name;

    public NamedVolume(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return name + ":";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NamedVolume)) return false;
        NamedVolume that = (NamedVolume) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
