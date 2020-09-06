# Java Miscellany
![](https://forthebadge.com/images/badges/made-with-java.svg)
![](https://forthebadge.com/images/badges/built-with-love.svg)
<p align="center">
    <strong>Java Miscellany</strong> is a collection of multipurpose, reusable java components.<br><br>
    <img src="https://memegenerator.net/img/instances/82234808/much-code-reuse-such-dry-wow.jpg" width="70%">
</p>

## Setting Up In Maven
1. Add jitpack repository to pom.xml
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
2. Add git repository dependency to pom.xml
```xml
<dependencies>
    <dependency>
        <groupId>com.github.kevinkowalew</groupId>
        <artifactId>java-miscellany</artifactId>
        <version>${version-number}</version>
    </dependency>
</dependency>
```