package databases.crud.sql;

import databases.crud.core.Deserializer;

public class SQLUpdateDeserializer implements Deserializer {
    public SQLUpdateDeserializer() {
    }

    public Object deserialize(Object response) {
        return response instanceof Integer && (response.equals(0) || response.equals(1));
    }
}
