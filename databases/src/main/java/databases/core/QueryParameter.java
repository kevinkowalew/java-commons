package databases.core;

public class QueryParameter {
    private final String field;
    private final QueryParameterOperator operator;
    private final String value;

    public QueryParameter(String field, QueryParameterOperator operator, String value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public QueryParameterOperator getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }
}
