package test.mocks;

import databases.crud.core.ResultSetDeserializer;
import java.sql.ResultSet;
import java.util.Optional;

import static test.mocks.MockUsersColumn.*;

public class MockUserDeserializer extends ResultSetDeserializer<MockUser> {

    public Optional<MockUser> deserializeResultSet(ResultSet resultSet) {
        final Integer id = extractFromResultSet(resultSet, ID, -1);
        final String email = extractFromResultSet(resultSet, EMAIL, "");
        final String salt = extractFromResultSet(resultSet, SALT, "");
        final String hashedPassword = extractFromResultSet(resultSet, HASHED_PASSWORD, "");
        return Optional.of( new MockUser(String.valueOf(id), email, salt, hashedPassword) );
    }

    @Override
    public Class<MockUser> getGenericClassReference() {
        return MockUser.class;
    }
}