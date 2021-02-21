#  Databases

![](https://forthebadge.com/images/badges/made-with-java.svg)
![](https://forthebadge.com/images/badges/built-with-love.svg)

This module contains abstractions for modeling database transactions in a manner independent of underlying database dependencies.

## Unified Interface Entry Point
```java
public interface Database {
    Optional<DatabaseResponse> processRequest(DatabaseRequest request);
}
```
