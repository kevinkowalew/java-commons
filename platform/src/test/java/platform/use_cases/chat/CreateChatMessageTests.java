package platform.use_cases.chat;

import platform.entities.ChatMessage;
import platform.exceptions.InvalidRecipientException;
import platform.exceptions.InvalidTextException;
import platform.exceptions.UnexpectedNullArgumentException;
import mocks.MockChatMessageRepository;
import mocks.MockIdGenerator;
import mocks.MockUserRepository;
import mocks.MocksFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class CreateChatMessageTests {
    @Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testNullArgumentExceptionForMessage() {
		final CreateChatMessage sut = createSut();
		thrown.expect(UnexpectedNullArgumentException.class);
		sut.send(null);
	}

	@Test
	public void testNullArgumentExceptionForSender() {
		final CreateChatMessage sut = createSut();
		thrown.expect(UnexpectedNullArgumentException.class);
		final ChatMessage message = ChatMessage.builder().build();
		sut.send(message);
	}

	@Test
	public void testNullArgumentExceptionForRecipient() {
		final CreateChatMessage sut = createSut();
		thrown.expect(UnexpectedNullArgumentException.class);
		final ChatMessage message = ChatMessage.builder().build();
		sut.send(message);
	}

	@Test
	public void testInvalidRecipientException() {
		final CreateChatMessage sut = createSut();
		final ChatMessage message = ChatMessage.builder().setSender(MocksFactory.mockUser())
				.setRecipient(MocksFactory.mockUser()).build();
		thrown.expect(InvalidRecipientException.class);
		sut.send(message);
	}

	@Test
	public void testInvalidTextException() {
		final CreateChatMessage sut = createSut();
		final ChatMessage message = ChatMessage.builder().setSender(MocksFactory.mockUser())
				.setRecipient(MocksFactory.anotherMockUser()).build();

		thrown.expect(InvalidTextException.class);
		sut.send(message);
	}

	@Test
	public void testHappyPath() {
		final CreateChatMessage sut = createSut();
		final ChatMessage message = ChatMessage.builder().setSender(MocksFactory.mockUser())
				.setRecipient(MocksFactory.anotherMockUser()).setText("what up").build();
		final ChatMessage rv = sut.send(message);

		assertEquals(rv.getSender(), MocksFactory.mockUser());
		assertEquals(rv.getRecipient(), MocksFactory.anotherMockUser());
		assertEquals(rv.getText(), "what up");
		assertEquals(rv.getId(), "120398");
	}

	private CreateChatMessage createSut() {
		final ChatMessage message = ChatMessage.builder().build();
		final MockUserRepository userRepository = new MockUserRepository(MocksFactory.mockUser(), true);
		final MockChatMessageRepository chatMessageRepository = new MockChatMessageRepository(message);
		final MockIdGenerator idGenerator = new MockIdGenerator("120398");
		return new CreateChatMessage(userRepository, chatMessageRepository, idGenerator);
	}
}
