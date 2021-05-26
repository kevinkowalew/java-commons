package databases.crud.sql.postgresql.statements.builders;

import databases.crud.core.Pair;
import databases.crud.sql.Column;
import databases.crud.sql.SqlStatementBuilderException;
import databases.crud.sql.postgresql.statements.DatabaseTableSchema;
import databases.crud.sql.postgresql.statements.Formatter;
import databases.crud.sql.postgresql.statements.WhereClause;
import databases.crud.sql.postgresql.statements.WhereClauseOperator;

import java.util.ArrayList;
import java.util.List;

public class SelectStatement {
    private SelectStatement() {

    }

    public static Builder newBuilder(final DatabaseTableSchema schema) {
        return new Builder(schema.getTableName());
    }

    public static class Builder {
        private final String tableName;
        private final List<Column> selectedColumnNames = new ArrayList<>();
        private CompoundClause.Builder clauseBuilder = CompoundClause.newBuilder();
        private List<Pair<Column>> joinTargets;

        private Builder(final String tableName) {
            this.tableName = tableName;
        }

        public Builder select(Column column) {
            this.selectedColumnNames.add(column);
            return this;
        }

        public Builder where(WhereClause clause) {
            clauseBuilder = clauseBuilder.where(clause);
            return this;
        }

        public Builder where(Column column, WhereClauseOperator operator, Object value) {
            final WhereClause clause = new WhereClause(column, operator, value);
            return where(clause);
        }

        public Builder or(WhereClause clause) {
            clauseBuilder = clauseBuilder.or(clause);
            return this;
        }

        public Builder or(Column column, WhereClauseOperator operator, Object value) {
            final WhereClause clause = new WhereClause(column, operator, value);
            return or(clause);
        }

        public Builder and(WhereClause clause) {
            clauseBuilder = clauseBuilder.and(clause);
            return this;
        }

        public Builder and(Column column, WhereClauseOperator operator, Object value) {
            final WhereClause clause = new WhereClause(column, operator, value);
            return where(clause);
        }

        public Builder innerJoin(final String tableName) {
            return this;
        }

        public String build() throws SqlStatementBuilderException {
            final String columnsDescription = Formatter.createColumnsDescription(selectedColumnNames);
            final String columnsStatement = selectedColumnNames.isEmpty() ? "*" : columnsDescription;
            final String whereStatement = Formatter.createWhereStatement(clauseBuilder.build());

            if (whereStatement.isEmpty()) {
                final String template = "SELECT %s FROM \"%s\";";
                return String.format(template, columnsStatement, tableName);
            } else {
                final String template = "SELECT %s FROM \"%s\" WHERE %s;";
                return String.format(template, columnsStatement, tableName, whereStatement);
            }
        }
    }
}