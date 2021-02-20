package databases.postgresql;

import databases.core.DeserializerFactory;
import databases.sql.OperationTypeFactory;
import databases.sql.SqlDatabaseController;
import databases.sql.SqlStatementFactory;

public class PostgresqlDatabaseController extends SqlDatabaseController {
    public PostgresqlDatabaseController(PostgresqlConnection connection,
                                        SqlStatementFactory sqlStatementFactory,
                                        DeserializerFactory deserializerFactory,
                                        OperationTypeFactory operationTypeFactory) {
        super(new SqlDatabaseControllerPostgresqlFactory(connection, deserializerFactory, sqlStatementFactory, operationTypeFactory));
    }
}

