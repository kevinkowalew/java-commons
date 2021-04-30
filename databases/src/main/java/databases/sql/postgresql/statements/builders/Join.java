package databases.sql.postgresql.statements.builders;

import databases.core.Pair;
import databases.sql.Column;
import databases.sql.postgresql.statements.ColumnReference;

import javax.annotation.Nullable;

public class Join {
    private final Type type;
    private final Pair<ColumnReference> columnMapping;

    private Join(Type type, Pair<ColumnReference> columnMapping) {
        this.type = type;
        this.columnMapping = columnMapping;
    }

    @Nullable
    public static Join innerJoin(ColumnReference... columnMappings) {
        if (columnMappings.length != 2) {
            return null;
        } else {
            return new Join(Type.INNER_JOIN, new Pair<>(columnMappings[0], columnMappings[1]));
        }
    }

    public String getTypeDescription() {
        switch (this.type) {
            case INNER_JOIN:
                return "INNER JOIN";
            default:
                return "";
        }
    }

    public Pair<ColumnReference> getColumnMapping() {
        return columnMapping;
    }

    private enum Type {
        INNER_JOIN;
    }
}
