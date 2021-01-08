package databases.core;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public class Controller<Model> {
    private final Database<Model, ResultSet> database;

    public Controller(Database<Model, ResultSet> database) {
        this.database = database;
    }

    public Optional<List<Model>> processRequest(Request<Model> request) {
        return this.database.processRequest(request);
    }
}
