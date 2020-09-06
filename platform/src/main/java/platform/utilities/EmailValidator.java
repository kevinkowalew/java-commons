package platform.utilities;

import java.util.regex.Pattern;

import platform.exceptions.InvalidEmailException;

public class EmailValidator {
	public static void validate(final String email) {
		if (!isValid(email)) {
			throw new InvalidEmailException();
		}
	}

	private static boolean isValid(final String email) {
		if (email == null) {
			return false;
		} else {
			final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
			final Pattern pat = Pattern.compile(regex);
			return pat.matcher(email).matches();
		}
	}
}
