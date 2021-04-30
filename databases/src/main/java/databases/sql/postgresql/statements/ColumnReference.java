package databases.sql.postgresql.statements;

import databases.sql.Column;

public class ColumnReference {
    private final String parentTableName;
    private final Column column;

    public ColumnReference(String parentTableName, Column column) {
        this.parentTableName = parentTableName;
        this.column = column;
    }

    public String getParentTableName() {
        return parentTableName;
    }

    public Column getColumn() {
        return column;
    }
}
