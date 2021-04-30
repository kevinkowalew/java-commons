package test.mocks;

import databases.sql.Column;

public class MockUsersColumn {
    public final static Column ID = Column.with("id", Column.Type.SERIAL_PRIMARY_KEY, true);
    public final static Column EMAIL = Column.with("email", Column.Type.VARCHAR_255, true);
    public final static Column SALT = Column.with("salt", Column.Type.VARCHAR_255, true);
    public final static Column HASHED_PASSWORD = Column.with("hashed_password", Column.Type.VARCHAR_255, true);
}
