package test.mocks;

import databases.sql.Column;

public class MockUsersColumn {
    public final static Column ID = Column.newBuilder()
            .serialPrimaryKey()
            .named("id")
            .build();
    public final static Column EMAIL = Column.newBuilder()
            .type(Column.Type.VARCHAR_255)
            .named("email")
            .required()
            .build();
    public final static Column SALT = Column.newBuilder()
            .type(Column.Type.VARCHAR_255)
            .named("salt")
            .required()
            .build();
    public final static Column HASHED_PASSWORD = Column.newBuilder()
            .type(Column.Type.VARCHAR_255)
            .named("hash_password")
            .required()
            .build();
}
