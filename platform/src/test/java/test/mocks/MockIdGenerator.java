package test.mocks;

import platform.use_cases.port.util.IdGenerator;

public class MockIdGenerator implements IdGenerator {
    private String returnValue;

    public MockIdGenerator(String returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public String generate() {
        return returnValue;
    }
}
