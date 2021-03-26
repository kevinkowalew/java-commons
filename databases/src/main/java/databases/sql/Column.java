package databases.sql;

public class Column {
    private final String name;
    private final Column.Type type;
    private final boolean isPrimaryKey;

    private Column(String name, Column.Type type, boolean isPrimaryKey) {
        this.name = name;
        this.type = type;
        this.isPrimaryKey = isPrimaryKey;
    }

    public static Column with(String name, Column.Type type, Boolean isPrimaryKey) {
        return new Column(name, type, isPrimaryKey);
    }

    public static Column with(String name, Column.Type type) {
        return new Column(name, type, false);
    }

    public String getName() {
        return this.name;
    }

    public Column.Type getType() {
        return this.type;
    }

    public boolean isPrimaryKey() {
        return this.isPrimaryKey;
    }

    public enum Type {
        SERIAL_PRIMARY_KEY,
        VARCHAR_255;

        private Type() {
        }
    }
}
