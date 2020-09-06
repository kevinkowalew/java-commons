package mocks;

import java.util.List;
import java.util.Optional;

import platform.entities.ChatMessage;
import platform.use_cases.port.repositories.ChatMessageRepository;

public class MockChatMessageRepository implements ChatMessageRepository {
	private final ChatMessage returnValue;

	public MockChatMessageRepository(ChatMessage returnValue) {
		this.returnValue = returnValue;
	}

	@Override
	public ChatMessage save(ChatMessage message) {
		return message;
	}

	@Override
	public Optional<ChatMessage> findChatMessageWithId(String id) {
		return returnValue != null ? Optional.of(returnValue) : Optional.empty();
	}

	@Override
	public Optional<List<ChatMessage>> findChatMessagesForConversationId(String id) {
		return returnValue != null ? Optional.of(List.of(returnValue)) : Optional.empty();
	}
}
