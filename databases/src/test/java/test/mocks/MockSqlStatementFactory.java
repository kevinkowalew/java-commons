package test.mocks;

import databases.core.DatabaseRequest;
import databases.sql.SqlStatementFactory;

public class MockSqlStatementFactory implements SqlStatementFactory {

    @Override
    public String createSqlStatementForDatabaseRequest(DatabaseRequest request) {
        MockSqlStatementType type = MockSqlStatementType.valueOf(request.getIdentifier());

        switch (type) {
            case CREATE_TABLE:
                return MockSqlStatements.CREATE_TABLE;
            case SELECT_ALL:
                return MockSqlStatements.SELECT_ALL;
            case INSERT_USER:
                return MockSqlStatements.INSERT_USER;
            case DROP_TABLE:
                return MockSqlStatements.DROP_TABLE;
            case TABLE_EXISTS:
                return MockSqlStatements.TABLE_EXISTS;
        }

        return null;
    }
}

