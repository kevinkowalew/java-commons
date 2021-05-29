package databases.orm;

import java.util.function.Function;

public class NamedFunction<T, R> {
    private final String name;
    private final Function<T, R> function;

    public NamedFunction(Function<T, R> function, String name) {
        this.function = function;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Function<T, R> getFunction() {
        return function;
    }
}
