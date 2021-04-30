package databases.sql.postgresql.statements.builders;

public class DropTableStatement {
    public DropTableStatement() {
    }

    public static String create(String tableName) {
        return String.format("DROP TABLE \"%s\";", tableName);
    }
}
