package databases.crud.sql.postgresql.statements;

import databases.crud.sql.Column;

public class WhereClause {
    private final Column column;
    private final WhereClauseOperator operator;
    private final Object value;

    public WhereClause(Column column, WhereClauseOperator operator, Object value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }

    public Column getColumn() {
        return this.column;
    }

    public WhereClauseOperator getOperator() {
        return this.operator;
    }

    public Object getValue() {
        return this.value;
    }
}
