package databases.orm;

import java.util.ArrayList;
import java.util.List;

public class Read {
    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private final List<Filter> filters = new ArrayList<>();

        private Builder() {
        }

    }
}
