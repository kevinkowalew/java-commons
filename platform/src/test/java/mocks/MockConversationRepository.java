package mocks;

import java.util.Optional;

import platform.entities.Conversation;
import platform.use_cases.port.repositories.ConversationRepository;

public class MockConversationRepository implements ConversationRepository {
	private final Conversation returnValue;

	public MockConversationRepository(Conversation returnValue) {
		this.returnValue = returnValue;
	}

	@Override
	public Optional<Conversation> findConversationWithId(String id) {
		return returnValue != null ? Optional.of(returnValue) : Optional.empty();
	}
}
