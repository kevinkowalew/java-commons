package databases.sql;

public class ClassReferenceWrapper {
    private final Class<?> type;

    public ClassReferenceWrapper(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

    public boolean isInstance(Object object) {
        return type.isInstance(object);
    }
}
