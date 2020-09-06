package platform.use_cases.chat;

import platform.entities.Conversation;
import platform.exceptions.NonexistentEntityException;
import platform.use_cases.port.repositories.ConversationRepository;
import platform.utilities.NullValidator;

public class FindConversations {
	final private ConversationRepository repository;

	public FindConversations(ConversationRepository repository) {
		this.repository = repository;
	}

	public Conversation findConversationWithId(String id) {
		NullValidator.validate(id);
		return repository.findConversationWithId(id).orElseThrow(NonexistentEntityException::new);
	}
}
