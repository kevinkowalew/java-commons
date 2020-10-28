package databases;

import java.util.Objects;

public class User implements KeyProvider<String> {
    private String id;

    public User(String id) {
       this.id = id;
    }

    @Override
    public String getKey() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
