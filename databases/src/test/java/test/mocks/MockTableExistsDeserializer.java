package test.mocks;

import databases.core.Deserializer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MockTableExistsDeserializer implements Deserializer {
    @Override
    public Object deserialize(Object object) {
        if (!(object instanceof ResultSet)) {
            return false;
        }

        try {
            return ((ResultSet) object).next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
}
