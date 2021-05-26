package databases.crud.sql.postgresql.statements;

public class TableExistsStatement {
    public static String create(final String schemaName, final String tableName) {
        final String template = "SELECT EXISTS(SELECT 1 FROM pg_tables WHERE schemaname = '%s' AND tablename = '%s');";
        return String.format(template, schemaName, tableName);
    }
}
