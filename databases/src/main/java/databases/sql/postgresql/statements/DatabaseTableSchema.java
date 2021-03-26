package databases.sql.postgresql.statements;

import databases.sql.Column;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DatabaseTableSchema {
    private final String postgresqlSchemaName = "public";
    private final String tableName;
    private final List<Column> columnList;

    public DatabaseTableSchema(String tableName, List<Column> columnList) {
        this.tableName = tableName;
        this.columnList = columnList;
    }

    public String getTableName() {
        return this.tableName;
    }

    public List<Column> getColumnList() {
        return this.columnList;
    }

    public String getPostgresqlSchemaName() {
        return postgresqlSchemaName;
    }

    public List<Column> getFilteredColumnList(Predicate<Column> predicate) {
        return (List)this.columnList.stream().filter(predicate).collect(Collectors.toList());
    }
}
