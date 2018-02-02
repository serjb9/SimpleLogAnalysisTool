# SimpleLogAnalysisTool
The tool allows to scan a directory for applicable pattern with some custom filter and grouping and produces output after scanning.

------------

### Build
```
mvn clean compile assembly:single
```
------------
### Run

```java
java -jar -Dconfig.path="path\\to\\config\\prop.properties" SimpleLogAnalysisTool-0.1-SNAPSHOT-jar-with-dependencies.jar
```
------------
### Configuration
At first, you should configure properties file and provide the path to the application using `config.path`.

#### Prop list:
- `directory` - directory to be scanned;

- `log.file.pattern.regexp`- desirable pattern for a file to be searched (regexp);

- `record.date.format` -  expected date format of a record in a file;

- `record.date.pattern.regexp` -  regex for expected date format in a file (filter purposes);

- `period` -  filter period, format `2018-01-01 11:00:00,000 - 2018-03-04 00:00:00,000`;

- `username` -  string, empty will be replaced with `(.*)`;

- `custom.message` -  string, empty will be replaced with `(.*)`;

- `group.by` -  aggregation parameter, acceptable values: `username`, `date, hour`,  `date, day`, `date, month;`

- `output.directory` -  output directory for csv file report, if empty - a report will be placed in the application directory;




