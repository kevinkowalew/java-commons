package test.mocks;

import databases.core.Deserializer;
import test.OperationResult;

public class MockSqlUpdateDeserializer implements Deserializer {
    public MockSqlUpdateDeserializer() { }

    @Override
    public Object deserialize(Object response) {
        return OperationResult.SUCCESS;
//        return response.isError() ? OperationResult.FAILURE : OperationResult.SUCCESS;
    }
}

