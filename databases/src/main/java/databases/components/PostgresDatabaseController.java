package databases.components;


public class PostgresDatabaseController<Model> {
    private final PostgresqlDatabase<Model> database;

    public PostgresDatabaseController(PostgresqlDatabase<Model> database) {
        this.database = database;
    }
}
