package databases.core;

import java.util.List;

public class Request<Model> {
    private final RequestType type;
    private List<QueryParameter> queryParameters;
    private List<Pair> updates;

    private Request(Builder builder) {
        this.type = builder.type;
        this.queryParameters = builder.queryParameters;
        this.updates = builder.updates;
    }

    public RequestType getType() {
        return type;
    }

    public List<QueryParameter> getQueryParameters() {
        return queryParameters;
    }

    public List<Pair> getUpdates() {
        return updates;
    }

    public Builder newBuilder() {
        return new Builder();
    }

    public class Builder {
        private RequestType type;
        private List<QueryParameter> queryParameters;
        private List<Pair> updates;

        private Builder() {

        }

        public Builder setType(RequestType type) {
            this.type = type;
            return this;
        }

        public Builder setQueryParameters(List<QueryParameter> queryParameters) {
            this.queryParameters = queryParameters;
            return this;
        }

        public Builder setUpdates(List<Pair> updates) {
            this.updates = updates;
            return this;
        }

        public Request<Model> build() {
            return new Request<Model>(this);
        }
    }
}