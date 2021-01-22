package databases.sql;

import databases.core.Database;
import databases.core.DatabaseRequest;
import databases.core.DatabaseResponse;
import databases.core.Deserializer;
import databases.core.OperationType;

/**
 * Abstract class for concrete sql data access implementations to subclass
 */
public abstract class SqlDatabaseController implements Database {
    AbstractFactory factory;

    public SqlDatabaseController(AbstractFactory factory) {
        this.factory = factory;
    }

    @Override
    public DatabaseResponse processRequest(DatabaseRequest request) {
        final String sql = factory.createSqlForDatabaseRequest(request);
        final SqlExecutor executor = factory.getExecutor();
        final Deserializer deserializer = factory.getDeserializerForRequest(request);

        if (request.getOperationType() == OperationType.READ) {
            return executor.executeQuery(sql, deserializer);
        } else {
            return executor.executeUpdate(sql, deserializer);
        }
    }

    public interface AbstractFactory {
        SqlExecutor getExecutor();
        Deserializer getDeserializerForRequest(DatabaseRequest request);
        String createSqlForDatabaseRequest(DatabaseRequest request);
    }
}
