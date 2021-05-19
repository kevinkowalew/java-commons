package databases.sql.postgresql.statements.builders;

import databases.sql.Column;

import java.util.Optional;

public class JoinMapping {
    private final Column from;
    private final Column to;
    private final Optional<String> alias;

    private JoinMapping(Builder builder) {
        this.from = builder.from;
        this.to = builder.to;
        this.alias = builder.alias;
    }

    public Column getFrom() {
        return from;
    }

    public Column getTo() {
        return to;
    }

    public Optional<String> getAlias() {
        return alias;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Column from;
        private Column to;
        private Optional<String> alias;

        public Builder from(Column column) {
            this.from = column;
            return this;
        }

        public Builder to(Column column) {
            this.to = column;
            return this;
        }

        public Builder alias(final String alias) {
            this.alias = Optional.ofNullable(alias);
            return this;
        }

        public JoinMapping build() {
            return new JoinMapping(this);
        }

        private Builder() {
        }
    }
}
