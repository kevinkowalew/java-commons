package databases.crud.core;

import databases.crud.sql.Column;

public class ColumnValuePair {
    private final Column column;
    private final String value;

    public ColumnValuePair(Column column, String value) {
        this.column = column;
        this.value = value;
    }

    public Column getColumn() {
        return column;
    }

    public String getValue() {
        return this.value;
    }
}
