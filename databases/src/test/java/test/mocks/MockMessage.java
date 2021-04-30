package test.mocks;

import databases.sql.Column;

public class MockMessage {
    private final String id;
    private final String senderId;
    private final String recipientId;
    private final String text;

    public MockMessage(String id, String senderId, String recipientId, String text) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public String getText() {
        return text;
    }
}
