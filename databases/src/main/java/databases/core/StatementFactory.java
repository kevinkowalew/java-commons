package databases.core;

import databases.core.Request;

public interface StatementFactory<Model> {
    String createStatementForRequest(Request<Model> request);
}
