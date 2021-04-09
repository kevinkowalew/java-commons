package databases.sql.postgresql.statements.builders;

import databases.core.ColumnValuePair;
import databases.sql.Column;
import databases.sql.postgresql.statements.DatabaseTableSchema;
import databases.sql.postgresql.statements.Formatter;

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
            return String.format("%s", generateInsertStatement());
        }

        public List<ColumnValuePair> getColumnValuePairs() {
            return columnValuePairs;
        }

        private String generateInsertStatement() {
            final String prefix = String.format("INSERT into \"%s\"", this.tableSchema.getTableName());
            final String columnsDescription = generateColumnDescriptions();
            final String valuesDescription = generateValuesDescription();
            final String returningDescription = generateReturningDescription();
            return String.format("%s %s VALUES %s;", prefix, columnsDescription, valuesDescription);
        }

        private String generateReturningDescription() {
            if (columnsToReturn.isEmpty()) {
                return "";
            }

            final String template = "RETURNING %s";
            final String columnsDescription = Formatter.createCommaSeparatedColumnsDescription(columnsToReturn);
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