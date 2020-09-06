package platform.use_cases.users;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import platform.entities.User;
import platform.exceptions.InvalidEmailException;
import platform.exceptions.UnexpectedNullArgumentException;
import mocks.MockUserRepository;
import mocks.MocksFactory;

public class FindUserTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	// Find By Email
	@Test
	public void testFindByEmailNullArgumentException() throws RuntimeException {
		final FindUser sut = createSut(MocksFactory.mockUser());
		thrown.expect(UnexpectedNullArgumentException.class);
		sut.findUserWithEmail(null);
	}

	@Test
	public void testFindByEmailInvalidEmailException() throws RuntimeException {
		final FindUser sut = createSut(MocksFactory.mockUser());
		final String invalidEmail = MocksFactory.invalidEmails().get(0);
		thrown.expect(InvalidEmailException.class);
		sut.findUserWithEmail(invalidEmail);
	}

	@Test
	public void testFindByEmailHappyPath() throws RuntimeException {
		final FindUser sut = createSut(MocksFactory.mockUser());
		final User rv = sut.findUserWithEmail(MocksFactory.validEmails().get(0));
		assertEquals(MocksFactory.mockUser(), rv);
	}

	// Find By Id
	@Test
	public void testFindByIdNullArgumentException() throws RuntimeException {
		final FindUser sut = createSut(MocksFactory.mockUser());
		thrown.expect(UnexpectedNullArgumentException.class);
		sut.findUserWithId(null);
	}

	@Test
	public void testFindByIdHappyPath() throws RuntimeException {
		final FindUser sut = createSut(MocksFactory.mockUser());
		final User rv = sut.findUserWithId(MocksFactory.validEmails().get(0));
		assertEquals(MocksFactory.mockUser(), rv);
	}

	private FindUser createSut(final User returnValue) {
		final MockUserRepository repo = new MockUserRepository(returnValue, true);
		return new FindUser(repo);
	}
}
