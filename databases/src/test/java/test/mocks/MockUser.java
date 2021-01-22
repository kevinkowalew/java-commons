package test.mocks;

import java.util.Objects;

public class MockUser {
    int id;
    String name;

    public MockUser(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MockUser mockUser = (MockUser) o;
        return id == mockUser.id && Objects.equals(name, mockUser.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

