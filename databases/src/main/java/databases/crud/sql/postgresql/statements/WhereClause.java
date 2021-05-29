package databases.crud.sql.postgresql.statements;

import databases.crud.sql.Column;

public class WhereClause {
    private final Column column;
    private final Operator operator;
    private final Object value;

    public WhereClause(Column column, Operator operator, Object value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }

    public Column getColumn() {
        return this.column;
    }

    public Operator getOperator() {
        return this.operator;
    }

    public Object getValue() {
        return this.value;
    }
}
