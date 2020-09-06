package databases;

import java.util.Optional;

public class DatabaseController {
	final private DatabaseConnection connection;

	private DatabaseController(DatabaseConnection connection) {
		this.connection = connection;
	}

	public static Optional<DatabaseController> connect(DatabaseConnection connection) {
		try {
			DatabaseController controller = new DatabaseController(connection);
			connection.connect();
			return connection.isOpen() ? Optional.of(controller) : Optional.empty();
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public boolean isOpen() {
		return connection.isOpen();
	}

	public Optional<String> execute(String query) {
	    return connection.executeQuery(query);
	}
}
