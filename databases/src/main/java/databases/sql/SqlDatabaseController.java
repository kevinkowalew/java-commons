package databases.sql;

import databases.core.Database;
import databases.core.DatabaseRequest;
import databases.core.DatabaseResponse;
import databases.core.Deserializer;
import databases.core.OperationType;

import java.util.Optional;

/**
 * Abstract class for concrete sql data access implementations to subclass
 */
public abstract class SqlDatabaseController implements Database {
    AbstractFactory factory;

    public SqlDatabaseController(AbstractFactory factory) {
        this.factory = factory;
    }

    @Override
    public Optional<DatabaseResponse> processRequest(DatabaseRequest request) {

        SqlExecutor executor = factory.getExecutor();

        if (executor == null)
            return Optional.empty();

        final Optional<String> sqlStatement = factory.createSqlStatementForRequest(request);

        if (sqlStatement.isEmpty())
            return Optional.empty();

        final Optional<Deserializer> deserializer = factory.getDeserializerForRequest(request);

        if (deserializer.isEmpty())
            return Optional.empty();

        final Optional<OperationType> type = factory.getOperationTypeForRequest(request);

        if (type.isEmpty())
           return Optional.empty();

        DatabaseResponse response = null;
        if (type.get() == OperationType.READ) {
            response = executor.executeQuery(sqlStatement.get(), deserializer.get());
        } else {
            response = executor.executeUpdate(sqlStatement.get(), deserializer.get());
        }

        return Optional.of(response);
    }

    public interface AbstractFactory {
        SqlExecutor getExecutor();
        Optional<Deserializer> getDeserializerForRequest(DatabaseRequest request);
        Optional<String> createSqlStatementForRequest(DatabaseRequest request);
        Optional<OperationType> getOperationTypeForRequest(DatabaseRequest request);
    }
}