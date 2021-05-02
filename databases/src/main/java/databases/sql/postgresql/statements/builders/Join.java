package databases.sql.postgresql.statements.builders;

import databases.core.Pair;
import databases.sql.Column;

import javax.annotation.Nullable;

public class Join {
    private final Type type;
    private final Pair<Column> columnMapping;

    private Join(Type type, Pair<Column> columnMapping) {
        this.type = type;
        this.columnMapping = columnMapping;
    }

    @Nullable
    public static Join innerJoin(Column... columnMappings) {
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

    public Pair<Column> getColumnMapping() {
        return columnMapping;
    }

    private enum Type {
        INNER_JOIN;
    }
}
