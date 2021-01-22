package test.mocks;

import databases.core.DatabaseRequest;
import databases.core.DeserializerFactory;
import databases.core.Deserializer;

public class MockDeserializerFactory implements DeserializerFactory {
    @Override
    public Deserializer getDeserializerForRequest(DatabaseRequest request) {
        MockSqlStatementType type = MockSqlStatementType.valueOf(request.getIdentifier());

        switch (type) {
            case SELECT_ALL:
                return new MockUserDeserializer();
            case CREATE_TABLE:
            case INSERT_USER:
            case DROP_TABLE:
                return new MockSqlUpdateDeserializer();
            case TABLE_EXISTS:
                return new MockTableExistsDeserializer();
        }

        return null;
    }
}
