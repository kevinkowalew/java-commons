package databases.sql.postgresql.statements.builders;

import databases.sql.Column;
import databases.sql.postgresql.statements.WhereClause;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Join {
    private final Type type;
    private final JoinMapping mapping;
    private final List<Column> selectedColumns;
    private final WhereClause whereClause;

    private Join(Builder builder) {
        this.type = builder.type;
        this.mapping = builder.mapping;
        this.selectedColumns = builder.selectedColumns;
        this.whereClause = builder.whereClause;
    }

    public String getTypeDescription() {
        switch (this.type) {
            case INNER_JOIN:
                return "INNER JOIN";
            default:
                return "";
        }
    }

    public JoinMapping getMapping() {
        return mapping;
    }

    public WhereClause getWhereClause() {
        return whereClause;
    }

    public List<Column> getSelectedColumns() {
        return selectedColumns;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Type type;
        public JoinMapping mapping;
        private List<Column> selectedColumns = new ArrayList<>();
        private WhereClause whereClause;

        public Builder innerJoin() {
            type = Type.INNER_JOIN;
            return this;
        }

        public Builder mapping(JoinMapping mapping) {
            this.mapping = mapping;
            return this;
        }

        public Builder select(Column... columns) {
            this.selectedColumns.addAll(Arrays.asList(columns));
            return this;
        }

        public Join build() {
            return new Join(this);
        }

        public Builder where(WhereClause whereClause) {
            this.whereClause = whereClause;
            return this;
        }
    }

    private enum Type {
        INNER_JOIN;
    }
}