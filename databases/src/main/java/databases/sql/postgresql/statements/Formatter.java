package databases.sql.postgresql.statements;

import databases.core.ColumnValuePair;
import databases.sql.Column;
import databases.sql.postgresql.statements.builders.CompoundClause;

import java.util.List;
import java.util.stream.Collectors;

public class Formatter {

    public static String createColumnsDescription(List<Column> columns) {
        return columns.stream().map(Formatter::createColumnDescription).collect(Collectors.joining(", "));
    }

    public static String createColumnDescription(Column column) {
        final String typeDescription = createColumnTypeDescription(column.getType());
        final String columnName = surroundString(column.getName(), "\"");
        final String description = joinWithSpace(columnName, typeDescription);
        return "\t" + description;
    }

    private static String joinWithSpace(String prefix, String suffix) {
        return String.format("%s %s", prefix, suffix);
    }

    private static String surroundString(String string, String surroundingString) {
        return String.format("%s%s%s", surroundingString, string, surroundingString);
    }

    private static String createColumnTypeDescription(Column.Type type) {
        switch(type) {
            case SERIAL_PRIMARY_KEY:
                return "SERIAL PRIMARY KEY";
            case VARCHAR_255:
                return "VARCHAR(255)";
            default:
                return "";
        }
    }

    public static String createWhereClauseDescription(WhereClause clause) {
        final String valueDescription = createValueDescription(clause.getValue());
        return String.format("\"%s\" %s %s", clause.getColumn().getName(), clause.getOperator().get(), valueDescription);
    }
    public static String createOperatorWhereClauseDescription(Pair<LogicalOperator, WhereClause> operatorWhereClausePair) {
        final String whereClauseDescription = createWhereClauseDescription(operatorWhereClausePair.getValue());
        return String.format("%s %s", operatorWhereClausePair.getKey(), whereClauseDescription);
    }

    private static String createValueDescription(Object value) {
        return value instanceof String ? String.format("'%s'", value) : value.toString();
    }
    public static String createWhereStatement(CompoundClause clause) {
        if (clause.getLeadingClause() == null) {
            return "";
        }

        String statement = createWhereClauseDescription(clause.getLeadingClause());
        if (clause.getTrailingClauses() == null) {
            return statement;
        }

        for (Pair<LogicalOperator, WhereClause> trailingClause : clause.getTrailingClauses()) {
            final String description = createTrailingClauseDescription(trailingClause);
            statement += String.format(" %s", description);
        }
        return statement;
    }

    private static String createTrailingClauseDescription(Pair<LogicalOperator, WhereClause> operatorWhereClausePair) {
        final String whereClauseDescription = createWhereClauseDescription((WhereClause) operatorWhereClausePair.getValue());
        return String.format("%s %s", operatorWhereClausePair.getKey(), whereClauseDescription);
    }

    public static String createUpdateDescription(ColumnValuePair update) {
        final String template = "%s = %s";
        final String columnDescription = surroundString(update.getColumn().getName(), "\"");
        final String valueDescription = createValueDescription(update.getValue());
        return String.format(template, columnDescription, valueDescription);
    }
}