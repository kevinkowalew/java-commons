package test.mocks;

import databases.core.ResultSetDeserializer;
import databases.sql.Column;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static test.mocks.MockUsersColumn.*;

public class MockUserDeserializer extends ResultSetDeserializer<MockUser> {

    public Optional<MockUser> deserializeResultSet(ResultSet resultSet) {
        Integer id = extractFromResultSet(resultSet, ID, -1);
        String email = extractFromResultSet(resultSet, EMAIL);
        String salt = extractFromResultSet(resultSet, SALT);
        String hashedPassword = extractFromResultSet(resultSet, HASHED_PASSWORD);
        return Optional.of( new MockUser(id, email, salt, hashedPassword) );
    }

    @Override
    public Class<MockUser> getGenericClassReference() {
        return MockUser.class;
    }
}