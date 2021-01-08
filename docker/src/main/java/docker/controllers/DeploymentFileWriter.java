package docker.controllers;

import docker.Formatter;
import docker.components.Deployment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class DeploymentFileWriter {
    final private Deployment deployment;

    public DeploymentFileWriter(Deployment deployment) {
        this.deployment = deployment;
    }

    public boolean writeToPath(String outputPath) throws IOException {
        String formattedDeployment = Formatter.createDescriptionForDeployment(deployment);
        Path file = Paths.get(outputPath);
        Files.write(file, Collections.singleton(formattedDeployment));
        return new File(outputPath).exists();
    }
}
