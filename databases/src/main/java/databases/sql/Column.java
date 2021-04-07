package databases.sql;

import java.util.Objects;

public class Column {
    private final String name;
    private final Column.Type type;
    private final Boolean required;

    private Column(String name, Column.Type type, Boolean required) {
        this.name = name;
        this.type = type;
        this.required = required;
    }

    public static Column with(String name, Column.Type type, Boolean isRequired) {
        return new Column(name, type, isRequired);
    }

    public String getName() {
        return this.name;
    }

    public Column.Type getType() {
        return this.type;
    }

    public Boolean isRequired() {
        return required;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Column column = (Column) o;
        return Objects.equals(name, column.name) && type == column.type && Objects.equals(required, column.required);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, required);
    }

    public enum Type {
        SERIAL_PRIMARY_KEY,
        VARCHAR_255;

        Type() {
        }
    }
}
