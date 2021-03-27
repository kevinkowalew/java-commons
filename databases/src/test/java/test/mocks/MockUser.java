package test.mocks;

public class MockUser {
    String id;
    String email;
    String salt;
    String hashedPassword;

    public MockUser(String id, String name, String salt, String hashedPassword) {
        this.id = id;
        this.email = name;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}