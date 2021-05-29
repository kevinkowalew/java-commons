package databases.orm;

import com.google.inject.internal.util.Objects;
import databases.crud.sql.Column;
import databases.orm.annotations.Persisted;
import databases.orm.annotations.PrimaryKey;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Helpers {
    static Column createColumnForField(Field field, Class<?> tClass) {
        return Column.newBuilder()
                .parentTableName(tClass.getName())
                .named(field.getName())
                .type(getTypeForField(field))
                .build();
    }

    private static Column.Type getTypeForField(Field field) {
        if (field.isAnnotationPresent(PrimaryKey.class)) {
            return Column.Type.SERIAL_PRIMARY_KEY;
        }
        Class<?> fieldClass = field.getType();
        if (field.isAnnotationPresent(Persisted.class) && fieldClass.equals(Object.class)) {
            return Column.Type.FOREIGN_KEY;
        } else {
            return Column.Type.VARCHAR_255;
        }
    }

    static <T> Optional<String> extractValueFromField(Field field, T t) {
        // TODO: expand this to support all primitives
        // TODO: make this operation safe
        try {
            Object value = field.get(t);
            return value != null ? Optional.of((String) value) : Optional.empty();
        } catch (IllegalAccessException e) {
            return Optional.empty();
        }
    }

    public static List<Field> getAllPersistedFieldsForClass(Class<?> tClass) {
        return Arrays.stream(tClass.getDeclaredFields())
                .filter(Helpers::isPersisted)
                .collect(Collectors.toList());
    }

    public static List<Field> getNestedPersistedObjectsForClass(Class<?> tClass) {
        return getAllPersistedFieldsForClass(tClass).stream()
                .filter(field -> field.getType().equals(Object.class))
                .collect(Collectors.toList());
    }

    public static boolean isPersisted(Field field) {
        return isPrimaryKey(field) || field.isAnnotationPresent(Persisted.class);
    }

    static boolean isPrimaryKey(Field field) {
        return field.isAnnotationPresent(PrimaryKey.class);
    }

    static Set<Column> getColumnsForObject(Class<?> tClass) {
        return Arrays.stream(tClass.getDeclaredFields())
                .filter(Predicate.not(Helpers::isPrimaryKey))
                .map(field -> createColumnForField(field, tClass))
                .collect(Collectors.toSet());
    }

    static Set<Column> createPersistedFieldColumns(Class<?> tClass) {
        return Helpers.getAllPersistedFieldsForClass(tClass).stream()
                .map(field -> Helpers.createColumnForField(field, tClass))
                .collect(Collectors.toSet());
    }

    public static Set<Column> createPrimaryKeyColumns(Class<?> tClass) {
        return Arrays.stream(tClass.getDeclaredFields())
                .filter(Helpers::isPrimaryKey)
                .map(field -> createColumnForField(field, tClass))
                .collect(Collectors.toSet());
    }
}
