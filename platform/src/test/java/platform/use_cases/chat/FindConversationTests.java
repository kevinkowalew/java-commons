package platform.use_cases.chat;

import platform.entities.Conversation;
import platform.exceptions.NonexistentEntityException;
import platform.exceptions.UnexpectedNullArgumentException;
import test.mocks.MockConversationRepository;
import test.mocks.MocksFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FindConversationTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testNullArgumentException() {
		final FindConversations sut = createSut(null);
		thrown.expect(UnexpectedNullArgumentException.class);
		sut.findConversationWithId(null);
	}

	@Test
	public void testNonexistentEntityException() {
		final FindConversations sut = createSut(null);
		thrown.expect(NonexistentEntityException.class);
		sut.findConversationWithId("1203981");
	}

	@Test
	public void testHappyPath() {
		final Conversation expected = MocksFactory.mockConversation();
		final FindConversations sut = createSut(expected);
		final Conversation rv = sut.findConversationWithId("1203981");
		assertEquals(rv.getId(), expected.getId());
		assertTrue(rv.getUsers().equals(expected.getUsers()));
	}

	private FindConversations createSut(final Conversation returnValue) {
		final MockConversationRepository repository = new MockConversationRepository(returnValue);
		return new FindConversations(repository);
	}
}
