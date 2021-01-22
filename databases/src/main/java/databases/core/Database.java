package databases.core;

/**
 * Dao interface used for dependency inversion
 */
public interface Database {
    DatabaseResponse processRequest(DatabaseRequest request);
}
