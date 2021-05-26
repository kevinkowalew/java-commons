package test.mocks;

import databases.crud.core.ResultSetDeserializer;

import java.sql.ResultSet;
import java.util.Optional;

import static test.mocks.MockMessageColumn.*;
import static test.mocks.MockMessageColumn.ID;

public class MockMessageDeserializer extends ResultSetDeserializer<MockMessage> {
    @Override
    public Optional<MockMessage> deserializeResultSet(ResultSet resultSet) {
        final Integer id = extractFromResultSet(resultSet, ID, -1);

        final String senderId = extractValueFrom(resultSet, "sender_id").orElse("");
        final String senderEmail = extractValueFrom(resultSet, "sender_email").orElse("");
        final MockUser sender = new MockUser(senderId, senderEmail, null, null);

        final String recipientId = extractValueFrom(resultSet, "recipient_id").orElse("");
        final String recipientEmail = extractValueFrom(resultSet, "recipient_email").orElse("");
        final MockUser recipient = new MockUser(recipientId, recipientEmail, null, null);

        final String text = extractFromResultSet(resultSet, TEXT).orElse("");
        return Optional.of( new MockMessage(id, text, sender, recipient) );
    }

    @Override
    public Class<MockMessage> getGenericClassReference() {
        return MockMessage.class;
    }
}
