package databases;

import java.util.Optional;

public interface DatabaseConnection<T> {
	Optional<T> connect();
}
