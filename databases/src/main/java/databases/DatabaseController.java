package databases;

public class DatabaseController<Connection extends DatabaseConnection> {
    DatabaseConnection<Connection> databaseConnection;

    public DatabaseController(DatabaseConnection<Connection> databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
}
