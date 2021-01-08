package databases.postgresql;

import databases.core.Deserializer;

import java.sql.ResultSet;

public interface PostgresqlDeserializer<T> extends Deserializer<ResultSet, T> {
}
