package databases.orm;

public class Filter {
    // TODO: add builder for this class to support compound filter with logical AND/OR operators
    private final String fieldName;
    private final Filter.Operator operator;
    private final Object value;

    private Filter(String fieldName, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
    }

    public static Filter on(String fieldName, Operator operator, Object value) {
        return new Filter(fieldName, operator, value);
    }

    public enum Operator {
        EQUALS, GREATER_THAN, LESS_THAN;
    }
}
