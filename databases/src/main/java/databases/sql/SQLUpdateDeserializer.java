package databases.sql;

import databases.core.Deserializer;

public class SQLUpdateDeserializer implements Deserializer {
    public SQLUpdateDeserializer() {
    }

    public Object deserialize(Object response) {
        return !(response instanceof Integer) ? false : response.equals(0) || response.equals(1);
    }
}
