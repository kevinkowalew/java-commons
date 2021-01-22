package test;

import databases.core.Deserializer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class DatabaseResponseGenericListDeserializer <T> implements Deserializer {
    public abstract Optional<T> deserializeRow(ResultSet resultSet);

    @Override
    public Object deserialize(Object response) {
        try {
            ResultSet resultSet = (ResultSet) response;

            List<T> returnValue = new ArrayList<>();

            while(resultSet.next()) {
                deserializeRow(resultSet).ifPresent(returnValue::add);
            }

            return returnValue;
        } catch (SQLException e) {
            return response;
        }

    }
}

