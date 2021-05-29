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
    private static MockNestedModel MOCK_NESTED_MODEL;
    private final static String MOCK_NAME = "John Doe";
    private final static String ID = "id";
    private final static String MOCK_TEXT = "mock text";
    private final static String MOCK_EMAIL_ONE = "john.doe@gmail.com";
    private final static String MOCK_EMAIL_TWO = "jane.doe@gmail.com";
    private final static Database<MockModel> MODEL_DATABASE = Database.storing(MockModel.class);
    private final static Database<MockNestedModel> NESTED_MODEL_DATABASE = Database.storing(MockNestedModel.class);

    @BeforeClass
    public static void bootstrap() {
        MOCK_MODEL = new MockModel();
        MOCK_MODEL.setEmail(MOCK_EMAIL_ONE);
        MOCK_MODEL.setName(MOCK_NAME);

        MOCK_NESTED_MODEL = new MockNestedModel();
        MOCK_NESTED_MODEL.setNestedPersistedModel(MOCK_MODEL);
        MOCK_NESTED_MODEL.setRootLevelField(MOCK_TEXT);
    }

    @Before
    public void setup() {
        MODEL_DATABASE.clearAllData();
        NESTED_MODEL_DATABASE.clearAllData();
    }

    @Test
    public void test_Insert_And_Read() {
        // Arrange...
        final MockModel recordToInsert = new MockModel();
        recordToInsert.setName(MOCK_NAME);
        recordToInsert.setEmail(MOCK_EMAIL_ONE);

        // Act...
        final Optional<MockModel> insertResult = MODEL_DATABASE.insert(recordToInsert);

        // Assert...
        assert (insertResult.isPresent());
        assertEquals(insertResult.get().getId().intValue(), 1);
        assertEquals(insertResult.get().getName(), MOCK_NAME);
        assertEquals(insertResult.get().getEmail(), MOCK_EMAIL_ONE);
    }

    @Test
    public void test_ReadAll_HappyPath() {
        // Arrange...
        insertMockModel();
        insertMockModel();
        insertMockModel();
        final Filter readAllFilter = MODEL_DATABASE.newFilterBuilder().build();
        final Filter singleIdFilter = MODEL_DATABASE.newFilterBuilder().where(ID).equalTo(1).build();
        final Filter emptyFilter = MODEL_DATABASE.newFilterBuilder().where(ID).equalTo(4).build();

        // Act...
        final Optional<List<MockModel>> readAllResult = MODEL_DATABASE.read(readAllFilter);
        final Optional<List<MockModel>> singleIdResult = MODEL_DATABASE.read(singleIdFilter);
        final Optional<List<MockModel>> emptyResult = MODEL_DATABASE.read(emptyFilter);

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
        final boolean success = MODEL_DATABASE.update(updatedModel);

        // Assert...
        assert (success);
        Optional<MockModel> updatedRecord = getFirstUserFromDatabase(MODEL_DATABASE);
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

    @Test
    public void test_insert_and_read_nested_model_happy_path() {
        // Arrange...
        insertMockNestedModel();
        final Filter filter = NESTED_MODEL_DATABASE.newFilterBuilder().build();

        // Act...
        final Optional<List<MockNestedModel>> result = NESTED_MODEL_DATABASE.read(filter);

        // Assert...
        assert(result.isPresent());
        assertEquals(result.get().size(), 1);
        assertEquals(result.get().get(0), MOCK_NESTED_MODEL);
    }

    private void insertMockModel() {
        MODEL_DATABASE.insert(MOCK_MODEL);
    }
    
    private void insertMockNestedModel() {
        NESTED_MODEL_DATABASE.insert(MOCK_NESTED_MODEL);
    }

    private Optional<MockModel> getFirstUserFromDatabase(Database database) {
        return database.read().stream().findFirst();
    }
}