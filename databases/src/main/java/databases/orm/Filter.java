package databases.orm;

import databases.crud.sql.postgresql.statements.Operator;

public class Filter<ModelType, FieldType> {
    private final String fieldName;
    private final FieldType expected;
    private final Operator operator;

    private Filter(Builder<ModelType, FieldType> builder) {
        this.fieldName = builder.fieldName;
        this.expected = builder.expected;
        this.operator = builder.operator;
    }

    public static class Builder<ModelType, FieldType> {
        private final Class<ModelType> tClass;
        public Operator operator;
        private String fieldName;
        private FieldType expected;

        private Builder(Class<ModelType> tClass) {
            this.tClass = tClass;
        }

        public Builder where(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public Builder equalTo(FieldType expected) {
            this.operator = Operator.EQUALS;
            this.expected = expected;
            return this;
        }

        public Filter<ModelType, FieldType> build() {
            // TODO: add validation for requests filtering on non-indexed fields
            return new Filter<ModelType, FieldType>(this);
        };
    }

    public static <T, F> Builder<T,F> newBuilder(Class<T> tClass) {
        return new Builder<T, F>(tClass);
    }

    public String getFieldName() {
        return fieldName;
    }

    public Class getFieldClassType() {
        return expected.getClass();
    }

    public Operator getOperator() {
        return operator;
    }

    public FieldType getExpected() {
        return expected;
    }
}
