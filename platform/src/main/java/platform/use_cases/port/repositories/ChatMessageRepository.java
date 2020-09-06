package platform.use_cases.port.repositories;

import java.util.List;
import java.util.Optional;

import platform.entities.ChatMessage;

public interface ChatMessageRepository {
	public ChatMessage save(ChatMessage message);

	public Optional<ChatMessage> findChatMessageWithId(String id);

	public Optional<List<ChatMessage>> findChatMessagesForConversationId(String id);
}
