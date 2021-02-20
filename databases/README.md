#  Databases

## Intent
- This module contains abstractions for modeling database transactions.
- By leveraging unified interfaces and POJOs for data flow in the module 

## Unified Interface
```java
public interface Database {
    Optional<DatabaseResponse> processRequest(DatabaseRequest request);
}
```
# Postgresql Setup 
