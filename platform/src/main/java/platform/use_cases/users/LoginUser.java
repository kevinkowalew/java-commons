package platform.use_cases.users;

import platform.entities.User;
import platform.exceptions.InvalidCredentialsException;
import platform.exceptions.InvalidPasswordException;
import platform.exceptions.NonexistentEntityException;
import platform.use_cases.port.repositories.UserRepository;
import platform.use_cases.port.util.Encoder;
import platform.use_cases.port.util.Validator;
import platform.utilities.EmailValidator;
import platform.utilities.NullValidator;

public class LoginUser {
	private final UserRepository repository;
	private final Encoder passwordEncoder;
	private final Validator<String> passwordValidator;

	public LoginUser(final UserRepository repository, final Encoder passwordEncoder,
			final Validator<String> passwordValidator) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.passwordValidator = passwordValidator;
	}

	public User login(final String email, final String password) {
		NullValidator.validate(email, password);
		EmailValidator.validate(email);

		if (!passwordValidator.validate(password)) {
			throw new InvalidPasswordException();
		}

		final User user = repository.findUserWithEmail(email).orElseThrow(NonexistentEntityException::new);
		if (user.getPassword() != passwordEncoder.encode(password)) {
			throw new InvalidCredentialsException();
		} else {
			return stripPassword(user);
		}
	}

	private User stripPassword(final User user) {
		return User.builder().setId(user.getId()).setEmail(user.getEmail()).setName(user.getName())
				.setAvatarUrl(user.getAvatarUrl()).build();
	}
}
