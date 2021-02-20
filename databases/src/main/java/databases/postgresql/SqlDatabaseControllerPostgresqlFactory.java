package databases.postgresql;

import databases.core.DatabaseRequest;
import databases.core.Deserializer;
import databases.core.DeserializerFactory;
import databases.core.OperationType;
import databases.sql.OperationTypeFactory;
import databases.sql.SqlDatabaseController;
import databases.sql.SqlExecutor;
import databases.sql.SqlStatementFactory;

import java.util.Optional;

public class SqlDatabaseControllerPostgresqlFactory implements SqlDatabaseController.AbstractFactory {
    private final PostgresqlConnection connection;
    private final DeserializerFactory deserializerFactory;
    private final SqlStatementFactory statementFactory;
    private final OperationTypeFactory operationTypeFactory;

    public SqlDatabaseControllerPostgresqlFactory(PostgresqlConnection connection,
                                                  DeserializerFactory deserializerFactory,
                                                  SqlStatementFactory statementFactory,
                                                  OperationTypeFactory operationTypeFactory) {
        this.connection = connection;
        this.deserializerFactory = deserializerFactory;
        this.statementFactory = statementFactory;
        this.operationTypeFactory = operationTypeFactory;
    }

    @Override
    public SqlExecutor getExecutor() {
        return new PostgresqlExecutor(connection);
    }

    @Override
    public Optional<Deserializer> getDeserializerForRequest(DatabaseRequest request) {
        return deserializerFactory.getDeserializerForRequest(request);
    }

    @Override
    public Optional<String> createSqlStatementForRequest(DatabaseRequest request) {
        return statementFactory.createSqlStatementForRequest(request);
    }

    @Override
    public Optional<OperationType> getOperationTypeForRequest(DatabaseRequest request) {
        return operationTypeFactory.getOperationTypeForRequest(request);
    }
}

