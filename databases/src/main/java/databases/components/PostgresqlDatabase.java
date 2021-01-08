package databases.components;

import databases.core.Controller;
import databases.core.Database;
import databases.core.Deserializer;
import databases.core.Executor;
import databases.core.Request;
import databases.core.RequestType;
import databases.core.StatementFactory;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public class PostgresqlDatabase<Model> implements Database<Model, ResultSet> {
    private final Executor executor;
    private final StatementFactory<Model> statementFactory;
    private final Deserializer<ResultSet, Model> deserializer;

    public PostgresqlDatabase(Executor executor, StatementFactory<Model> statementFactory, Deserializer<ResultSet, Model> deserializer) {
        this.executor = executor;
        this.statementFactory = statementFactory;
        this.deserializer = deserializer;
    }

    @Override
    public Optional<List<Model>> processRequest(Request<Model> request) {
        final String statement = statementFactory.createStatementForRequest(request);

        if (request.getType() == RequestType.READ) {
            return executor.execute(statement, new DeserializerToPostgresqlDeserializer<>(deserializer));
        } else {
            boolean success = executor.executeUpdate(statement);
            return Optional.empty();
        }
    }
}

