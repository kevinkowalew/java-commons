package databases.postgresql.builders;

import databases.core.KeyValuePair;
import databases.sql.SqlStatementBuilderException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PostgresqlInsertStatementBuilder {
    private String searchPath = null;
    private String tableName = null;
    private List<KeyValuePair> valueColumnNamePairsToInsert = new ArrayList<>();

    private PostgresqlInsertStatementBuilder() {
    }

    public static PostgresqlInsertStatementBuilder newBuilder() {
        return new PostgresqlInsertStatementBuilder();
    }

    public PostgresqlInsertStatementBuilder setSearchPath(String searchPath) {
        this.searchPath = searchPath;
        return this;
    }

    public PostgresqlInsertStatementBuilder setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public PostgresqlInsertStatementBuilder insert(String value, String columnName) {
        valueColumnNamePairsToInsert.add(new KeyValuePair(columnName, value));
        return this;
    }

    public String build() throws SqlStatementBuilderException {
        return String.format("%s %s", generateSearchPathStatement(), generateInsertStatement());
    }

    private String generateSearchPathStatement() {
        return "SET search_path = " + this.searchPath + ";";
    }

    private String generateInsertStatement() {
        final String prefix = String.format("INSERT into \"%s\"", this.tableName);
        final String columnsDescription = generateColumnDescriptions();
        final String valuesDescription = generateValuesDescription();
        return String.format("%s %s VALUES %s;", prefix, columnsDescription, valuesDescription);
    }

    private String generateColumnDescriptions() {
        return generateParameterDescription(param -> surroundWithDoubleQuotes(param.getKey()));
    }

    private String generateValuesDescription() {
        return generateParameterDescription(param -> surroundWithSingleQuotes(param.getValue()));
    }

    private String generateParameterDescription(Function<KeyValuePair, String> mapperFunction) {
        return surroundWithParentheses(
                this.valueColumnNamePairsToInsert.stream()
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
}
