package databases;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

class GenericDao<KeyType,ObjectType extends KeyProvider<KeyType>> implements Dao<KeyType, ObjectType> {
    private final Set<ObjectType> objects;

    GenericDao() {
        this.objects = new HashSet<>();
    }

    @Override
    public Result create(ObjectType object) {
        if (objects.contains(object)) {
            return Result.ENTITY_ALREADY_EXISTS;
        }

        boolean result = objects.add(object);
        return result ? Result.SUCCESS : Result.INSERTION_FAILURE;
    }

    @Override
    public Optional<ObjectType> read(KeyType key) {
        return objects.stream()
                .filter(o -> o.getKey() == key)
                .findFirst();
    }

    @Override
    public Result update(ObjectType object) {
        if (!objects.contains(object)) {
            return Result.NONEXISTENT_ENTITY;
        } else {
            objects.add(object);
            return Result.SUCCESS;
        }
    }

    @Override
    public Result delete(KeyType key) {
        Optional<ObjectType> object = read(key);

        if (!object.isPresent()) {
            return Result.NONEXISTENT_ENTITY;
        }

        boolean success = objects.remove(object.get());
        return success ? Result.SUCCESS : Result.DELETION_FAILURE;
    }
}