
package platform.entities;

import org.junit.Test;

import test.mocks.MocksFactory;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

public class ConversationBuilderTests {
	@Test
	public void testConstruction() {
		final String ID = "1304923814";
		final Set<User> USERS = new HashSet<User>();
		USERS.add(MocksFactory.mockUser());
		USERS.add(MocksFactory.anotherMockUser());

		Conversation rv = Conversation.builder().setId(ID).setUsers(USERS).build();

		assertEquals(rv.getId(), ID);
		assertEquals(rv.getUsers(), USERS);
	}
}
