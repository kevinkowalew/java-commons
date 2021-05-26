package test.mocks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import platform.entities.ChatMessage;
import platform.entities.Conversation;
import platform.entities.User;

final public class MocksFactory {
	public static User mockUser() {
		return User.builder().setName("John Doe").setEmail("john.doe@gmail.com").setPassword("my-password")
				.setAvatarUrl(
						"https://avatars3.githubusercontent.com/u/9099021?s=460&u=15b32c428a5c6a2a1e291499a1d9d0e9420c8a40&v=4")
				.build();
	}

	public static User anotherMockUser() {
		return User.builder().setName("Jane Doe").setEmail("jane.doe@gmail.com").setPassword("my-password")
				.setAvatarUrl(
						"https://avatars3.githubusercontent.com/u/9099021?s=460&u=15b32c428a5c6a2a1e291499a1d9d0e9420c8a40&v=4")
				.build();
	}

	public static ArrayList<String> invalidEmails() {
		final ArrayList<String> invalidEmails = new ArrayList<String>();
		invalidEmails.add("#@%^%#$@#$@#.com");
		invalidEmails.add("@example.com");
		invalidEmails.add("Joe Smith <email@example.com>");
		invalidEmails.add("email.example.com");
		invalidEmails.add("email@example@example.com");
		invalidEmails.add(".email@example.com");
		invalidEmails.add("email.@example.com");
		invalidEmails.add("email..email@example.com");
		invalidEmails.add("email@example.com (Joe Smith)");
		invalidEmails.add("email@example");
		invalidEmails.add("email@111.222.333.44444");
		invalidEmails.add("email@example..com");
		return invalidEmails;
	}

	public static ArrayList<String> validEmails() {
		final ArrayList<String> validEmails = new ArrayList<>();
		validEmails.add("john.doe@gmail.com");
		validEmails.add("jane.doe@gmail.com");
		validEmails.add("jimi.doe@gmail.com");
		return validEmails;
	}

	public static Conversation mockConversation() {
		final Set<User> users = new HashSet<User>();
		users.add(mockUser());
		users.add(anotherMockUser());
		return Conversation.builder().setId("123098123").setUsers(users).build();
	}

	public static ChatMessage mockMessage() {
		return ChatMessage.builder().setId("123098").setText("what up").setSender(mockUser())
				.setRecipient(anotherMockUser()).build();
	}
}
