package test.mocks;

import test.DatabaseResponseGenericListDeserializer;
import test.mocks.MockUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MockUserDeserializer extends DatabaseResponseGenericListDeserializer<MockUser> {

    @Override
    public Optional<MockUser> deserializeRow(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("Id");
            String name = resultSet.getString("Name");
            MockUser user = new MockUser(id, name);
            return Optional.of(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Optional.empty();
        }
    }
}

