package databases;

public class DatabaseController<Connection extends DatabaseConnection> {
	public Connection connection;

	public DatabaseController(Connection connection) {
		this.connection = connection;
	}

	public boolean isOpen() {
		return this.connection.isOpen();
	}

	public boolean connect() { return this.connection.connect(); }
}
