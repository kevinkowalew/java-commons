package docker.controllers;

import docker.components.Deployment;
import docker.components.Service;
import docker.fields.EnvironmentVariable;
import docker.fields.Link;
import docker.fields.NamedVolume;
import docker.fields.Port;
import docker.fields.Volume;
import docker.fields.enums.Restart;

import java.io.File;
import java.io.IOException;

public class DeploymentController {
    private final DeploymentFileWriter writer;
    private final String outputPath;

    public DeploymentController(DeploymentFileWriter writer, String outputPath) {
        this.writer = writer;
        this.outputPath = outputPath;
    }

    public boolean deploy() {
        return !writeFileToPath() || !up();
    }

    private boolean writeFileToPath() {
        try {
            return writer.writeToPath(outputPath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean up() {
        return execute("docker-compose up");
    }

    public boolean down() {
        return execute("docker-compose down && docker volume rm $(docker volume ls -q)");
    }

    private boolean execute(String command) {
        File workingDirectory = new File("");
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.inheritIO();
        pb.directory(workingDirectory);

        try {
            pb.start();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static void main(String[] args) {
    }
}
