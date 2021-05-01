package test.mocks;

import databases.sql.Column;
import databases.sql.postgresql.statements.ColumnReference;

import static databases.sql.Column.Type.VARCHAR_255;

public class MockMessageColumn {
    private static ColumnReference USERS_ID = MockUsersColumn.ID.getReferenceInTable("Users");

    public static final Column ID = Column.newBuilder()
            .serialPrimaryKey()
            .named("id")
            .build();
    public static final Column SENDER_ID = Column.newBuilder()
            .foreignKey(USERS_ID)
            .named("sender_id")
            .required()
            .build();
    public static final Column RECIPIENT_ID = Column.newBuilder()
            .foreignKey(USERS_ID)
            .named("recipient_id")
            .required()
            .build();
    public static final Column TEXT = Column.newBuilder()
            .type(VARCHAR_255)
            .named("text")
            .build();
}
