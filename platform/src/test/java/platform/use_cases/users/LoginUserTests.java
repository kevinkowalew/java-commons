package platform.use_cases.users;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import platform.entities.User;
import platform.exceptions.InvalidCredentialsException;
import platform.exceptions.InvalidEmailException;
import platform.exceptions.InvalidPasswordException;
import platform.exceptions.NonexistentEntityException;
import platform.exceptions.UnexpectedNullArgumentException;
import test.mocks.MockEncoder;
import test.mocks.MockUserRepository;
import test.mocks.MockValidator;
import test.mocks.MocksFactory;

public class LoginUserTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testNullArgumentException() {
		final LoginUser sut = createSut(MocksFactory.mockUser(), true, "");
		thrown.expect(UnexpectedNullArgumentException.class);
		sut.login(null, "sdlfkj");

		thrown.expect(UnexpectedNullArgumentException.class);
		sut.login("asdlkfh", null);
	}

	@Test
	public void testInvalidEmailException() {
		final LoginUser sut = createSut(MocksFactory.mockUser(), true, "");
		thrown.expect(InvalidEmailException.class);
		sut.login(MocksFactory.invalidEmails().get(0), "sdlfkjsdflk");
	}

	@Test
	public void testInvalidPasswordException() {
		final LoginUser sut = createSut(MocksFactory.mockUser(), false, "");
		thrown.expect(InvalidPasswordException.class);
		sut.login(MocksFactory.validEmails().get(0), "sdlfkjsdflk");
	}

	@Test
	public void testNonexistentEntityException() {
		final LoginUser sut = createSut(null, true, "");
		thrown.expect(NonexistentEntityException.class);
		sut.login(MocksFactory.validEmails().get(0), "sdlfkjsdflk");
	}

	@Test
	public void testInvalidCredentialsException() {
		final String encodedPassword = "asd;lfkj";
		final LoginUser sut = createSut(MocksFactory.mockUser(), true, encodedPassword);
		thrown.expect(InvalidCredentialsException.class);
		sut.login(MocksFactory.validEmails().get(0), encodedPassword);
	}

	@Test
	public void testHappyPath() {
		final String password = MocksFactory.mockUser().getPassword();
		final LoginUser sut = createSut(MocksFactory.mockUser(), true, password);
		final User rv = sut.login(MocksFactory.validEmails().get(0), password);
		assertEquals(rv.getId(), MocksFactory.mockUser().getId());
		assertEquals(rv.getEmail(), MocksFactory.mockUser().getEmail());
		assertEquals(rv.getAvatarUrl(), MocksFactory.mockUser().getAvatarUrl());
		assertEquals(rv.getName(), MocksFactory.mockUser().getName());
		assertEquals(rv.getPassword(), "");
	}

	private LoginUser createSut(final User repositoryRv, final boolean validatorRv, final String encoderRv) {
		final MockUserRepository repository = new MockUserRepository(repositoryRv, true);
		final MockValidator<String> validator = new MockValidator<String>(validatorRv);
		final MockEncoder encoder = new MockEncoder(encoderRv);
		return new LoginUser(repository, encoder, validator);
	}
}
