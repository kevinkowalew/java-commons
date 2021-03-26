package databases.sql;

public class SqlExecutorException extends Exception {

    private SqlExecutorException(String errorMessage) {
        super(errorMessage);
    }

    public static final Exception FAILED_TO_OPEN_DATABASE_CONNECTION = new SqlExecutorException("Failed to open database connection");
    public static final Exception FAILED_TO_OPEN_STATEMENT_ENTRY_POINT = new SqlExecutorException("Failed to open statement entry point");
    public static final Exception FAILED_TO_FETCH_RESULTS_FROM_DATABASE = new SqlExecutorException("Failed to fetch results from database");
    public static final Exception FAILED_TO_DESERIALIZE_DATABASE_RESULTS = new SqlExecutorException("Failed to deserialize results from database");
}

