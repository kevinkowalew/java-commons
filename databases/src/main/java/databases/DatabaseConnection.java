package databases;

import java.util.Optional;

public interface DatabaseConnection {
	void connect();

	boolean isOpen();

	Optional<String> executeQuery(String query);
}
