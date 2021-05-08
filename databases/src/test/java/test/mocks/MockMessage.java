package test.mocks;

public class MockMessage {
    private final Integer id;
    private final String text;
    private final MockUser sender;
    private final MockUser recipient;

    public MockMessage(Integer id, String text, MockUser sender, MockUser recipient) {
        this.id = id;
        this.text = text;
        this.sender = sender;
        this.recipient = recipient;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public MockUser getSender() {
        return sender;
    }

    public MockUser getRecipient() {
        return recipient;
    }
}
