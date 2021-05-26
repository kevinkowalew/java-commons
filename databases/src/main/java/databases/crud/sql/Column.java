package databases.crud.sql;


import databases.crud.sql.postgresql.statements.builders.JoinColumnMapping;

import java.util.Objects;
import java.util.Optional;

public class Column {
    private final String name;
    private final Column.Type type;
    private final String parentTableName;
    private final Boolean required;
    private final Optional<Column> referencedColumn;

    private Column(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.parentTableName = builder.parentTableName;
        this.required = builder.required;
        this.referencedColumn = builder.referencedColumn;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public JoinColumnMapping joinMapping(Column column) {
        return new JoinColumnMapping(this, column);
    }

    public static class Builder {
        private String name;
        private Type type;
        private String parentTableName;
        private Boolean required = false;
        private Optional<Column> referencedColumn = Optional.empty();

        private Builder() {
        }

        public Builder named(String name) {
            this.name = name;
            return this;
        }

        public Builder type(Type type) {
            this.type = type;
            return this;
        }

        public Builder parentTableName(String parentTableName) {
            this.parentTableName = parentTableName;
            return this;
        }

        public Builder required() {
            this.required = true;
            return this;
        }

        public Builder serialPrimaryKey() {
            this.type = Type.SERIAL_PRIMARY_KEY;
            return this;
        }

        public Builder foreignKey(Column referencedColumn) {
            this.type = Type.FOREIGN_KEY;
            this.referencedColumn = Optional.of(referencedColumn);
            return this;
        }

        public Column build() {
            // TODO: add validation here
            return new Column(this);
        }
    }

    public String getName() {
        return this.name;
    }

    public Column.Type getType() {
        return type;
    }

    public Boolean isRequired() {
        return required;
    }

    public Optional<Column> getAssociatedColumn() {
        return referencedColumn;
    }

    public String getParentTableName() {
        return parentTableName;
    }

    public Builder toBuilder() {
        Builder builder = newBuilder().named(name).type(type).parentTableName(parentTableName);
        if (required) {
            builder = builder.required();
        }

        if (type == Type.SERIAL_PRIMARY_KEY) {
            builder = builder.serialPrimaryKey();
        }

        if (referencedColumn.isPresent()) {
            builder = builder.foreignKey(referencedColumn.get());
        }

        return builder;
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
        VARCHAR_255,
        FOREIGN_KEY;

        Type() {
        }
    }

}