package test.orm;

import databases.orm.DatabaseModel;
import databases.orm.Persist;
import databases.orm.PrimaryKey;

import java.util.Objects;

public class MockModel extends DatabaseModel {
    @PrimaryKey
    public Integer id;
    @Persist
    public String email;
    @Persist
    public String name;

    public MockModel() {
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
}
