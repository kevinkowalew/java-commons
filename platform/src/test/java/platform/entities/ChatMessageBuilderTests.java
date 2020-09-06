package platform.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import mocks.MocksFactory;

public class ChatMessageBuilderTests {
	@Test
	public void testConstruction() {
		final String ID = "12308923";
		final User SENDER = MocksFactory.mockUser();
		final User RECIPIENT = MocksFactory.mockUser();
		final String TEXT = "here's my message text";

		final ChatMessage message = ChatMessage.builder().setId(ID).setSender(SENDER).setRecipient(RECIPIENT)
				.setText(TEXT).build();

		assertEquals(message.getId(), ID);
		assertEquals(message.getSender(), SENDER);
		assertEquals(message.getRecipient(), RECIPIENT);
		assertEquals(message.getText(), TEXT);
	}
}
