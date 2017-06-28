# A Spring Boot Starter for the request logging lib

It started as a tutorial for [Creating a Spring Boot Starter](http://codeboje.de/create-spring-boot-starter/) but evolved as a utiity lib.


# Install
The libs are not in Maven Central (yet), so you have to clone and build the [request logging lib](https://github.com/azarai/request-logging)
and this starter.

```
mvn clean install
```

Add the dependencies to your pom:

```xml
<dependency>
  <groupId>de.codeboje</groupId>
  <artifactId>request-logging-spring-boot-parent</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Now add _requestId_ as a user variable to your logback log pattern like:

```
%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} %X{requestId} - %msg
```
