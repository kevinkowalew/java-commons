package databases.core;

import databases.sql.postgresql.statements.DeleteStatement;
import databases.sql.postgresql.statements.builders.InsertStatement;
import databases.sql.postgresql.statements.builders.SelectStatement;
import databases.sql.postgresql.statements.builders.UpdateStatement;

import java.util.List;
import java.util.Optional;

/**
 * Dao interface used for dependency inversion with data stores
 */
public interface Database<T> {
    Optional<T> insert(InsertStatement.Builder builder);

    Optional<List<T>> read(SelectStatement.Builder builder);

    Boolean update(UpdateStatement.Builder builder);

    Boolean delete(DeleteStatement.Builder builder);
}
