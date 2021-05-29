package test.orm;

import databases.orm.Database;
import databases.orm.Filter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class DatabaseIntegrationTests {
    private static MockModel MOCK_MODEL;
    private final static String MOCK_NAME = "John Doe";
    private final static String ID = "id";
    private final static String MOCK_EMAIL_ONE = "john.doe@gmail.com";
    private final static String MOCK_EMAIL_TWO = "jane.doe@gmail.com";
    private final static Database<MockModel> SUT = Database.storing(MockModel.class);

    @BeforeClass
    public static void bootstrap() {
        MOCK_MODEL = new MockModel();
        MOCK_MODEL.setEmail(MOCK_EMAIL_ONE);
        MOCK_MODEL.setName(MOCK_NAME);
    }

    @Before
    public void setup() {
        SUT.clearAllData();
    }

    @Test
    public void test_Insert_And_Read() {
        // Arrange...
        final MockModel recordToInsert = new MockModel();
        recordToInsert.setName(MOCK_NAME);
        recordToInsert.setEmail(MOCK_EMAIL_ONE);

        // Act...
        final Optional<MockModel> insertResult = SUT.insert(recordToInsert);

        // Assert...
        assert (insertResult.isPresent());
        assertEquals(insertResult.get().getId().intValue(), 1);
        assertEquals(insertResult.get().getName(), MOCK_NAME);
        assertEquals(insertResult.get().getEmail(), MOCK_EMAIL_ONE);
    }

    @Test
    public void test_ReadAll_HappyPath() {
        // Arrange...
        insertMockUser();
        insertMockUser();
        insertMockUser();
        final Filter readAllFilter = SUT.newFilterBuilder().build();
        final Filter singleIdFilter = SUT.newFilterBuilder().where(ID).equalTo(1).build();
        final Filter emptyFilter = SUT.newFilterBuilder().where(ID).equalTo(4).build();

        // Act...
        final Optional<List<MockModel>> readAllResult = SUT.read(readAllFilter);
        final Optional<List<MockModel>> singleIdResult = SUT.read(singleIdFilter);
        final Optional<List<MockModel>> emptyResult = SUT.read(emptyFilter);

        // Assert...
        assert(readAllResult.isPresent());
        assertEquals(readAllResult.get().size(), 3);

        assert(singleIdResult.isPresent());
        assertEquals(singleIdResult.get().size(), 1);

        assert(emptyResult.isPresent());
        assert(emptyResult.get().isEmpty());
    }

    @Test
    public void test_update_happy_path() {
        // Arrange...
        final MockModel updatedModel = MOCK_MODEL.duplicate();
        updatedModel.setEmail(MOCK_EMAIL_TWO);

        // Act...
        final boolean success = SUT.update(updatedModel);

        // Assert...
        assert (success);
        Optional<MockModel> updatedRecord = getFirstUserFromDatabase(SUT);
        assert (updatedRecord.isPresent());
        assertEquals(updatedRecord.get().getId().intValue(), 1);
        assertEquals(updatedRecord.get().getName(), MOCK_NAME);
        assertEquals(updatedRecord.get().getEmail(), MOCK_EMAIL_TWO);
    }

    @Test
    public void test_delete_happy_path() {
        // Arrange...

        // Act...

        // Assert...
    }

    private void insertMockUser() {
        SUT.insert(MOCK_MODEL);
    }

    private Optional<MockModel> getFirstUserFromDatabase(Database database) {
        return database.read().stream().findFirst();
    }
}