package databases.core;

/**
 *
 */
public class DatabaseRequest {
  private final String identifier;
  private final Object object;

  public DatabaseRequest(Builder builder) {
    this.identifier = builder.identifier;
    this.object = builder.object;
  }

  public String getIdentifier() {
    return identifier;
  }

  public Object getObject() {
    return object;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String identifier;
    private Object object;

    private Builder() {}

    public Builder setIdentifier(String identifier) {
      this.identifier = identifier;
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