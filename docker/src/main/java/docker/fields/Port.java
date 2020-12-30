package docker.fields;

import java.util.Objects;

public class Port implements Field {
    final int hostPort;
    final int containerPort;

    public Port(int hostPort, int containerPort) {
        this.hostPort = hostPort;
        this.containerPort = containerPort;
    }

    @Override
    public String getDescription() {
        return hostPort + ":" + containerPort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Port port = (Port) o;
        return hostPort == port.hostPort && containerPort == port.containerPort;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostPort, containerPort);
    }
}
