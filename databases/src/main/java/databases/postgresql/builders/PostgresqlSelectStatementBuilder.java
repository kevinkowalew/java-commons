package databases.postgresql.builders;

import databases.core.KeyValuePair;
import databases.sql.SqlStatementBuilderException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostgresqlSelectStatementBuilder {
    private final List<String> selectedColumnNames = new ArrayList<>();
    private final List<KeyValuePair> queryParams = new ArrayList<>();
    private String searchPath = null;
    private String tableName = null;

    private PostgresqlSelectStatementBuilder() {
    }

    public static PostgresqlSelectStatementBuilder newBuilder() {
        return new PostgresqlSelectStatementBuilder();
    }

    public PostgresqlSelectStatementBuilder setSearchPath(String searchPath) {
        this.searchPath = searchPath;
        return this;
    }

    public PostgresqlSelectStatementBuilder setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public PostgresqlSelectStatementBuilder selectColumn(String columnName) {
        this.selectedColumnNames.add(columnName);
        return this;
    }

    public String build() throws SqlStatementBuilderException {
        return String.format("%s %s", generateSearchPathStatement(), generateSelectStatement());
    }

    private String generateSearchPathStatement() {
        return "SET search_path = " + this.searchPath + ";";
    }

    private String generateSelectStatement() {
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

        String queryDescription = queryParams.stream()
                .map(keyValuePair -> {
                    return surroundWithDoubleQuotes(keyValuePair.getKey()) + "=" + surroundWithSingleQuotes(keyValuePair.getValue());
                }).collect(Collectors.joining(","));

        return String.format("WHERE %s", queryDescription);
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
