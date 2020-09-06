package platform.use_cases.port.repositories;

import java.util.Optional;

import platform.entities.Conversation;

public interface ConversationRepository {
	public Optional<Conversation> findConversationWithId(String id);
}
