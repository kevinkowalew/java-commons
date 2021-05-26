package databases.crud.sql.postgresql.statements.builders;

import databases.crud.sql.Column;
import databases.crud.sql.postgresql.statements.DatabaseTableSchema;
import databases.crud.sql.postgresql.statements.Formatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CreateTableStatement {
    public CreateTableStatement() {
    }

    public static Optional<String> create(DatabaseTableSchema schema) {
        Builder builder = newBuilder().setTableName(schema.getTableName());
        final Set<Column> columnList = schema.getColumns();
        columnList.forEach(builder::addColumn);
        return builder.build();
    }

    public static CreateTableStatement.Builder newBuilder() {
        return new CreateTableStatement.Builder();
    }

    public static class Builder {
        private String tableName;
        private List<Column> columnList = new ArrayList();

        private Builder() {
        }

        public CreateTableStatement.Builder setTableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public CreateTableStatement.Builder addColumn(Column column) {
            this.columnList.add(column);
            return this;
        }

        public Optional<String> build() {
            if (this.tableName != null && !this.tableName.isEmpty() && !this.columnList.isEmpty()) {
                final String template = "CREATE TABLE \"%s\" (%s);";
                final String columnDescription = createColumnsDescription(columnList);
                final String statement = String.format(template, this.tableName, columnDescription);
                return Optional.of(statement);
            } else {
                return Optional.empty();
            }
        }

        private static String createColumnsDescription(List<Column> columns) {
            return columns.stream()
                    .map(Builder::createColumnDescription)
                    .collect(Collectors.joining(", "));
        }

        private static String createColumnDescription(Column column) {
            final String template = "%s %s";
            final String name = column.getName();
            final String typeDescription = createColumnTypeDescription(column);
            return String.format(template, name, typeDescription);
        };

        private static String createColumnTypeDescription(Column column) {
            switch (column.getType()) {
                case FOREIGN_KEY:
                    return createForeignKeyDescription(column);
                case SERIAL_PRIMARY_KEY:
                    return "SERIAL PRIMARY KEY";
                case VARCHAR_255:
                    return "VARCHAR(255)";
                default:
                    return "";
            }
        }

        private static String createForeignKeyDescription(Column column) {
            if (column.getAssociatedColumn().isEmpty()) {
                return "";
            } else {
                final Column columnReference = column.getAssociatedColumn().get();
                final String template = "INT references %s (%s)";
                final String tableName = Formatter.surroundString(columnReference.getParentTableName(), "\"");
                return String.format(template, tableName, columnReference.getName());
            }
        }
    }
}

