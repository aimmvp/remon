# remon

### Change server port through maven startup
```
$ mvn spring-boot:run -Drun.arguments="--server.port=9000"
```
### Create jar file
```
$ mvn package
```
### Change server port on jar startup
```
$ java -jar -Dserver.port=9000 spring-boot-helloworld-0.0.1-SNAPSHOT.jar
```

### SSOSESSION 입력 위치
```
DomainCLient.java
```