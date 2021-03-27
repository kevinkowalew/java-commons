package test.mocks;

import databases.sql.Column;

public class MockColumns {
    public final static Column ID = Column.with("Id", Column.Type.SERIAL_PRIMARY_KEY, true);
    public final static Column EMAIL = Column.with("Email", Column.Type.VARCHAR_255, true);
    public final static Column SALT = Column.with("Salt", Column.Type.VARCHAR_255, true);
    public final static Column HASHED_PASSWORD = Column.with("Hashed Password", Column.Type.VARCHAR_255, true);
}
