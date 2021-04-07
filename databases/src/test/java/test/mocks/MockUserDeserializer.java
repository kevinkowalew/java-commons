package test.mocks;

import test.DatabaseResponseGenericListDeserializer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MockUserDeserializer extends DatabaseResponseGenericListDeserializer<MockUser> {

    @Override
    public Optional<MockUser> deserializeRow(ResultSet resultSet) {
        try {
            String id = String.valueOf(resultSet.getInt(MockColumns.ID.getName()));
            String email = resultSet.getString(MockColumns.EMAIL.getName());
            String salt = resultSet.getString(MockColumns.SALT.getName());
            String hashedPassword= resultSet.getString(MockColumns.HASHED_PASSWORD.getName());
            MockUser user = new MockUser(id, email, salt, hashedPassword);
            return Optional.of(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Optional.empty();
        }
    }
}