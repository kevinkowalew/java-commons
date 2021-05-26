package databases.crud.sql.postgresql.statements;

import databases.crud.sql.Column;

import java.util.Set;

public class DatabaseTableSchema {
    private final String postgresqlSchemaName;
    private final String tableName;
    private final Set<Column> columns;

    public DatabaseTableSchema(String postgresqlSchemaName, String tableName, Set<Column> columns) {
        this.postgresqlSchemaName = postgresqlSchemaName;
        this.tableName = tableName;
        this.columns = columns;
    }

    public DatabaseTableSchema(String tableName, Set<Column> columns) {
        this.postgresqlSchemaName = "public";
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
