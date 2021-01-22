package databases.core;

public enum QueryParameterOperator {
    LESS_THEN, LESS_THEN_OR_EQUAL, EQUAL, NOT_EQUAL, GREATER_THAN_OR_EQUAL, GREATER_THAN;

    public static class PostgresqlErrorResponse {
        public static DatabaseResponse CONNECTION_ERROR = response("Failed to open database connection");
        public static DatabaseResponse STATEMENT_ERROR = response("Failed to create SQL statement");
        public static DatabaseResponse FETCH_RESULTS_ERROR = response("Failed to fetch results from database");
        public static DatabaseResponse DESERIALIZATION_ERROR = response("Failed to deserialize database results");

        private static DatabaseResponse response(String errorMessage) {
            return DatabaseResponse.error()
                    .setObject("Postgresql Error: " + errorMessage)
                    .build();
        }
    }
}
