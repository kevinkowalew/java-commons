package platform.utilities;

import platform.exceptions.UnexpectedNullArgumentException;

public class NullValidator {
	public static void validate(final Object... objects) {
		for (Object object : objects) {
			if (object == null) {
				throw new UnexpectedNullArgumentException();
			}
		}
	}
}
