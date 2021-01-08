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
    private final String workingDirectory;
    private final String filename;

    public DeploymentController(DeploymentFileWriter writer, String workingDirectory, String filename) {
        this.writer = writer;
        this.workingDirectory = workingDirectory;
        this.filename = filename;
    }

    public boolean deploy() {
        return !writeFileToPath() || !up();
    }

    private boolean writeFileToPath() {
        try {
            return writer.writeToPath(workingDirectory + "/" + filename);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean up() {
        return execute("docker-compose up");
    }

    public boolean stopDeployment() {
        return execute("docker stop $( docker ps ) && docker system prune –af ––volumes");
    }

    private boolean execute(String command) {
        ProcessBuilder pb = new ProcessBuilder(command.split(" "));
        pb.inheritIO();
        pb.directory(new File(workingDirectory));

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
