package commons;

import java.util.Optional;

public interface OptionalProvider<T> {
    Optional<T> get();
}