package databases.sql.postgresql.statements.builders;

import databases.sql.Column;
import databases.sql.postgresql.statements.DatabaseTableSchema;
import databases.sql.postgresql.statements.Formatter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CreateTableStatement {
    public CreateTableStatement() {
    }

    public static Optional<String> create(@Nonnull DatabaseTableSchema schema) {
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
                final String columnDescription = Formatter.createColumnsDescription(columnList);
                final String statement = String.format(template, this.tableName, columnDescription);
                return Optional.of(statement);
            } else {
                return Optional.empty();
            }
        }
    }
}

