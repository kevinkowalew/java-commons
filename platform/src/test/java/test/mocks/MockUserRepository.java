package test.mocks;

import java.util.Optional;

import platform.entities.User;
import platform.use_cases.port.repositories.UserRepository;

public class MockUserRepository implements UserRepository {
	User returnValue;
	boolean shouldReturnRvForFind;

	public MockUserRepository(final User returnValue, final boolean shouldReturnRvForFind) {
		this.returnValue = returnValue;
		this.shouldReturnRvForFind = shouldReturnRvForFind;
	}

	public User save(final User user) {
		return user;
	}

	public Optional<User> findUserWithId(final String id) {
		if (shouldReturnRvForFind) {
			return returnValue != null ? Optional.of(returnValue) : Optional.empty();
		} else {
			return null;
		}
	}

	public Optional<User> findUserWithEmail(final String email) {
		if (shouldReturnRvForFind) {
			return returnValue != null ? Optional.of(returnValue) : Optional.empty();
		} else {
			return null;
		}
	}

	@Override
	public boolean deleteUserWithEmail(final String email) {
		return returnValue != null;
	}
}
