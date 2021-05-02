package databases.core;

import databases.sql.Column;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ResultSetDeserializer<T> implements Deserializer {
    public Object deserialize(Object object) {
        if (!(object instanceof ResultSet)) {
            return null;
        } else {
            ResultSet resultSet = (ResultSet) object;
            List<T> returnValue = new ArrayList<>();

            try {
                while (resultSet.next()) {
                    Optional<T> results = deserializeResultSet(resultSet);
                    results.ifPresent(returnValue::add);
                }

                return returnValue;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return null;
            }
        }
    }

    public abstract Optional<T> deserializeResultSet(ResultSet resultSet);

    public abstract Class<T> getGenericClassReference();

    public String extractFromResultSet(ResultSet resultSet, Column column) {
        try {
            return resultSet.getString(column.getName());
        } catch (SQLException throwables) {
            return "";
        }
    }

    public Integer extractFromResultSet(ResultSet resultSet, Column column, Integer defaultValue) {
        try {
            return resultSet.getInt(column.getName());
        } catch (SQLException throwables) {
            return defaultValue;
        }
    }
}
