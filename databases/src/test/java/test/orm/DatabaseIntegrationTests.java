package test.orm;

import databases.orm.Database;
import databases.orm.Filter;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class DatabaseIntegrationTests {
    private final static Database<MockModel> sut = Database.storing(MockModel.class);
    private final static String MOCK_NAME = "John Doe";
    private final static String MOCK_EMAIL = "john.doe@gmail.com";

    @Before
    public void setup() {
        sut.clearAllData();
    }


    @Test
    public void test_insert_and_read() {
        // Arrange...
        final MockModel recordToInsert = new MockModel();
        recordToInsert.setName(MOCK_NAME);
        recordToInsert.setEmail(MOCK_EMAIL);

        // Act...
        final Optional<MockModel> insertResult = sut.insert(recordToInsert);

        // Assert...
        assert(insertResult.isPresent());
        assertEquals(insertResult.get().getId().intValue(), 1);
        assertEquals(insertResult.get().getName(), MOCK_NAME);
        assertEquals(insertResult.get().getEmail(), MOCK_EMAIL);
    }

    @Test
    public void test_read_happy_path() {
        // Arrange...
        insertMockUser();
        final Filter filterOne = Filter.on("id", Filter.Operator.EQUALS, 1);


        // Act...
        final Optional<List<MockModel>> resultOne = sut.read(filterOne);

        // Assert...
        assert(resultOne.isPresent());
        assertEquals(resultOne.get().size(), 1);
    }

    @Test
    public void test_update_happy_path() {
        // Arrange...

        // Act...

        // Assert...
    }

    @Test
    public void test_delete_happy_path() {
        // Arrange...

        // Act...

        // Assert...
    }

    void insertMockUser() {
        final MockModel recordToInsert = new MockModel();
        recordToInsert.setEmail("john.doe@gmail.com");
        recordToInsert.setName("John Joe");
        sut.insert(recordToInsert);
    }
}