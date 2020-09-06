package platform.use_cases.chat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import platform.entities.ChatMessage;
import platform.exceptions.NonexistentEntityException;
import platform.exceptions.UnexpectedNullArgumentException;
import mocks.MockChatMessageRepository;
import mocks.MocksFactory;

public class FindChatMessagesTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	// Find by id
	@Test
	public void testFindById_NullArgumentException() {
		final FindChatMessages sut = createSut(null);
		thrown.expect(UnexpectedNullArgumentException.class);
		sut.findChatMessageWithId(null);
	}

	@Test
	public void testFindById_NonexistentEntityException() {
		final FindChatMessages sut = createSut(null);
		thrown.expect(NonexistentEntityException.class);
		sut.findChatMessageWithId("120398");
	}

	@Test
	public void testFindById_HappyPath() {
		final FindChatMessages sut = createSut(MocksFactory.mockMessage());
		ChatMessage rv = sut.findChatMessageWithId("12039812");
		assertEquals(rv, MocksFactory.mockMessage());
	}

	// Find by conversation id
	@Test
	public void testFindForConversation_NullArgumentException() {
		final FindChatMessages sut = createSut(null);
		thrown.expect(UnexpectedNullArgumentException.class);
		sut.findChatMessagesForConversationId(null);
	}

	@Test
	public void testFindForConversation_NonexistentEntity() {
		final FindChatMessages sut = createSut(null);
		thrown.expect(NonexistentEntityException.class);
		sut.findChatMessagesForConversationId("12398721");
	}

	@Test
	public void testFindForConversation_HappyPath() {
		final FindChatMessages sut = createSut(MocksFactory.mockMessage());
		List<ChatMessage> rv = sut.findChatMessagesForConversationId("12398721");
		assertTrue(rv.get(0).equals(MocksFactory.mockMessage()));
	}

	private FindChatMessages createSut(ChatMessage returnValue) {
		final MockChatMessageRepository repository = new MockChatMessageRepository(returnValue);
		return new FindChatMessages(repository);
	}
}
