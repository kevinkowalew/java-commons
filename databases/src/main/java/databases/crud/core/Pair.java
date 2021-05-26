package databases.crud.core;

public class Pair<T> {
    private final T leading;
    private final T trailing;

    public Pair(T leading, T trailing) {
        this.leading = leading;
        this.trailing = trailing;
    }

    public T getLeading() {
        return leading;
    }

    public T getTrailing() {
        return trailing;
    }
}
