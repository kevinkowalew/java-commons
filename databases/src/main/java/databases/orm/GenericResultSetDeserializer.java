package databases.orm;

import databases.crud.core.ResultSetDeserializer;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GenericResultSetDeserializer<T> extends ResultSetDeserializer<T> {
    public GenericResultSetDeserializer(Class<T> tClass) {
        this.tClass = tClass;
    }

    private final Class<T> tClass;

    @Override
    public Optional<T> deserializeResultSet(ResultSet resultSet) {
        final List<Field> persistedFields = Helpers.getAllPersistedFieldsForClass(tClass);
        return deserializeFieldsFromResultSet(persistedFields, resultSet);
    }

    public Optional<T> deserializeFieldsFromResultSet(List<Field> persistedFields, ResultSet resultSet) {
        final T object = createGenericClassInstance();
        if (object != null) {
            for (Field persistedField : persistedFields) {
                populateFieldFromResultSet(object, persistedField, resultSet);
            }

            return Optional.of(object);
        } else {
            return Optional.empty();
        }
    }

    private void populateFieldFromResultSet(T t, Field field, ResultSet resultSet) {
        if (field.getType().equals(String.class)) {
            populateStringFieldFromResultSet(t, field, resultSet);
        } else if (field.getType().equals(Integer.class)) {
            populateIntegerFieldFromResultSet(t, field, resultSet);
        } else {
            // TODO: add error logging error
        }
    }

    private void populateIntegerFieldFromResultSet(T t, Field field, ResultSet resultSet) {
        try {
            final Integer value = resultSet.getInt(field.getName());
            field.set(t, value);
        } catch (SQLException throwables) {
            // TODO: add error logging error
        } catch (IllegalAccessException e) {
            // TODO: add error logging error
        }
    }

    private void populateStringFieldFromResultSet(T t, Field field, ResultSet resultSet) {
        try {
            final String value = resultSet.getString(field.getName());
            field.set(t, value);
        } catch (SQLException throwables) {
            // TODO: add error logging error
        } catch (IllegalAccessException e) {
            // TODO: add error logging error
        }
    }

    @Nullable
    private T createGenericClassInstance() {
        try {
            return tClass.newInstance();
        } catch (IllegalAccessException e) {
            // TODO: add error logging
            return null;
        } catch (InstantiationException e) {
            // TODO: add error logging
            return null;
        }
    }

    @Override
    public Class<T> getGenericClassReference() {
        return tClass;
    }
}
