package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class PostgresqlConnection implements DatabaseConnection {
	private final String url;
	private final String user;
	private final String password;
	Connection connection = null;

	PostgresqlConnection(PostgresqlConnectionConfiguration configuration) {
		this.url = constructUrl(configuration);
		this.user = configuration.getUser();
		this.password = configuration.getPassword();
	}

	private String constructUrl(PostgresqlConnectionConfiguration configuration) {
		return "jdbc:postgresql://" +
				configuration.getHost()+ ":" +
				configuration.getPort() + "/" +
				configuration.getDatabaseName();
	}

	@Override
	public boolean connect() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			return isOpen();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
			return false;
		}
	}

	@Override
	public boolean isOpen() {
		if (connection == null) { return false; }

		try {
			return connection.isValid(10);
		} catch (SQLException throwables) {
			System.out.println(throwables.getLocalizedMessage());
			return false;
		}
	}
}