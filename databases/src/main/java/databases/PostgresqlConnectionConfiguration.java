package databases;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.util.Optional;

class PostgresqlConnectionConfiguration {
    private String host;
    private int port;
    private String user;
    private String password;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    private String databaseName;

    private PostgresqlConnectionConfiguration() { }

    String getHost() {
        return host;
    }

    int getPort() { return port; }

    String getUser() {
        return user;
    }

    String getPassword() {
        return password;
    }

    String getDatabaseName() {
        return databaseName;
    }

    Optional<PostgresqlConnectionConfiguration> loadFromYaml(final String filepath) {
        try {
            File file = new File(filepath);
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            PostgresqlConnectionConfiguration configuration = mapper.readValue(file, PostgresqlConnectionConfiguration.class);
            return Optional.of(configuration);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
