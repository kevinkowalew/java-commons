package databases;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public interface Dao<T> {
    boolean create(T object);
    Collection<T> read(Map<String,Object> query);
    boolean update(Function<T,Boolean> function, Map<String,Object> mutations);
    boolean delete(Function<T,Boolean> function);
}
