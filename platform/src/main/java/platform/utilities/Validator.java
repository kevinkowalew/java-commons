package platform.utilities;

public interface Validator<T> {
	boolean validate(T field);
}
