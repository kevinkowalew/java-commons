package databases;

import java.sql.ResultSet;

public interface ResultSetDeserializer<T> {
    T deserialize(ResultSet resultSet);
}
