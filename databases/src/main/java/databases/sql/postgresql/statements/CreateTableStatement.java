package databases.sql.postgresql.statements;

import databases.sql.Column;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CreateTableStatement {
    public CreateTableStatement() {
    }

    public static Optional<String> create(DatabaseTableSchema schema) {
        if (schema == null) {
            return Optional.empty();
        } else {
            CreateTableStatement.Builder builder = newBuilder().setTableName(schema.getTableName());
            final Set<Column> columnList = schema.getColumns();
            columnList.forEach(builder::addColumn);
            return builder.build();
        }
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
                String prefix = String.format("CREATE TABLE \"%s\" (\n%s", this.tableName, this.columnDescriptions());
                String suffix = "\n);";
                String statement = prefix + "\n);";
                return Optional.of(statement);
            } else {
                return Optional.empty();
            }
        }

        private String columnDescriptions() {
            return (String)this.columnList.stream().map(this::createColumnDescription).collect(Collectors.joining(",\n"));
        }

        private String createColumnDescription(Column column) {
            String typeDescription = this.createColumnTypeDescription(column.getType());
            String columnName = this.surroundString(column.getName(), "\"");
            String description = this.joinWithSpace(columnName, typeDescription);
            return "\t" + description;
        }

        private String joinWithSpace(String prefix, String suffix) {
            return String.format("%s %s", prefix, suffix);
        }

        private String surroundString(String string, String surroundingString) {
            return String.format("%s%s%s", surroundingString, string, surroundingString);
        }

        private String createColumnTypeDescription(Column.Type type) {
            switch(type) {
                case SERIAL_PRIMARY_KEY:
                    return "SERIAL PRIMARY KEY";
                case VARCHAR_255:
                    return "VARCHAR(255)";
                default:
                    return "";
            }
        }
    }
}

