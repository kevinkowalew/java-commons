package databases.sql.postgresql.statements;

import databases.sql.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteStatement {
    public DeleteStatement() {
    }

    public static DeleteStatement.Builder newBuilder(final DatabaseTableSchema schema) {
        return new DeleteStatement.Builder(schema.getTableName());
    }

    private static String createWhereClauseDescription(WhereClause clause) {
        String valueDescription = createValueDescription(clause.getValue());
        return String.format("\"%s\" %s %s", clause.getColumn().getName(), clause.getOperator().get(), valueDescription);
    }

    private static String createOperatorWhereClauseDescription(Pair<LogicalOperator, WhereClause> operatorWhereClausePair) {
        String whereClauseDescription = createWhereClauseDescription((WhereClause)operatorWhereClausePair.getValue());
        return String.format("%s %s", operatorWhereClausePair.getKey(), whereClauseDescription);
    }

    private static String createValueDescription(Object value) {
        return value instanceof String ? String.format("'%s'", value) : value.toString();
    }

    public static class Builder {
        private final String tableName;
        private WhereClause initialClause;
        private List<Pair<LogicalOperator, WhereClause>> trailingClauses;

        private Builder(String tableName) {
            this.tableName = tableName;
            this.trailingClauses = new ArrayList();
        }

        public DeleteStatement.Builder where(WhereClause clause) {
            this.initialClause = clause;
            return this;
        }

        public DeleteStatement.Builder or(WhereClause clause) {
            Pair<LogicalOperator, WhereClause> pair = new Pair(LogicalOperator.OR, clause);
            this.trailingClauses.add(pair);
            return this;
        }

        public DeleteStatement.Builder and(WhereClause clause) {
            Pair<LogicalOperator, WhereClause> pair = new Pair(LogicalOperator.AND, clause);
            this.trailingClauses.add(pair);
            return this;
        }

        public DeleteStatement.Builder setTrailingClauses(List<Pair<LogicalOperator, WhereClause>> trailingClauses) {
            this.trailingClauses = trailingClauses;
            return this;
        }

        public String build() {
            if (this.initialClause == null) {
                return String.format("SELECT * FROM %s;", this.tableName);
            } else {
                String initialValueDescription = DeleteStatement.createWhereClauseDescription(this.initialClause);
                String prefix = String.format("DELETE FROM \"%s\" WHERE %s", this.tableName, initialValueDescription);
                if (this.trailingClauses.isEmpty()) {
                    return String.format("%s;", prefix);
                } else {
                    String valueDescription = (String)this.trailingClauses.stream().map((x$0) -> {
                        return DeleteStatement.createOperatorWhereClauseDescription(x$0);
                    }).collect(Collectors.joining(" "));
                    return String.format("%s %s;", prefix, valueDescription);
                }
            }
        }
    }
}


