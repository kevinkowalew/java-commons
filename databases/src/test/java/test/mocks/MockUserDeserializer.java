package test.mocks;

import databases.crud.core.ResultSetDeserializer;
import java.sql.ResultSet;
import java.util.Optional;

import static test.mocks.MockUsersColumn.*;

public class MockUserDeserializer extends ResultSetDeserializer<MockUser> {

    public Optional<MockUser> deserializeResultSet(ResultSet resultSet) {
        Integer id = extractFromResultSet(resultSet, ID, -1);
        String email = extractFromResultSet(resultSet, EMAIL).orElse("");
        String salt = extractFromResultSet(resultSet, SALT).orElse("");
        String hashedPassword = extractFromResultSet(resultSet, HASHED_PASSWORD).orElse("");
        return Optional.of( new MockUser(String.valueOf(id), email, salt, hashedPassword) );
    }

    @Override
    public Class<MockUser> getGenericClassReference() {
        return MockUser.class;
    }
}