package databases.sql;

import databases.core.DatabaseRequest;
import databases.core.OperationType;

import java.util.Optional;

public interface OperationTypeFactory {
    Optional<OperationType> getOperationTypeForRequest(DatabaseRequest request);
}
