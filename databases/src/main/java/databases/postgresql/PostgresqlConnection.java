package databases.postgresql;

import databases.DatabaseConnection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class PostgresqlConnection implements DatabaseConnection<java.sql.Connection> {
	private String url;
	private String user;
	private String password;
	private java.sql.Connection connection = null;

	protected PostgresqlConnection(Builder builder) {
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

	protected void setUrl(String url) {
		this.url = url;
	}

	protected void setUser(String user) {
		this.user = user;
	}

	protected void setPassword(String password) {
		this.password = password;
	}


	@Override
	public Optional<java.sql.Connection> connect() {
		try {
			Class.forName("org.postgresql.Driver");
			java.sql.Connection connection = DriverManager.getConnection(url, user, password);
			return Optional.of(connection);
		} catch (SQLException | ClassNotFoundException e) {
			return Optional.empty();
		}
	}
}