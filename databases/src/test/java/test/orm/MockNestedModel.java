package test.orm;

import databases.orm.annotations.Persisted;
import databases.orm.annotations.PrimaryKey;

import java.util.Objects;

public class MockNestedModel {
    @PrimaryKey
    public Integer id;

    @Persisted
    public MockModel nestedPersistedModel;

    @Persisted
    public String rootLevelField;

    public MockNestedModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MockModel getNestedPersistedModel() {
        return nestedPersistedModel;
    }

    public void setNestedPersistedModel(MockModel nestedPersistedModel) {
        this.nestedPersistedModel = nestedPersistedModel;
    }

    public String getRootLevelField() {
        return rootLevelField;
    }

    public void setRootLevelField(String rootLevelField) {
        this.rootLevelField = rootLevelField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MockNestedModel that = (MockNestedModel) o;
        return Objects.equals(id, that.id) && Objects.equals(nestedPersistedModel, that.nestedPersistedModel) && Objects.equals(rootLevelField, that.rootLevelField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nestedPersistedModel, rootLevelField);
    }
}