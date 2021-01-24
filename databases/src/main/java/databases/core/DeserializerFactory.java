package databases.core;

public interface DeserializerFactory {
    Deserializer getDeserializerForRequest(DatabaseRequest request);
}
