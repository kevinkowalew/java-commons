package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class PostgresqlConnection implements DatabaseConnection {
	private final String url;
	private final String user;
	private final String password;
	private Connection connection = null;

	private PostgresqlConnection(Builder builder) {
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

		private Builder() {
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
	public void connect() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	@Override
	public boolean isOpen() {
		if (connection == null) {
			return false;
		}

		try {
			return connection.isValid(10);
		} catch (SQLException throwables) {
			System.out.println(throwables.getLocalizedMessage());
			return false;
		}
	}
}