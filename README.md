# Java Miscellany
![](https://forthebadge.com/images/badges/made-with-java.svg)
![](https://forthebadge.com/images/badges/built-with-love.svg)

## Maven Integration
1. Add the following to your pom.xml
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.kevinkowalew</groupId>
        <artifactId>java-miscellany</artifactId>
        <version>${version-number}</version>
    </dependency>
</dependency>
```

## Bumping a Release
- To push a new release to Github use the following:
```shell script
$ mvn release:prepare
$ mvn release:perform
```