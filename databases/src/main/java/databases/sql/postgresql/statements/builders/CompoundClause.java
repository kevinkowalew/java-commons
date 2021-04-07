package databases.sql.postgresql.statements.builders;

import databases.sql.postgresql.statements.LogicalOperator;
import databases.sql.postgresql.statements.Pair;
import databases.sql.postgresql.statements.WhereClause;

import java.util.ArrayList;
import java.util.List;

public class CompoundClause {
    private WhereClause leadingClause;
    private List<Pair<LogicalOperator, WhereClause>> trailingClauses;

    private CompoundClause(Builder builder) {
        this.leadingClause = builder.leadingClause;
        this.trailingClauses = builder.trailingClauses;
    }

    public WhereClause getLeadingClause() {
        return leadingClause;
    }

    public List<Pair<LogicalOperator, WhereClause>> getTrailingClauses() {
        return trailingClauses;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private WhereClause leadingClause;
        private List<Pair<LogicalOperator, WhereClause>> trailingClauses = new ArrayList<>();

        private Builder() {
        }

        public Builder where(WhereClause clause) {
            this.leadingClause = clause;
            return this;
        }

        public Builder and(WhereClause clause) {
            addTrailingClauseWithOperator(clause, LogicalOperator.AND);
            return this;
        }

        public Builder or(WhereClause clause) {
            addTrailingClauseWithOperator(clause, LogicalOperator.OR);
            return this;
        }

        public CompoundClause build() {
            return new CompoundClause(this);
        }

        private void addTrailingClauseWithOperator(WhereClause clause, LogicalOperator operator) {
            final Pair<LogicalOperator, WhereClause> pair = new Pair<>(operator, clause);
            this.trailingClauses.add(pair);
        }
    }
}
