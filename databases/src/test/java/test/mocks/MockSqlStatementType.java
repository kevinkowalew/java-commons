package test.mocks;

import databases.core.OperationType;
import jdk.dynalink.Operation;

public enum MockSqlStatementType {
    CREATE_TABLE("CREATE_TABLE"),
    SELECT_ALL("SELECT_ALL"),
    INSERT_USER("INSERT_USER"),
    DROP_TABLE("DROP_TABLE"),
    TABLE_EXISTS("TABLE_EXISTS");

    private final String identifier;

    MockSqlStatementType(String identifier) {
        this.identifier = identifier;
    }
    public String getIdentifier() {
        return identifier;
    }

}
