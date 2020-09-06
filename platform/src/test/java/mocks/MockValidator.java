package mocks;

import platform.use_cases.port.util.Validator;

public class MockValidator<T> implements Validator<T> {
	private final boolean returnValue;

	public MockValidator(boolean returnValue) {
		this.returnValue = returnValue;
	}

	@Override
	public boolean validate(T field) {
		return this.returnValue;
	}
}
