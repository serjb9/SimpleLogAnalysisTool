# SimpleLogAnalysisTool
A tool allows to scan a directory for applicable pattern with some custom filter and grouping and produces output after scanning.


------------


###Build
```
mvn clean compile assembly:single
```


###Run
```java
java -jar -Dconfig.path="path\\to\\config\\prop.properties" SimpleLogAnalysisTool-0.1-SNAPSHOT-jar-with-dependencies.jar
```
------------