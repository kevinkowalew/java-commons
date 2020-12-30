package docker.components;

import docker.fields.NamedVolume;
import docker.fields.Volume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Deployment {
    private String version;
    private List<Service> services;
    private List<NamedVolume> namedVolumes;

    public Deployment(Builder builder) {
        this.version = builder.version;
        this.services = builder.services;
        this.namedVolumes = builder.namedVolumes;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getVersion() {
        return this.version;
    }

    public List<Service> getServices() {
        return services;
    }

    public List<NamedVolume> getNamedVolumes() {
        return namedVolumes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deployment)) return false;
        Deployment that = (Deployment) o;
        return Objects.equals(version, that.version) && Objects.equals(services, that.services) && Objects.equals(namedVolumes, that.namedVolumes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, services, namedVolumes);
    }

    public static class Builder {
        private String version = "3";
        private List<Service> services = new ArrayList<>();
        private List<NamedVolume> namedVolumes = new ArrayList<>();

        private Builder() {
        }

        public Builder setVersion(String version) {
            this.version = version;
            return this;
        }

        public Builder addServices(Service... services) {
            return appendElementsToListAndReturnBuilder(this.services, services);
        }

        public Builder addNamedVolumes(NamedVolume... namedVolumes) {
            return appendElementsToListAndReturnBuilder(this.namedVolumes, namedVolumes);
        }

        @SafeVarargs
        private <T> Builder appendElementsToListAndReturnBuilder(List<T> existingList, T... elements) {
            existingList.addAll(Arrays.asList(elements));
            return this;
        }

        public Deployment build() {
            return new Deployment(this);
        }
    }
}