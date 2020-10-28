package databases;

public interface KeyProvider<KeyType> {
    KeyType getKey();
}
