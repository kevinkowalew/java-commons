package databases.components;

import databases.core.Deserializer;
import databases.postgresql.PostgresqlDeserializer;

import java.sql.ResultSet;

public class DeserializerToPostgresqlDeserializer<Model> implements PostgresqlDeserializer<Model> {
    private final Deserializer<ResultSet, Model> deserializer;

    public DeserializerToPostgresqlDeserializer(Deserializer<ResultSet, Model> deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public Model deserialize(ResultSet object) {
        return deserializer.deserialize(object);
    }
}
