package databases.sql.postgresql.statements.builders;

public class DropTableStatement {
    public final static String DELETE_TEMPLATE = "DROP TABLE %s;";
    public final static String CASCADE_DELETE_TEMPLATE = "DROP TABLE %s CASCADE;";
}
