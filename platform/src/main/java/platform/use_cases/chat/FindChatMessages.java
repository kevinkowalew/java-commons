package platform.use_cases.chat;

import java.util.List;
import platform.entities.ChatMessage;
import platform.exceptions.NonexistentEntityException;
import platform.use_cases.port.repositories.ChatMessageRepository;
import platform.utilities.NullValidator;

public class FindChatMessages {
	final private ChatMessageRepository repository;

	public FindChatMessages(ChatMessageRepository repository) {
		this.repository = repository;
	}

	public ChatMessage findChatMessageWithId(String id) {
		NullValidator.validate(id);
		return repository.findChatMessageWithId(id).orElseThrow(NonexistentEntityException::new);
	}

	public List<ChatMessage> findChatMessagesForConversationId(String id) {
		NullValidator.validate(id);
		return repository.findChatMessagesForConversationId(id).orElseThrow(NonexistentEntityException::new);
	}
}
