package platform.entities;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import platform.exceptions.InvalidEmailException;
import test.mocks.MocksFactory;
import platform.utilities.EmailValidator;

public class EmailValidatorTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testInvalidEmails() {
		for (final String invalidEmail : MocksFactory.invalidEmails()) {
			thrown.expect(InvalidEmailException.class);
			EmailValidator.validate(invalidEmail);
		}
	}

	@Test
	public void testValidEmails() {
		for (final String validEmail : MocksFactory.validEmails()) {
			EmailValidator.validate(validEmail);
		}
	}
}
