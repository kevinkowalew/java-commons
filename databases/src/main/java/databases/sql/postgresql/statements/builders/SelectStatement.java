package databases.sql.postgresql.statements.builders;

import databases.core.ColumnValuePair;
import databases.sql.SqlStatementBuilderException;
import databases.sql.postgresql.statements.DatabaseTableSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SelectStatement {
    private SelectStatement() {

    }

    public static Builder newBuilder(final DatabaseTableSchema schema) {
        return new Builder(schema.getTableName());
    }

    public static class Builder {
        private final String tableName;
        private final List<String> selectedColumnNames = new ArrayList<>();
        private final List<ColumnValuePair> queryParams = new ArrayList<>();

        private Builder(final String tableName) {
            this.tableName = tableName;
        }

        public Builder selectColumn(String columnName) {
            this.selectedColumnNames.add(columnName);
            return this;
        }

        public String build() throws SqlStatementBuilderException {
            final String columnsDescription = generateSelectedColumnsDescriptions();
            final String suffix = String.format("FROM \"%s\"", this.tableName);
            return String.format("%s %s %s", generateSelectedColumnsDescriptions(), generateWhereStatement(), suffix);
        }

        private String generateSelectedColumnsDescriptions() {
            final String selectedColumnDescriptions = selectedColumnNames.isEmpty() ? "*" : String.join(", ", selectedColumnNames);
            return String.format("SELECT %s", selectedColumnDescriptions);
        }

        private String generateWhereStatement() {
            if (queryParams.isEmpty()) {
                return "";
            }

            final String queryDescription = queryParams.stream()
                    .map(this::generateDescriptionForColumnValuePair)
                    .collect(Collectors.joining(","));

            return String.format("WHERE %s", queryDescription);
        }

        private String generateDescriptionForColumnValuePair(ColumnValuePair pair) {
            return surroundWithDoubleQuotes(pair.getColumn().getName()) + "=" + surroundWithSingleQuotes(pair.getValue());
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
    }
}