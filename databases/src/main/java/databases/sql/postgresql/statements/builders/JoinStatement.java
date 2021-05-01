package databases.sql.postgresql.statements.builders;

import databases.core.Pair;
import databases.sql.Column;
import databases.sql.postgresql.statements.ColumnReference;
import databases.sql.postgresql.statements.DatabaseTableSchema;
import databases.sql.postgresql.statements.Formatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JoinStatement {
    public static Builder newBuilder(DatabaseTableSchema schema) {
        return new Builder(schema.getTableName());
    }

    public static class Builder {
        private final String tableName;
        private final List<ColumnReference> selectedColumns = new ArrayList<>();
        private final List<Join> joins = new ArrayList<>();

        public Builder(String tableName) {
            this.tableName = tableName;
        }

        public Builder select(ColumnReference... columns) {
            Collections.addAll(selectedColumns, columns);
            return this;
        }

        public Builder join(Join join) {
            this.joins.add(join);
            return this;
        }

        public Builder on(Pair<Column> columnPair) {
            return this;
        }

        public String build() {
            final String template = "SELECT %s FROM %s;";
            final String selectedColumnsDescription = Formatter.createColumnReferencesDescription(selectedColumns);
            return String.format(template, selectedColumnsDescription, createJoinsDescription());
        }

        private String createJoinsDescription() {
            final String leadingParenthesis = "".repeat(joins.size());
            final String prefix = String.format("%s\"%s\"", leadingParenthesis, tableName);
            final String template = "%s %s";
            final String joinsString = joins.stream()
                    .map(this::createJoinDescription)
                    .collect(Collectors.joining(""));
            return String.format(template, prefix, joinsString);
//            SELECT "Messages".recipient_id, "Messages".sender_id, "Messages".text, U1.id, U1.email, U2.id, U2.email
//            FROM "Messages"
//            INNER JOIN "Users" as U1 ON U1.id = "Messages".sender_id
//            INNER JOIN "Users" as U2 ON U2.id = "Messages".recipient_id
        }

        private String createJoinDescription(Join join) {
            final String template = "%s \"%s\" ON %s;";
            final String joinDescription = join.getTypeDescription();
            final String otherTableName = getOtherTableName(join);
            final String columnMappingDescription = createColumnMappingDescription(join);
            return String.format(template, joinDescription, otherTableName, columnMappingDescription);
        }

        private String createColumnMappingDescription(Join join) {
            final String template = "%s = %s";
            final String leadingColumnDescription = Formatter.createColumnReferenceDescription(join.getColumnMapping().getLeading());
            final String trailingColumnDescription = Formatter.createColumnReferenceDescription(join.getColumnMapping().getTrailing());
            return String.format(template, leadingColumnDescription, trailingColumnDescription);
        }


        private String getOtherTableName(Join join) {
            if (!join.getColumnMapping().getLeading().getParentTableName().equals(tableName)) {
                return join.getColumnMapping().getLeading().getParentTableName();
            } else {
                return join.getColumnMapping().getTrailing().getParentTableName();
            }
        }
    }
}
