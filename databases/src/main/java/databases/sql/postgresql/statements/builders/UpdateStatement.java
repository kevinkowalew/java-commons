package databases.sql.postgresql.statements.builders;

import databases.core.ColumnValuePair;
import databases.sql.Column;
import databases.sql.postgresql.statements.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateStatement {
    public static Builder newBuilder(final DatabaseTableSchema schema) {
        return new Builder(schema.getTableName());
    }

    public static class Builder {
        private final String tableName;
        private CompoundClause.Builder whereClauseBuilder = CompoundClause.newBuilder();
        private List<ColumnValuePair> updates = new ArrayList<>();

        public Builder(final String tableName) {
            this.tableName = tableName;
        }

        public Builder where(WhereClause clause) {
            whereClauseBuilder = whereClauseBuilder.where(clause);
            return this;
        }

        public Builder and(WhereClause clause) {
            whereClauseBuilder = whereClauseBuilder.and(clause);
            return this;
        }

        public Builder or(WhereClause clause) {
            whereClauseBuilder = whereClauseBuilder.or(clause);
            return this;
        }

        public Builder update(String value, Column column) {
            this.updates.add(new ColumnValuePair(column, value));
            return this;
        }

        public String build() {
            final String updatesStatement = updates.stream()
                    .map(Formatter::createUpdateDescription)
                    .collect(Collectors.joining(", "));

            final String tableNameDescrption = String.format("\"%s\"", this.tableName);
            final CompoundClause compoundClause = whereClauseBuilder.build();
            final String whereStatement = Formatter.createWhereStatement(compoundClause);

            final String template = "UPDATE %s SET %s WHERE %s;";
            return String.format(template, tableNameDescrption, updatesStatement, whereStatement);
        }
    }
}
