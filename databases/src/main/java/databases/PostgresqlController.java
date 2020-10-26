package databases;

public class PostgresqlController extends DatabaseController<PostgresqlConnection> {
    public PostgresqlController(PostgresqlConnection connection) {
        super(connection);
    }
}
