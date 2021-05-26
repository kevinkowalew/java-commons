package databases.crud.sql.postgresql.statements;

public enum WhereClauseOperator {
    EQUALS,
    LESS_THAN,
    GREATER_THAN;

    WhereClauseOperator() {
    }

    public String get() {
        switch (this) {
            case EQUALS:
                return "=";
            case GREATER_THAN:
                return ">";
            case LESS_THAN:
                return "<";
            default:
                return null;
        }
    }
}
