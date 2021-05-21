package databases.sql.postgresql.statements.builders;


import databases.core.Pair;
import databases.sql.Column;
import databases.sql.ColumnAlias;
import databases.sql.postgresql.statements.DatabaseTableSchema;
import databases.sql.postgresql.statements.Formatter;
import databases.sql.postgresql.statements.WhereClause;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JoinStatement {
    public static Builder newBuilder(DatabaseTableSchema schema) {
        return new Builder(schema.getTableName());
    }

    public static class Builder {
        private final String tableName;
        private final List<Column> selectedColumns = new ArrayList<>();
        private final List<Join> joins = new ArrayList<>();
        private WhereClause whereClause;

        public Builder(String tableName) {
            this.tableName = tableName;
        }

        public Builder select(Column... columns) {
            Collections.addAll(selectedColumns, columns);
            return this;
        }

        public Builder join(Join... join) {
            this.joins.addAll(Arrays.asList(join));
            return this;
        }

        public Builder where(WhereClause clause) {
            this.whereClause = clause;
            return this;
        }

        public String build() {
            final String template = "SELECT %s FROM %s %s %s;";
            final String selectedColumnsDescription = createSelectedColumnsDescription();
            final String targetTableName = surroundWithQuotes(tableName);
            final String joinDescriptions = createJoinsDescription();
            final String whereClause = createWhereClause();
            return String.format(template, selectedColumnsDescription, targetTableName, joinDescriptions, whereClause);
        }

        private String createWhereClause() {
            if (whereClause == null) {
                return "";
            } else {
                return "WHERE " + Formatter.createWhereClauseDescription(whereClause);
            }
        }

        private String surroundWithQuotes(String tableName) {
            final String template = "\"%s\"";
            return String.format(template, tableName);
        }

        private String createSelectedColumnsDescription() {
            final Set<String> tableNamesWithMultipleJoins = getTableNamesWithMultipleJoins();
            final List<String> columnDescriptions = new ArrayList<>();

            selectedColumns.stream()
                    .filter(c -> !tableNamesWithMultipleJoins.contains(c.getParentTableName()))
                    .map(Formatter::createColumnDescription)
                    .forEach(columnDescriptions::add);

            final Map<String, Integer> seenTableCountMap = new HashMap<>();
            for (Join join : joins) {
                final String parentTableName = join.getMapping().getTo().getParentTableName();
                if (!tableNamesWithMultipleJoins.contains(parentTableName)) {
                    continue;
                }

                final Integer existingCount = seenTableCountMap.getOrDefault(parentTableName, 0);
                seenTableCountMap.put(parentTableName, existingCount + 1);

                final String numberedName = parentTableName + (existingCount + 1);
                join.getSelectedColumns().stream()
                        .map(column -> createMultiTableJoinDescription(column, numberedName))
                        .forEach(columnDescriptions::add);
            }

            return flattenToCommaSeparatedString(columnDescriptions);
        }

        private String createMultiTableJoinDescription(ColumnAlias reference, String numberedName) {
            final String template = "%s.%s as %s";
            final String columnName = reference.getColumn().getName();
            return String.format(template, surroundWithQuotes(numberedName), columnName, reference.getAlias());
        }

        private String flattenToCommaSeparatedString(List<String> strings) {
            return flattenToStringWithDelimiter(strings, ", ");
        }

        private String flattenToStringWithDelimiter(List<String> strings, String delimiter) {
            return String.join(delimiter, strings);
        }

        private Set<String> getTableNamesWithMultipleJoins() {
            final Map<String, Integer> occurrenceTableNameCount = new HashMap<>();
            for (Join join : joins) {
                final String tableName = join.getMapping().getTo().getParentTableName();
                final Integer count = occurrenceTableNameCount.getOrDefault(tableName, 0);
                occurrenceTableNameCount.put(tableName, count + 1);
            }
            return occurrenceTableNameCount.entrySet().stream()
                    .filter(entry -> entry.getValue() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
        }

        private String createJoinsDescription() {
            final Set<String> tableNamesWithMultipleJoins = getTableNamesWithMultipleJoins();
            final Map<String, Integer> seenTableNameCountMap = new HashMap<>();
            final List<String> descriptions = new ArrayList<>();

            for (Join join : joins) {
                final String otherTableNameForJoin = join.getMapping().getTo().getParentTableName();
                final String typeDescription = join.getTypeDescription();

                if (tableNamesWithMultipleJoins.contains(otherTableNameForJoin)) {
                    final Integer existingValue = seenTableNameCountMap.getOrDefault(otherTableNameForJoin, 1);
                    seenTableNameCountMap.put(otherTableNameForJoin, existingValue + 1);
                    final String numberedTableName = otherTableNameForJoin + existingValue;

                    final String template = "%s %s as %s ON %s";
                    final String columnMappingDescription = createColumnMappingDescriptionWithTableName(
                            join,
                            numberedTableName
                    );
                    final String description = String.format(
                            template, typeDescription,
                            surroundWithQuotes(otherTableNameForJoin),
                            surroundWithQuotes(numberedTableName),
                            columnMappingDescription
                    );
                    descriptions.add(description);
                } else {
                    final String template = "%s %s ON %s";
                    final String columnMappingDescription = createColumnMappingDescriptionWithTableName(
                            join,
                            surroundWithQuotes(otherTableNameForJoin)
                    );
                    final String description = String.format(template, typeDescription, otherTableNameForJoin, columnMappingDescription);
                    descriptions.add(description);
                }
            }

            return descriptions.stream().collect(Collectors.joining(" "));
        }

        private String createColumnMappingDescriptionWithTableName(Join join, String tableName) {
            final String template = "%s = %s";
            final String leadingColumnDescription = createJoinTargetDescription(join.getMapping().getFrom(), tableName);
            final String trailingColumnDescription = createJoinTargetDescription(join.getMapping().getTo(), tableName);
            return String.format(template, leadingColumnDescription, trailingColumnDescription);
        }

        private String createJoinTargetDescription(Column column, String tableName) {
            Optional<String> columnAlias = getAliasForJoin(column);
            if (columnAlias.isPresent()) {
                final String template = "%s.%s as %s";
                final String formattedTableName = surroundWithQuotes(tableName);
                final String formattedAlias = surroundWithQuotes(columnAlias.get());
                return String.format(template, formattedTableName, column.getName(),formattedAlias);
            } else if (tableName.contains(column.getParentTableName())) {
                final String template = "%s.%s";
                return String.format(template, surroundWithQuotes(tableName), column.getName());
            } else {
                final String template = "%s.%s";
                final String formattedTableName = surroundWithQuotes(column.getParentTableName());
                return String.format(template, formattedTableName, column.getName());
            }
        }

        private Optional<String> getAliasForJoin(Column column) {
            return joins.stream()
                    .filter(join -> join.getMapping().getFrom().equals(column))
                    .flatMap(join -> join.getSelectedColumns().stream())
                    .filter(join -> join.getColumn().equals(column))
                    .map(ColumnAlias::getAlias)
                    .findFirst();
        }
    }
}