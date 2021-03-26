package databases.sql.postgresql.statements;

import databases.sql.Column;

public class WhereClause {
    private final Column column;
    private final Object value;
    private final WhereClauseOperator operator;

    public WhereClause(Column column, Object value, WhereClauseOperator operator) {
        this.column = column;
        this.value = value;
        this.operator = operator;
    }

    public Column getColumn() {
        return this.column;
    }

    public Object getValue() {
        return this.value;
    }

    public WhereClauseOperator getOperator() {
        return this.operator;
    }
}
