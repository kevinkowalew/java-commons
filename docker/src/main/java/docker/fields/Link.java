package docker.fields;

import java.util.Objects;

public class Link implements Field {
    private final String volumeOne;
    private final String volumeTwo;

    public Link(String volumeOne, String volumeTwo) {
        this.volumeOne = volumeOne;
        this.volumeTwo = volumeTwo;
    }

    @Override
    public String getDescription() {
        return volumeOne + ":" + volumeTwo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(volumeOne, link.volumeOne) && Objects.equals(volumeTwo, link.volumeTwo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volumeOne, volumeTwo);
    }
}
