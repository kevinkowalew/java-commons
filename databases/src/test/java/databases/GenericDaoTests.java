package databases;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.rowset.Predicate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GenericDaoTests {
    static private MockUserDao sut;
    static private List<User> mockUsers;
    static private List<User> mockUsersToDelete;

    @BeforeClass
    public static void setup() {
        // creates mock users
        mockUsers = createMockUsers();

        // randomly selects users to delete
        mockUsersToDelete = createMockUsers();
        Collections.shuffle(mockUsersToDelete);
        mockUsersToDelete = mockUsers.subList(0, mockUsers.size() / 2);
    }

    @Test
    public void test_GenericDao_Create() {
        // Arrange
        sut = new MockUserDao();

        // Act
        mockUsers.forEach(sut::create);

        // Assert
        mockUsers.forEach(this::assertExistentUser);
    }


    @Test
    public void test_GenericDao_Delete() {
        // Arrange
        sut = new MockUserDao();

        // Act
        mockUsers.forEach(sut::create);
        mockUsersToDelete.stream().map(u -> u.getKey()).forEach(sut::delete);

        // Assert
        mockUsersToDelete.forEach(this::assertNonexistentUser);
        mockUsers.stream()
                .filter(u -> !mockUsersToDelete.contains(u))
                .forEach(this::assertExistentUser);
    }

    private static List<User>createMockUsers() {
        return IntStream.range(0, 100)
                .mapToObj(Integer::toString)
                .map(User::new)
                .collect(Collectors.toList());
    }

    private void assertExistentUser(User user) {
        Optional<User> fetchedUser = sut.read(user.getKey());
        assertTrue(fetchedUser.isPresent());
    }

    private void assertNonexistentUser(User user) {
        Optional<User> fetchedUser = sut.read(user.getKey());
        assertFalse(fetchedUser.isPresent());
    }
}
