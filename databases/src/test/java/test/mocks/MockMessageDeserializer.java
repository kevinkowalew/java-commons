package test.mocks;

import databases.core.ResultSetDeserializer;

import java.sql.ResultSet;
import java.util.Optional;

public class MockMessageDeserializer extends ResultSetDeserializer<MockMessage> {
    @Override
    public Optional<MockMessage> deserializeResultSet(ResultSet resultSet) {
        return Optional.empty();
    }

    @Override
    public Class<MockMessage> getGenericClassReference() {
        return MockMessage.class;
    }
}
