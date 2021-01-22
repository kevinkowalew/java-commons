package test.mocks;

public class MockSqlStatements {
    public static final String CREATE_TABLE = "CREATE TABLE Persons (Id int, Name varchar(255));";
    public static final String DROP_TABLE = "DROP TABLE Persons;";
    public static final String TABLE_EXISTS = "SELECT EXISTS (SELECT FROM information_schema.tables WHERE  table_schema = 'schema_name' AND table_name = 'Persons')";
    public static final String INSERT_USER = "INSERT INTO Persons (Id, Name) VALUES (0, 'Kevin');";
    public static final String SELECT_ALL = "SELECT * from Persons;";
}

