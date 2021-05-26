package databases.crud.sql.postgresql.statements.builders;

import databases.crud.core.ColumnValuePair;
import databases.crud.sql.Column;
import databases.crud.sql.postgresql.statements.DatabaseTableSchema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InsertStatement {
    private InsertStatement() {
    }

    public static Builder newBuilder(final DatabaseTableSchema schema) {
        return new Builder(schema);
    }

    public static class Builder {
        private DatabaseTableSchema tableSchema;
        private List<ColumnValuePair> columnValuePairs = new ArrayList<>();
        private List<Column> columnsToReturn = new ArrayList<>();

        private Builder(final DatabaseTableSchema tableSchema) {
            this.tableSchema = tableSchema;
        }

        public Builder insert(String value, Column column) {
            columnValuePairs.add(new ColumnValuePair(column, value));
            return this;
        }

        public Builder returning(Column... columns) {
            columnsToReturn.addAll(Arrays.asList(columns));
            return this;
        }

        public String build() throws Exception {
            final String tableName = surroundWithDoubleQuotes(tableSchema.getTableName());
            final String prefix = String.format("INSERT into %s", tableName);
            final String columnsDescription = generateColumnDescriptions();
            final String valuesDescription = generateValuesDescription();
            final String returningDescription = generateReturningDescription();
            return String.format("%s %s VALUES %s %s;", prefix, columnsDescription, valuesDescription, returningDescription);
        }

        public List<ColumnValuePair> getColumnValuePairs() {
            return columnValuePairs;
        }

        private String generateReturningDescription() {
            if (columnsToReturn.isEmpty()) {
                return "RETURNING *";
            }

            final String template = "RETURNING %s";
            final List<Column> columns = columnsToReturn;
            final String columnsDescription = columns.stream()
                    .map(Column::getName)
                    .collect(Collectors.joining(", "));
            return String.format(template, columnsDescription);
        }

        private String generateColumnDescriptions() {
            return generateParameterDescription(param -> surroundWithDoubleQuotes(param.getColumn().getName()));
        }

        private String generateValuesDescription() {
            return generateParameterDescription(param -> surroundWithSingleQuotes(param.getValue()));
        }

        private String generateParameterDescription(Function<ColumnValuePair, String> mapperFunction) {
            return surroundWithParentheses(
                    this.columnValuePairs.stream()
                            .map(mapperFunction::apply)
                            .collect(Collectors.joining(", "))
            );
        }

        private String surroundWithParentheses(String string) {
            return String.format("(%s)", string);
        }

        private String surroundWithDoubleQuotes(String string) {
            return surroundStringWithDelimiter(string, "\"");
        }

        private String surroundWithSingleQuotes(String string) {
            return surroundStringWithDelimiter(string, "'");
        }

        private String surroundStringWithDelimiter(String string, String delimiter) {
            return delimiter + string + delimiter;
        }

        public class Exception extends java.lang.Exception {
        }
    }
}