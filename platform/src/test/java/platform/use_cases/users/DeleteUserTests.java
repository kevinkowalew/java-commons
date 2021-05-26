package platform.use_cases.users;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import platform.entities.User;
import platform.exceptions.InvalidEmailException;
import platform.exceptions.NonexistentEntityException;
import platform.exceptions.UnexpectedNullArgumentException;
import test.mocks.MockUserRepository;
import test.mocks.MocksFactory;

public class DeleteUserTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testEmailNullArgumentException() throws RuntimeException {
		final DeleteUser sut = createSut(MocksFactory.mockUser());
		thrown.expect(UnexpectedNullArgumentException.class);
		sut.deleteUserWithEmail(null);
	}

	@Test
	public void testInvalidEmailException() throws RuntimeException {
		final DeleteUser sut = createSut(MocksFactory.mockUser());
		thrown.expect(InvalidEmailException.class);
		sut.deleteUserWithEmail(MocksFactory.invalidEmails().get(0));
	}

	@Test
	public void testNonexistentAccountArgument() throws RuntimeException {
		final DeleteUser sut = createSut(null);
		thrown.expect(NonexistentEntityException.class);
		sut.deleteUserWithEmail(MocksFactory.validEmails().get(0));
	}

	@Test
	public void testHappyPath() throws RuntimeException {
		final DeleteUser sut = createSut(MocksFactory.mockUser());
		final boolean result = sut.deleteUserWithEmail(MocksFactory.validEmails().get(0));
		assertTrue(result);
	}

	private DeleteUser createSut(final User repositoryRv) {
		final MockUserRepository repo = new MockUserRepository(repositoryRv, true);
		return new DeleteUser(repo);

	}
}
