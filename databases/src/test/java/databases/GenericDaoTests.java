package databases;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class GenericDaoTests {
    static private MockUserDao sut;
    static private List<MockUser> mockUsers;
    static private List<MockUser> mockUsersSubset;
    static private final String MOCK_USER_NAME = "Jane Doe";
    static private final String UPDATED_MOCK_USER_NAME = "Jane Doe";

    @BeforeClass
    public static void setup() {
        mockUsers = createMockUsers();

        mockUsersSubset = createMockUsers();
        Collections.shuffle(mockUsersSubset);
        mockUsersSubset = mockUsersSubset.subList(0, mockUsersSubset.size() / 2);
    }

    @Test
    public void test_GenericDao_CREATE_READ() {
        // Arrange
        sut = new MockUserDao();

        // Act
        mockUsers.forEach(sut::create);

        // Assert
        mockUsers.forEach(this::assertNonemptyUserOptional);
    }



    @Test
    public void test_GenericDao_UPDATE() {
        // Arrange
        sut = new MockUserDao();

        // Act
        mockUsers.forEach(sut::create);
        mockUsersSubset.stream()
                .map(this::updateMockUserName)
                .forEach(sut::update);

        // Assert
        mockUsersSubset.stream()
                .filter(mockUsersSubset::contains)
                .forEach(this::assertUpdatedMockUserName);
        mockUsers.stream()
                .filter(u -> !mockUsersSubset.contains(u))
                .forEach(this::assertNonemptyUserOptional);
    }


    @Test
    public void test_GenericDao_DELETE() {
        // Arrange
        sut = new MockUserDao();

        // Act
        mockUsers.forEach(sut::create);
        mockUsersSubset.stream()
                .map(MockUser::getKey)
                .forEach(sut::delete);

        // Assert
        mockUsersSubset.forEach(this::assertEmptyUserOptional);
        mockUsers.stream()
                .filter(u -> !mockUsersSubset.contains(u))
                .forEach(this::assertNonemptyUserOptional);
    }

    private static List<MockUser>createMockUsers() {
        return IntStream.range(0, 100)
                .mapToObj(Integer::toString)
                .map(u -> new MockUser(u, MOCK_USER_NAME))
                .collect(Collectors.toList());
    }

    private void assertNonemptyUserOptional(MockUser user) {
        Optional<MockUser> fetchedUser = sut.read(user.getKey());
        assertTrue(fetchedUser.isPresent());
    }

    private void assertEmptyUserOptional(MockUser user) {
        Optional<MockUser> fetchedUser = sut.read(user.getKey());
        assertFalse(fetchedUser.isPresent());
    }

    private MockUser updateMockUserName(MockUser user) {
        user.setName(UPDATED_MOCK_USER_NAME);
        return user;
    }

    private void assertUpdatedMockUserName(MockUser user) {
        assertEquals(user.getName(), UPDATED_MOCK_USER_NAME);
    }
}