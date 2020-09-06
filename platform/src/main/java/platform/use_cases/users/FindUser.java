package platform.use_cases.users;

import platform.entities.User;
import platform.exceptions.NonexistentEntityException;
import platform.use_cases.port.repositories.UserRepository;
import platform.utilities.EmailValidator;
import platform.utilities.NullValidator;

public class FindUser {
	final private UserRepository repository;

	public FindUser(final UserRepository repository) {
		this.repository = repository;
	}

	public User findUserWithEmail(final String email) throws RuntimeException {
		NullValidator.validate(email);
		EmailValidator.validate(email);
		return repository.findUserWithEmail(email).orElseThrow(NonexistentEntityException::new);
	}

	public User findUserWithId(final String id) throws RuntimeException {
		NullValidator.validate(id);
		return repository.findUserWithId(id).orElseThrow(NonexistentEntityException::new);
	}
}
