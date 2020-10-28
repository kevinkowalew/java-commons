package databases;

import java.util.Optional;

interface Dao<KeyType, ObjectType extends KeyProvider<KeyType>> {
    Result create(ObjectType object);
    Optional<ObjectType> read(KeyType key);
    Result update(ObjectType object);
    Result delete(KeyType key);
}
