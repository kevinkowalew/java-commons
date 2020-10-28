package databases;

public class MockUser implements KeyProvider<String> {
    private String id;
    private String name;

    public MockUser(String id, String name) {
       this.id = id;
       this.name = name;
    }

    @Override
    public String getKey() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
