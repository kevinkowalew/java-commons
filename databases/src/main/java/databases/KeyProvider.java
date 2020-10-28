package databases;

interface KeyProvider<KeyType> {
    KeyType getKey();
}
