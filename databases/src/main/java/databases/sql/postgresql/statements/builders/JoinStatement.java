package databases.sql.postgresql.statements.builders;

import databases.core.Pair;
import databases.sql.Column;
import databases.sql.postgresql.statements.DatabaseTableSchema;
import databases.sql.postgresql.statements.Formatter;

import java.util.*;
import java.util.stream.Collectors;

public class JoinStatement {
    public static Builder newBuilder(DatabaseTableSchema schema) {
        return new Builder(schema.getTableName());
    }

    public static class Builder {
        private final String tableName;
        private final List<Column> selectedColumns = new ArrayList<>();
        private final List<Join> joins = new ArrayList<>();

        public Builder(String tableName) {
            this.tableName = tableName;
        }

        public Builder select(Column... columns) {
            Collections.addAll(selectedColumns, columns);
            return this;
        }

        public Builder join(Join join) {
            this.joins.add(join);
            return this;
        }

        public Builder on(Pair<Column> columnPair) {
            return this;
        }

        public String build() {
            final String template = "SELECT %s FROM %s;";
            final String selectedColumnsDescription = createSelectedColumnsDescription(selectedColumns);
            return String.format(template, selectedColumnsDescription, createJoinsDescription());
        }

        private String createSelectedColumnsDescription(List<Column> selectedColumns) {
            // find the potential tables we join multiple times
            final Set<String> tableNamesWithMultipleJoins = getTableNamesWithMultipleJoins();
            final Map<String,Integer> tableNameCountMap = new HashMap<>();
            final List<String> columnDescriptions = new ArrayList<>();

            for (Column column : selectedColumns) {
                final String columnTableName = column.getParentTableName();
                if (columnTableName.equals(tableName)) {
                    final String description = Formatter.createColumnDescriptionWithTableName(column);
                    columnDescriptions.add(description);
                } else if (tableNamesWithMultipleJoins.contains(columnTableName)) {
                    final Integer existingValue = tableNameCountMap.getOrDefault(columnTableName,1);
                    tableNameCountMap.put(columnTableName, existingValue + 1);

                    final String numberedTableName = column.getParentTableName() + existingValue;
                    getSelectedColumnsForTableName(tableName).stream()
                            .map(c -> createColumnDescriptionWithTableName(c, numberedTableName))
                            .forEach(columnDescriptions::add);
                } else{
                    final String description = Formatter.createColumnDescriptionWithTableName(column);
                    columnDescriptions.add(description);
                }
            }

            return columnDescriptions.stream().collect(Collectors.joining(", "));
        }

        private Set<String> getTableNamesWithMultipleJoins() {
            final Map<String,Integer> occurrenceTableNameCount = new HashMap<>();
            for(Join join : joins) {
                final String tableName = getOtherTableName(join);
                final Integer count = occurrenceTableNameCount.getOrDefault(tableName,0);
                occurrenceTableNameCount.put(tableName, count + 1);
            }
            return occurrenceTableNameCount.entrySet().stream()
                    .filter(entry -> entry.getValue() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
        }

        private List<Column> getSelectedColumnsForTableName(String tableName) {
            return selectedColumns.stream()
                    .filter(c -> c.getParentTableName().equals(tableName))
                    .collect(Collectors.toList());
        }

        private String createColumnDescriptionWithTableName(Column column, String tableName) {
            final String template = "%s.%s";
            return String.format(template, tableName, column.getName());
        }

        private String createJoinsDescription() {
            final String leadingParenthesis = "".repeat(joins.size());
            final String prefix = String.format("%s\"%s\"", leadingParenthesis, tableName);
            final String template = "%s %s";
            final String joinsString = joins.stream()
                    .map(this::createJoinDescription)
                    .collect(Collectors.joining(""));
            return String.format(template, prefix, joinsString);
        }

        private String createJoinDescription(Join join) {
            final String template = "%s \"%s\" ON %s";
            final String joinDescription = join.getTypeDescription();
            final String otherTableName = getOtherTableName(join);
            final String columnMappingDescription = createColumnMappingDescription(join);
            return String.format(template, joinDescription, otherTableName, columnMappingDescription);
        }

        private String createColumnMappingDescription(Join join) {
            final String template = "%s = %s";
            final String leadingColumnDescription = Formatter.createColumnDescriptionWithTableName(join.getColumnMapping().getLeading());
            final String trailingColumnDescription = Formatter.createColumnDescriptionWithTableName(join.getColumnMapping().getTrailing());
            return String.format(template, leadingColumnDescription, trailingColumnDescription);
        }


        private String getOtherTableName(Join join) {
            if (!join.getColumnMapping().getLeading().getParentTableName().equals(tableName)) {
                return join.getColumnMapping().getLeading().getParentTableName();
            } else {
                return join.getColumnMapping().getTrailing().getParentTableName();
            }
        }
    }
}
