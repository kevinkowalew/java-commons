package databases.orm;

import java.util.List;
import java.util.Optional;

public interface CrudOperable<T> {
    Optional<T> insert(T t);
    Optional<List<T>> read(Filter... filters);
    boolean update(T t, Filter... filters);
    boolean delete(T t, Filter... filters);
}
