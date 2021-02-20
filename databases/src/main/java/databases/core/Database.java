package databases.core;

import java.util.Optional;

/**
 * Dao interface used for dependency inversion
 */
public interface Database {
    Optional<DatabaseResponse> processRequest(DatabaseRequest request);
}
