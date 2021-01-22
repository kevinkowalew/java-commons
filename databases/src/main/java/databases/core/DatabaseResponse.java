package databases.core;

import java.util.Optional;

public class DatabaseResponse {
    private final DatabaseResponse.Type type;
    private final Object object;

    private DatabaseResponse(Builder builder) {
        this.type = builder.type;
        this.object = builder.object;
    }

    public Optional<String> getErrorMessage() {
        if (type == Type.ERROR) {
            String errorMessage = object instanceof Exception ? ((Exception) object).getMessage() : "";
            return Optional.of(errorMessage);
        } else {
            return Optional.empty();
        }
    }

    public Object getObject() {
        return object;
    }

    public boolean isError() {
        return type == Type.ERROR;
    }

    public static Builder success() {
        return new Builder().setType(Type.SUCCESS);
    }

    public static Builder error() {
        return new Builder().setType(Type.ERROR);
    }

    public DatabaseResponse cloneWithUpdatedObject(Object updatedObject) {
        return new Builder().setType(type).setObject(updatedObject).build();
    }

    public static class Builder {
        public Type type;
        private Object object;

        private Builder() {
        }

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setObject(Object object) {
            this.object = object;
            return this;
        }

        public DatabaseResponse build() {
            return new DatabaseResponse(this);
        }
    }

    public enum Type {
        SUCCESS, ERROR
    }
}
