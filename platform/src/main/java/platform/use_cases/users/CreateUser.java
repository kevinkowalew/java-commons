package platform.use_cases.users;

import java.util.Optional;

import platform.entities.User;
import platform.exceptions.ExistingUserException;
import platform.exceptions.InvalidPasswordException;
import platform.use_cases.port.repositories.UserRepository;
import platform.use_cases.port.util.Encoder;
import platform.use_cases.port.util.IdGenerator;
import platform.use_cases.port.util.Validator;
import platform.utilities.EmailValidator;
import platform.utilities.NullValidator;

public class CreateUser {
	final private UserRepository repository;
	final private Validator<String> passwordValidator;
	final private Encoder passwordEncoder;
	final private IdGenerator idGenerator;

	public CreateUser(final UserRepository repository, final Validator<String> passwordValidator,
			final Encoder passwordEncoder, final IdGenerator idGenerator) {
		this.repository = repository;
		this.passwordValidator = passwordValidator;
		this.passwordEncoder = passwordEncoder;
		this.idGenerator = idGenerator;
	}

	public User createUser(final User user) throws RuntimeException {
		NullValidator.validate(user);
		EmailValidator.validate(user.getEmail());

		if (!passwordValidator.validate(user.getPassword())) {
			throw new InvalidPasswordException();
		}

		if (userWithEmailExists(user.getEmail())) {
			throw new ExistingUserException();
		}

		final User newUser = User.builder().setId(idGenerator.generate()).setEmail(user.getEmail())
				.setPassword(passwordEncoder.encode(user.getPassword())).setAvatarUrl(user.getAvatarUrl()).build();

		return repository.save(newUser);
	}

	private boolean userWithEmailExists(final String email) {
		final Optional<User> existingUser = repository.findUserWithEmail(email);
		return existingUser != null && existingUser.isPresent();
	}
}
