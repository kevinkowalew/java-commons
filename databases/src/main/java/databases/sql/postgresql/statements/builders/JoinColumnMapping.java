package databases.sql.postgresql.statements.builders;

import databases.sql.Column;

import java.util.Optional;

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
