package test.mocks;

import databases.core.DatabaseRequest;
import databases.sql.SqlStatementFactory;

import java.util.Optional;

public class MockSqlStatementFactory implements SqlStatementFactory {

    @Override
    public Optional<String> createSqlStatementForRequest(DatabaseRequest request) {
        MockSqlStatementType type = MockSqlStatementType.valueOf(request.getIdentifier());

        switch (type) {
            case CREATE_TABLE:
                return Optional.of(MockSqlStatements.CREATE_TABLE);
            case SELECT_ALL:
                return Optional.of(MockSqlStatements.SELECT_ALL);
            case INSERT_USER:
                return Optional.of(MockSqlStatements.INSERT_USER);
            case DROP_TABLE:
                return Optional.of(MockSqlStatements.DROP_TABLE);
            case TABLE_EXISTS:
                return Optional.of(MockSqlStatements.TABLE_EXISTS);
        }

        return Optional.empty();
    }
}

