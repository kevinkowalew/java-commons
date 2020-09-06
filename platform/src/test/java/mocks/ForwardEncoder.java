package mocks;

import platform.use_cases.port.util.Encoder;

public class ForwardEncoder implements Encoder {
	public ForwardEncoder() {
	}

	@Override
	public String encode(String string) {
		return string;
	}
}
