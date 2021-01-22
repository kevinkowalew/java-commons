package databases.core;

import java.util.List;

/**
 *
 */
public class DatabaseRequest {
  private final String identifier;
  private final OperationType operationType;
  private final Object object;

  public DatabaseRequest(Builder builder) {
    this.identifier = builder.identifier;
    this.operationType = builder.operationType;
    this.object = builder.object;
  }

  public String getIdentifier() {
    return identifier;
  }

  public OperationType getOperationType() {
    return operationType;
  }

  public Object getObject() {
    return object;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String identifier;
    private OperationType operationType;
    private Object object;

    private Builder() {}

    public Builder setIdentifier(String identifier) {
      this.identifier = identifier;
      return this;
    }

    public Builder setOperationType(OperationType operationType) {
      this.operationType = operationType;
      return this;
    }

    public Builder setObject(Object object) {
      this.object = object;
      return this;
    }

    public DatabaseRequest build() {
      return new DatabaseRequest(this);
    }
  }
}
