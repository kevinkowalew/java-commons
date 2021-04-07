package test.mocks;

//public class MockOperationTypeFactory implements OperationTypeFactory {
//    @Override
//    public Optional<OperationType> getOperationTypeForRequest(DatabaseRequest request) {
//        MockSqlStatementType type = MockSqlStatementType.valueOf(request.getIdentifier());
//
//        switch (type) {
//            case CREATE_TABLE:
//            case INSERT_USER:
//                return Optional.of(OperationType.CREATE);
//            case TABLE_EXISTS:
//            case SELECT_ALL:
//                return Optional.of(OperationType.READ);
//            case DROP_TABLE:
//                return Optional.of(OperationType.DELETE);
//            default:
//                return Optional.empty();
//        }
//    }
//}
