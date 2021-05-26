package databases.crud.core;

import databases.crud.sql.postgresql.statements.builders.*;

import java.util.List;
import java.util.Optional;

/**
 * Dao interface used for dependency inversion with relational data stores
 */
public interface RelationalDatabase<T> {
    Optional<T> insert(InsertStatement.Builder builder);

    Optional<List<T>> read(SelectStatement.Builder builder);

    Boolean update(UpdateStatement.Builder builder);

    Boolean delete(DeleteStatement.Builder builder);

    Optional<List<T>> join(JoinStatement.Builder builder);
}
