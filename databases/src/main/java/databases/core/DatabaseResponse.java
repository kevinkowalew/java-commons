package databases.core;

import java.util.Optional;

public class DatabaseResponse {
    private final Object object;

    private DatabaseResponse(Builder builder) {
        this.object = builder.object;
    }

    public Optional<Object> getObject() {
        return Optional.ofNullable(object);
    }

    public <T> Optional<T> getCastedObject(Class<T> tClass) {
        if (!tClass.isInstance(object)) {
            return Optional.empty();
        } else {
            return Optional.of(tClass.cast(object));
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public DatabaseResponse cloneWithUpdatedObject(Object updatedObject) {
        return new Builder().setObject(updatedObject).build();
    }

    public static class Builder {
        private Object object;

        private Builder() {
        }

        public Builder setObject(Object object) {
            this.object = object;
            return this;
        }

        public DatabaseResponse build() {
            return new DatabaseResponse(this);
        }
    }
}
