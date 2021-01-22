package databases.postgresql;

import databases.core.DeserializerFactory;
import databases.sql.SqlDatabaseController;
import databases.sql.SqlStatementFactory;

public class PostgresqlDatabaseController extends SqlDatabaseController {
    PostgresqlConnection connection;
    DeserializerFactory deserializerFactory;

    public PostgresqlDatabaseController(PostgresqlConnection connection,
                                        SqlStatementFactory sqlStatementFactory,
                                        DeserializerFactory deserializerFactory) {
        super(new SqlDatabaseControllerPostgresqlFactory(connection, deserializerFactory, sqlStatementFactory));
    }

    private PostgresqlDatabaseController(SqlDatabaseController.AbstractFactory factory) {
        super(factory);
    }

}

