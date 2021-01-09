package docker.controllers;

import commons.utils.ShellExcutor;

import java.io.IOException;

public class DeploymentController {
    private final DeploymentFileWriter writer;
    private final String workingDirectory;
    private final String filename;
    private final ShellExcutor shellExecutor;

    public DeploymentController(DeploymentFileWriter writer, String workingDirectory, String filename, ShellExcutor shellExecutor) {
        this.writer = writer;
        this.workingDirectory = workingDirectory;
        this.filename = filename;
        this.shellExecutor = shellExecutor;
    }

    public boolean start() {
        return !writeFileToPath() || !up();
    }

    public boolean stop() {
        return shellExecutor.execute("docker-compose down", workingDirectory);
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
        return shellExecutor.execute("docker-compose up", workingDirectory);
    }

}
