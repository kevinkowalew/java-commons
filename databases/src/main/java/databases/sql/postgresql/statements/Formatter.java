package databases.sql.postgresql.statements;

import databases.core.ColumnValuePair;
import databases.sql.Column;
import databases.sql.postgresql.statements.builders.CompoundClause;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Formatter {
    private final static String COMMA_SPACE_DELIMITER = ", ";
    private final static String QUOTATION_MARK = "\"";

    public static String createColumnsDescription(List<Column> columns) {
        return columns.stream()
                .map(Column::getName)
                .collect(Collectors.joining(COMMA_SPACE_DELIMITER));
    }

    public static String createColumnDescription(Column column) {
        return joinWithSeparator(
                surroundString(column.getParentTableName(), QUOTATION_MARK),
                column.getName(),
                ".");
    }

    public static String createColumnDescriptionWithAlternateName(Column column, String alternateName) {
        return joinWithSeparator(
                surroundString(alternateName, QUOTATION_MARK),
                column.getName(),
                ".");
    }

    private static String joinWithSpace(String prefix, String suffix) {
        return joinWithSeparator(prefix, suffix, " ");
    }

    private static String joinWithSeparator(String prefix, String suffix, String separator) {
        return String.format("%s%s%s", prefix, separator, suffix);
    }

    public static String surroundString(String string, String surroundingString) {
        return String.format("%s%s%s", surroundingString, string, surroundingString);
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