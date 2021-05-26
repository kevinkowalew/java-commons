package databases.crud.sql.postgresql.configuration;

public class PostgresqlDeploymentConfiguration {
    private PostgresqlConfiguration postgresqlConfiguration;
    private PgAdminConfiguration pgAdminConfiguration;

    public PostgresqlDeploymentConfiguration() {
    }

    public PostgresqlConfiguration getPostgresqlConfiguration() {
        return postgresqlConfiguration;
    }

    private void setPostgresqlConfiguration(PostgresqlConfiguration postgresqlConfiguration) {
        this.postgresqlConfiguration = postgresqlConfiguration;
    }

    public PgAdminConfiguration getPgAdminConfiguration() {
        return pgAdminConfiguration;
    }

    private void setPgAdminConfiguration(PgAdminConfiguration pgAdminConfiguration) {
        this.pgAdminConfiguration = pgAdminConfiguration;
    }

    public class PostgresqlConfiguration {
        private String serviceName;
        private String host;
        private int port;
        private String user;
        private String password;
        private String databaseName;
        private String initScriptName;

        public PostgresqlConfiguration() {
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDatabaseName() {
            return databaseName;
        }

        public void setDatabaseName(String databaseName) {
            this.databaseName = databaseName;
        }

        public String getInitScriptName() {
            return initScriptName;
        }

        public void setInitScriptName(String initScriptName) {
            this.initScriptName = initScriptName;
        }
    }

    public class PgAdminConfiguration {
        private String serviceName;
        private String email;
        private String password;
        private int port;

        public PgAdminConfiguration() {
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

    }
}
