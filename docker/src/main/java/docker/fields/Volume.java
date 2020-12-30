package docker.fields;

import java.util.Objects;

public class Volume implements Field {
    final private String path;
    final private String mountPoint;

    public Volume(String path, String mountPoint) {
        this.path = path;
        this.mountPoint = mountPoint;
    }

    @Override
    public String getDescription() {
        return "" + path + ":" + mountPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volume volume = (Volume) o;
        return Objects.equals(path, volume.path) && Objects.equals(mountPoint, volume.mountPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, mountPoint);
    }
}
