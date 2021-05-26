package databases.crud.sql.postgresql.statements.builders;

import databases.crud.sql.Column;
import databases.crud.sql.ColumnAlias;
import databases.crud.sql.postgresql.statements.WhereClause;

import java.util.ArrayList;
import java.util.List;

public class Join {
    private final Type type;
    private final JoinColumnMapping mapping;
    private final List<ColumnAlias> selectedColumns;

    private Join(Builder builder) {
        this.type = builder.type;
        this.mapping = builder.mapping;
        this.selectedColumns = builder.selectedColumns;
    }

    public String getTypeDescription() {
        switch (this.type) {
            case INNER_JOIN:
                return "INNER JOIN";
            default:
                return "";
        }
    }

    public JoinColumnMapping getMapping() {
        return mapping;
    }

    public List<ColumnAlias> getSelectedColumns() {
        return selectedColumns;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Type type;
        public JoinColumnMapping mapping;
        private List<ColumnAlias> selectedColumns = new ArrayList<>();
        private WhereClause whereClause;

        public Builder innerJoin(JoinColumnMapping mapping) {
            type = Type.INNER_JOIN;
            this.mapping = mapping;
            return this;
        }

        public Builder select(Column column, String alias) {
            this.selectedColumns.add(new ColumnAlias(column, alias));
            return this;
        }

        public Join build() {
            return new Join(this);
        }
    }

    private enum Type {
        INNER_JOIN;
    }
}