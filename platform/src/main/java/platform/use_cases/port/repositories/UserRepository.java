package platform.use_cases.port.repositories;

import platform.entities.User;

import java.util.Optional;

public interface UserRepository {
	public User save(User user);

	public Optional<User> findUserWithId(String id);

	public Optional<User> findUserWithEmail(String email);

	public boolean deleteUserWithEmail(String email);

	default boolean userWithEmailExists(String email) {
		return findUserWithId(email).isPresent();
	}
}
