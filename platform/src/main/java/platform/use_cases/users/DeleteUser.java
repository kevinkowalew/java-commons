package platform.use_cases.users;

import platform.entities.User;
import platform.exceptions.NonexistentEntityException;
import platform.use_cases.port.repositories.UserRepository;
import platform.utilities.EmailValidator;
import platform.utilities.NullValidator;

public class DeleteUser {
	private final UserRepository repository;

	public DeleteUser(final UserRepository repository) {
		this.repository = repository;
	}

	public boolean deleteUserWithEmail(final String email) throws RuntimeException {
		NullValidator.validate(email);
		EmailValidator.validate(email);

		final User user = repository.findUserWithEmail(email).orElseThrow(NonexistentEntityException::new);
		return repository.deleteUserWithEmail(user.getEmail());

	}
}
