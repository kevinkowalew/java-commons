package docker.components;

import docker.fields.EnvironmentVariable;
import docker.fields.Link;
import docker.fields.Port;
import docker.fields.Volume;
import docker.fields.enums.Restart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Service {
    private final String name;
    private final String image;
    private final Restart restart;
    private List<Port> ports;
    private List<EnvironmentVariable> environmentVariables;
    private List<Volume> volumes;
    private List<Link> links;

    public Service(Builder builder) {
        this.name = builder.name;
        this.image = builder.image;
        this.restart = builder.restart;
        this.ports = builder.ports;
        this.environmentVariables = builder.environmentVariables;
        this.volumes = builder.volumes;
        this.links = builder.links;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return this.name;
    }

    public String getImage() {
        return this.image;
    }

    public Restart getRestart() {
        return restart;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public List<EnvironmentVariable> getEnvironmentVariables() {
        return environmentVariables;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public List<Link> getLinks() {
        return links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service)) return false;
        Service service = (Service) o;
        return Objects.equals(name, service.name) &&
                Objects.equals(image, service.image) &&
                restart == service.restart &&
                Objects.equals(ports, service.ports) &&
                Objects.equals(environmentVariables, service.environmentVariables) &&
                Objects.equals(volumes, service.volumes) &&
                Objects.equals(links, service.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, image, restart, ports, environmentVariables, volumes, links);
    }

    public static class Builder {
        private String name;
        private String image;
        private Restart restart;
        private List<Port> ports = new ArrayList();
        private List<EnvironmentVariable> environmentVariables = new ArrayList();
        private List<Volume> volumes = new ArrayList();
        public List<Link> links = new ArrayList<>();

        private Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setImage(String image) {
            this.image = image;
            return this;
        }

        public Builder setRestart(Restart restart) {
            this.restart = restart;
            return this;
        }

        public Builder setPorts(Port... ports) {
            return appendElementsToListAndReturnBuilder(this.ports, ports);
        }

        public Builder setEnvironmentVariables(EnvironmentVariable... variables) {
            return appendElementsToListAndReturnBuilder(this.environmentVariables, variables);
        }

        public Builder setVolumes(Volume... volumes) {
            return appendElementsToListAndReturnBuilder(this.volumes, volumes);
        }

        public Builder setLinks(Link... links) {
            return appendElementsToListAndReturnBuilder(this.links, links);
        }

        @SafeVarargs
        private <T> Builder appendElementsToListAndReturnBuilder(List<T> existingList, T... elements) {
            existingList.addAll(Arrays.asList(elements));
            return this;
        }

        public Service build() {
            return new Service(this);
        }
    }

}