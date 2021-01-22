package databases.postgresql;

import databases.core.DatabaseRequest;
import databases.core.Deserializer;
import databases.core.DeserializerFactory;
import databases.sql.SqlDatabaseController;
import databases.sql.SqlExecutor;
import databases.sql.SqlStatementFactory;

public class SqlDatabaseControllerPostgresqlFactory implements SqlDatabaseController.AbstractFactory {
    PostgresqlConnection connection;
    DeserializerFactory deserializerFactory;
    SqlStatementFactory statementFactory;

    public SqlDatabaseControllerPostgresqlFactory(PostgresqlConnection connection,
                                                  DeserializerFactory deserializerFactory,
                                                  SqlStatementFactory statementFactory) {
        this.connection = connection;
        this.deserializerFactory = deserializerFactory;
        this.statementFactory = statementFactory;
    }

    @Override
    public SqlExecutor getExecutor() {
        return new PostgresqlExecutor(connection);
    }

    @Override
    public Deserializer getDeserializerForRequest(DatabaseRequest request) {
        return deserializerFactory.getDeserializerForRequest(request);
    }

    @Override
    public String createSqlForDatabaseRequest(DatabaseRequest request) {
        return statementFactory.createSqlStatementForDatabaseRequest(request);
    }
}

