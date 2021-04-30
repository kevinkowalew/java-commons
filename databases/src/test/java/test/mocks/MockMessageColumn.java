package test.mocks;

import databases.sql.Column;

public class MockMessageColumn {
    public static final Column ID = Column.with("id", Column.Type.SERIAL_PRIMARY_KEY, false);
    public static final Column SENDER_ID = Column.with("sender_id", Column.Type.FOREIGN_KEY, false);
    public static final Column RECIPIENT = Column.with("recipient_id", Column.Type.FOREIGN_KEY, false);
    public static final Column TEXT = Column.with("text", Column.Type.VARCHAR_255, false);
}
