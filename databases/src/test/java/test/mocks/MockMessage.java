package test.mocks;

public class MockMessage {
    private final Integer id;
    private final String senderId;
    private final String recipientId;
    private final String text;

    public MockMessage(Integer id, String senderId, String recipientId, String text) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.text = text;
    }

    public Integer getId() {
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
