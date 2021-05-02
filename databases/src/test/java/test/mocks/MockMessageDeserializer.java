package test.mocks;

import databases.core.ResultSetDeserializer;

import java.sql.ResultSet;
import java.util.Optional;

import static test.mocks.MockMessageColumn.TEXT;
import static test.mocks.MockUsersColumn.*;

public class MockMessageDeserializer extends ResultSetDeserializer<MockMessage> {
    @Override
    public Optional<MockMessage> deserializeResultSet(ResultSet resultSet) {
        final Integer id = extractFromResultSet(resultSet, ID, -1);
        final String senderId = extractFromResultSet(resultSet, TEXT);
        final String recipientId = extractFromResultSet(resultSet, TEXT);
        final String text = extractFromResultSet(resultSet, TEXT);
        return Optional.of( new MockMessage(id, senderId, recipientId, text));
    }

    @Override
    public Class<MockMessage> getGenericClassReference() {
        return MockMessage.class;
    }
}
