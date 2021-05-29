package test.orm;

import databases.orm.annotations.Filterable;
import databases.orm.annotations.Persisted;
import databases.orm.annotations.PrimaryKey;

import java.util.Objects;

public class MockModel {
    @PrimaryKey
    public Integer id;

    @Filterable
    @Persisted
    public String email;

    @Filterable
    @Persisted
    public String name;

    public MockModel() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MockModel model = (MockModel) o;
        return Objects.equals(id, model.id) && Objects.equals(email, model.email) && Objects.equals(name, model.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name);
    }

    public MockModel duplicate() {
        final MockModel duplicate = new MockModel();
        duplicate.setId(this.getId());
        duplicate.setName(this.getName());
        duplicate.setEmail(this.getEmail());
        return duplicate;
    }
}
