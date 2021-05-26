package test.mocks;

import databases.crud.sql.Column;

public class MockUsersColumn {
    private static final String TABLE_NAME = "Users";

    public final static Column ID = Column.newBuilder()
            .serialPrimaryKey()
            .named("id")
            .parentTableName(TABLE_NAME)
            .build();
    public final static Column EMAIL = Column.newBuilder()
            .type(Column.Type.VARCHAR_255)
            .named("email")
            .required()
            .parentTableName(TABLE_NAME)
            .build();
    public final static Column SALT = Column.newBuilder()
            .type(Column.Type.VARCHAR_255)
            .named("salt")
            .required()
            .parentTableName(TABLE_NAME)
            .build();
    public final static Column HASHED_PASSWORD = Column.newBuilder()
            .type(Column.Type.VARCHAR_255)
            .named("hash_password")
            .required()
            .parentTableName(TABLE_NAME)
            .build();
}
