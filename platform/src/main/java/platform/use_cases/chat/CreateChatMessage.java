package platform.use_cases.chat;

import platform.entities.ChatMessage;
import platform.entities.User;
import platform.exceptions.InvalidRecipientException;
import platform.exceptions.InvalidTextException;
import platform.exceptions.NonexistentEntityException;
import platform.use_cases.port.repositories.ChatMessageRepository;
import platform.use_cases.port.repositories.UserRepository;
import platform.use_cases.port.util.IdGenerator;
import platform.utilities.NullValidator;

class CreateChatMessage {
	final private UserRepository userRepository;
	final private ChatMessageRepository chatMessageRepository;
	final private IdGenerator idGenerator;

	public CreateChatMessage(UserRepository userRepository, ChatMessageRepository chatMessageRepository,
			IdGenerator idGenerator) {
		this.userRepository = userRepository;
		this.chatMessageRepository = chatMessageRepository;
		this.idGenerator = idGenerator;
	}

	public ChatMessage send(ChatMessage message) {
		NullValidator.validate(message);
		NullValidator.validate(message.getSender(), message.getRecipient());

		if (message.getSender().equals(message.getRecipient())) {
			throw new InvalidRecipientException();
		}

		if (!userExists(message.getSender()) || !userExists(message.getRecipient())) {
			throw new NonexistentEntityException();
		}

		if (message.getText() == null || message.getText().isEmpty()) {
			throw new InvalidTextException();
		}

		ChatMessage newMessage = ChatMessage.builder().setId(idGenerator.generate()).setSender(message.getSender())
				.setRecipient(message.getRecipient()).setText(message.getText()).build();

		return chatMessageRepository.save(newMessage);
	}

	private boolean userExists(User user) {
		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			return false;
		}

		return userRepository.userWithEmailExists(user.getEmail());
	}
}
