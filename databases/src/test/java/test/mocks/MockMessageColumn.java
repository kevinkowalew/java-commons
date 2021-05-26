package test.mocks;

import databases.crud.sql.Column;

import static databases.crud.sql.Column.Type.VARCHAR_255;

public class MockMessageColumn {
    private static final Column USERS_ID = MockUsersColumn.ID;
    private static final String TABLE_NAME = "Messages";

    public static final Column ID = Column.newBuilder()
            .serialPrimaryKey()
            .named("id")
            .parentTableName(TABLE_NAME)
            .build();
    public static final Column SENDER_ID = Column.newBuilder()
            .foreignKey(USERS_ID)
            .named("sender_id")
            .parentTableName(TABLE_NAME)
            .required()
            .build();
    public static final Column RECIPIENT_ID = Column.newBuilder()
            .foreignKey(USERS_ID)
            .named("recipient_id")
            .parentTableName(TABLE_NAME)
            .required()
            .build();
    public static final Column TEXT = Column.newBuilder()
            .type(VARCHAR_255)
            .named("text")
            .parentTableName(TABLE_NAME)
            .build();
}
