package docker;

import docker.components.Deployment;
import docker.components.Service;
import docker.fields.Field;
import docker.fields.NamedVolume;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Formatter {
    /*
     * Public Interface
     */
    public static String createDescriptionForDeployment(Deployment deployment) {
        StringBuilder builder = new StringBuilder();

        final String version = surroundObjectWithDoubleQuotes(deployment.getVersion());
        appendValueDescriptionToBuilder(FormatterKey.VERSION, version, builder);

        appendServicesDescription(deployment, builder);
        appendNamedVolumesDescription(deployment, builder);
        return builder.toString();
    }

    public static String createDescriptionForService(Service service) {
        StringBuilder builder = new StringBuilder();

        appendValueDescriptionToBuilder(FormatterKey.IMAGE, service.getImage(), builder);
        appendValueDescriptionToBuilder(FormatterKey.RESTART, service.getRestart(), builder);

        appendListDescriptionToBuilder(FormatterKey.PORTS, service.getPorts(), builder);
        appendListDescriptionToBuilder(FormatterKey.ENVIRONMENT, service.getEnvironmentVariables(), builder);
        appendListDescriptionToBuilder(FormatterKey.VOLUMES, service.getVolumes(), builder);

        return builder.toString();
    }

    private static <T extends Field> void appendListDescriptionToBuilder(FormatterKey key, List<T> list, StringBuilder builder) {
        createListDescription(list).ifPresent(childDescriptions -> {
            boolean includeNewLine = builder.length() > 0;
            final String prefix = createDescriptionPrefix(key, includeNewLine);
            builder.append(prefix + childDescriptions);
        });
    }

    private static String createDescriptionPrefix(FormatterKey key, boolean includeNewLinePrefix) {
        String prefix = includeNewLinePrefix ? "\n" : "";
        return prefix + key.toString() + ":";
    }

    private static void appendServicesDescription(Deployment deployment, StringBuilder builder) {
        if (deployment.getServices().isEmpty())
            return;

        builder.append("\n" + FormatterKey.SERVICES + ":");
        deployment.getServices().forEach(service -> {
            String description = createDescriptionForService(service);
            builder.append(surroundStringWithNewlines(addSpacePrefix(2, service.getName() + ":")));
            builder.append(addSpacePrefix(4, description));
        });
    }

    private static void appendNamedVolumesDescription(Deployment deployment, StringBuilder builder) {
        if (deployment.getNamedVolumes().isEmpty())
            return;

        builder.append("\n" + FormatterKey.VOLUMES + ":\n");

        for (int i = 0; i < deployment.getNamedVolumes().size(); i++) {
            String description = deployment.getNamedVolumes().get(i).getDescription();
            builder.append(addSpacePrefix(2, description) + "\n");
        }
    }

    public static void appendValueDescriptionToBuilder(FormatterKey key, Object value, StringBuilder builder) {
        if (value == null)
            return;

        boolean includeNewlinePrefix = builder.length() > 0;
        builder.append(createDescriptionPrefix(key, includeNewlinePrefix) + " " + value.toString());
    }

    private static <T extends Field> Optional<String> createListDescription(List<T> list) {
        if (list.isEmpty())
            return Optional.empty();

        final String listItemPrefix = "  - ";
        final String childDescriptions = list.stream()
                .map(field -> addNewlinePrefix(listItemPrefix + field.getDescription()))
                .collect(Collectors.joining(""));
        return Optional.of(childDescriptions);
    }

    private static String addSpacePrefix(int numberOfSpaces, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        new BufferedReader(new StringReader(string)).lines()
                .map(line -> " ".repeat(numberOfSpaces) + line + '\n')
                .forEach(stringBuilder::append);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private static String addNewlinePrefix(String string) {
        return "\n" + string;
    }

    private static String surroundStringWithNewlines(String string) {
        return "\n" + string + "\n";
    }

    private static String surroundObjectWithDoubleQuotes(Object object) {
        return "\"" + object + "\"";
    }
}