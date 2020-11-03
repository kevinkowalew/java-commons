package databases;

import java.sql.ResultSet;

interface ResultSetDeserializer<T> {
    T deserialize(ResultSet resultSet);
}
