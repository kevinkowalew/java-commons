package test.mocks;

import databases.core.Deserializer;
import test.OperationResult;

public class MockSqlUpdateDeserializer implements Deserializer {
    public MockSqlUpdateDeserializer() { }

    @Override
    public Object deserialize(Object response) {
        if (response.equals(0) || response.equals(1)) {
            return OperationResult.SUCCESS;
        } else {
            return OperationResult.FAILURE;
        }
    }
}

