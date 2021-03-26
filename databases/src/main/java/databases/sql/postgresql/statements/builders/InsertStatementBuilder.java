package databases.sql.postgresql.statements.builders;

import databases.core.ColumnValuePair;
import databases.sql.Column;
import databases.sql.SqlStatementBuilderException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InsertStatementBuilder {
    private String searchPath = null;
    private String tableName = null;
    private List<ColumnValuePair> columnValuePairs = new ArrayList<>();

    private InsertStatementBuilder() {
    }

    public static InsertStatementBuilder newBuilder() {
        return new InsertStatementBuilder();
    }

    public InsertStatementBuilder setSearchPath(String searchPath) {
        this.searchPath = searchPath;
        return this;
    }

    public InsertStatementBuilder setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public InsertStatementBuilder insert(String value, Column columnName) {
        columnValuePairs.add(new ColumnValuePair(columnName, value));
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
}