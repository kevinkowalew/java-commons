package databases.crud.sql.postgresql.statements.builders;

import databases.crud.sql.postgresql.statements.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteStatement {
    public DeleteStatement() {
    }

    public static DeleteStatement.Builder newBuilder(final DatabaseTableSchema schema) {
        return new DeleteStatement.Builder(schema.getTableName());
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
                String initialValueDescription = Formatter.createWhereClauseDescription(this.initialClause);
                String prefix = String.format("DELETE FROM \"%s\" WHERE %s", this.tableName, initialValueDescription);

                if (this.trailingClauses.isEmpty()) {
                    return String.format("%s;", prefix);
                } else {
                    String valueDescription = this.trailingClauses.stream()
                            .map(c -> Formatter.createOperatorWhereClauseDescription(c))
                            .collect(Collectors.joining(" "));
                    return String.format("%s %s;", prefix, valueDescription);
                }
            }
        }
    }
}


