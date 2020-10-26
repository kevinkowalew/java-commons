package databases;

public interface DatabaseConnection {
	boolean connect();
	boolean isOpen();
}
