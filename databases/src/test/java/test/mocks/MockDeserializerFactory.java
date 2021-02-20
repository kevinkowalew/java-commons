package test.mocks;

import databases.core.DatabaseRequest;
import databases.core.DeserializerFactory;
import databases.core.Deserializer;

import java.util.Optional;

public class MockDeserializerFactory implements DeserializerFactory {
    @Override
    public Optional<Deserializer> getDeserializerForRequest(DatabaseRequest request) {
        MockSqlStatementType type = MockSqlStatementType.valueOf(request.getIdentifier());

        switch (type) {
            case SELECT_ALL:
                return Optional.of(new MockUserDeserializer());
            case CREATE_TABLE:
            case INSERT_USER:
            case DROP_TABLE:
                return Optional.of(new MockSqlUpdateDeserializer());
            case TABLE_EXISTS:
                return Optional.of(new MockTableExistsDeserializer());
        }

        return Optional.empty();
    }
}
