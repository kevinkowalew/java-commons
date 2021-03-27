package databases.sql.postgresql.statements;

import databases.sql.Column;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DatabaseTableSchema {
    private final String postgresqlSchemaName = "public";
    private final String tableName;
    private final Set<Column> columns;

    public DatabaseTableSchema(String tableName, Set<Column> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    public String getTableName() {
        return this.tableName;
    }

    public Set<Column> getColumns() {
        return this.columns;
    }

    public String getPostgresqlSchemaName() {
        return postgresqlSchemaName;
    }
}
