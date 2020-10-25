package databases;

public interface DatabaseConnection {
	void connect();

	boolean isOpen();
}
