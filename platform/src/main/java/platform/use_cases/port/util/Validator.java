package platform.use_cases.port.util;

public interface Validator<T> {
	boolean validate(T object);
}
