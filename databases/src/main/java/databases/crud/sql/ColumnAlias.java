package databases.crud.sql;

public class ColumnAlias {
    private final Column column;
    private final String alias;

    public ColumnAlias(Column column, String alias) {
        this.column = column;
        this.alias = alias;
    }

    public Column getColumn() {
        return column;
    }

    public String getAlias() {
        return alias;
    }
}
