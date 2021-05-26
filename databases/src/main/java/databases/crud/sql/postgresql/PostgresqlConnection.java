package databases.crud.sql.postgresql;

import commons.OptionalProvider;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.sql.Connection;

public class PostgresqlConnection implements OptionalProvider<Connection> {
    private final String url;
    private final String user;
    private final String password;
    private Connection connection = null;

    public PostgresqlConnection(Builder builder) {
        this.url = constructUrl(builder);
        this.user = builder.user;
        this.password = builder.password;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String host = "127.0.0.1";
        private int port = 5432;
        private String databaseName = "";
        private String user = "";
        private String password = "";

        protected Builder() {
        }

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setDatabaseName(String databaseName) {
            this.databaseName = databaseName;
            return this;
        }

        public Builder setUser(String user) {
            this.user = user;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public PostgresqlConnection build() {
            return new PostgresqlConnection(this);
        }

    }

    private String constructUrl(Builder builder) {
        return "jdbc:postgresql://" + builder.host + ":" + builder.port + "/" + builder.databaseName;
    }

    @Override
    public Optional<Connection> get() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            return Optional.of(connection);
        } catch (SQLException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }
}