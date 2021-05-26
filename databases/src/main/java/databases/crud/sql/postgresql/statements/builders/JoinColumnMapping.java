package databases.crud.sql.postgresql.statements.builders;

import databases.crud.sql.Column;

public class JoinColumnMapping {
    private final Column from;
    private final Column to;

    public JoinColumnMapping(Column from, Column to) {
        this.from = from;
        this.to = to;
    }

    public Column getFrom() {
        return from;
    }

    public Column getTo() {
        return to;
    }
}
