package mocks;

import platform.use_cases.port.util.Encoder;

public class MockEncoder implements Encoder {
	private final String returnValue;

	public MockEncoder(String returnValue) {
		this.returnValue = returnValue;
	}

	@Override
	public String encode(String string) {
		return this.returnValue;
	}
}
