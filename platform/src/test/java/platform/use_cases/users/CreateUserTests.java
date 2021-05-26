package platform.use_cases.users;

import static junit.framework.TestCase.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import platform.entities.User;
import platform.exceptions.ExistingUserException;
import platform.exceptions.InvalidEmailException;
import platform.exceptions.InvalidPasswordException;
import platform.exceptions.UnexpectedNullArgumentException;
import test.mocks.MockEncoder;
import test.mocks.MockIdGenerator;
import test.mocks.MockUserRepository;
import test.mocks.MockValidator;
import test.mocks.MocksFactory;

public class CreateUserTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testNullArgumentException() throws Exception {
		final CreateUser sut = createSut(null, true, "", "");
		thrown.expect(UnexpectedNullArgumentException.class);
		sut.createUser(null);
	}

	@Test
	public void testInvalidEmailException() throws Exception {
		for (final String invalidEmail : MocksFactory.invalidEmails()) {
			testInvalidEmailException(invalidEmail);
		}
	}

	@Test
	public void testInvalidPasswordException() throws Exception {
		final CreateUser sut = createSut(null, false, "", "");
		thrown.expect(InvalidPasswordException.class);
		sut.createUser(MocksFactory.mockUser());
	}

	@Test
	public void testExistingUserException() throws Exception {
		final CreateUser sut = createSut(MocksFactory.mockUser(), true, "", "");
		thrown.expect(ExistingUserException.class);
		sut.createUser(MocksFactory.mockUser());
	}

	@Test
	public void testHappyPath() throws Exception {
		final String encodedPassword = "encoded-password";
		final String generatedId = "generated-id";
		final CreateUser sut = createSut(MocksFactory.mockUser(), true, encodedPassword, generatedId);

		final User returnValue = sut.createUser(MocksFactory.mockUser());

		assertEquals(returnValue.getPassword(), encodedPassword);
		assertEquals(returnValue.getId(), generatedId);
	}

	private CreateUser createSut(final User repositoryRv, final boolean isValidPasswordRv, final String encoderRv,
			final String generatorRv) {
		return new CreateUser(new MockUserRepository(repositoryRv, encoderRv.isEmpty() && generatorRv.isEmpty()),
				new MockValidator<String>(isValidPasswordRv), new MockEncoder(encoderRv),
				new MockIdGenerator(generatorRv));
	}

	private void testInvalidEmailException(final String email) throws Exception {
		final User user = User.builder().setEmail(email).build();
		final CreateUser sut = createSut(null, true, "", "");
		thrown.expect(InvalidEmailException.class);
		sut.createUser(user);
	}
}
