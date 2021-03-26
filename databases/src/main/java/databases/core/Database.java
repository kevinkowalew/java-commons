package databases.core;

import databases.sql.postgresql.statements.builders.InsertStatementBuilder;
import databases.sql.postgresql.statements.builders.SelectStatementBuilder;
import databases.sql.postgresql.statements.builders.UpdateStatementBuilder;

import java.util.Optional;

/**
 * Dao interface used for dependency inversion
 */
public interface Database {
    Optional<Object> insert(InsertStatementBuilder insertStatementBuilder);
    Optional<Object> read(SelectStatementBuilder selectStatementBuilder);
    Optional<Object> update(UpdateStatementBuilder updateStatementBuilder);
    Optional<Object> delete(SelectStatementBuilder selectStatementBuilder);
}
