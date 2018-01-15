# A Spring Boot Starter for the request logging lib

This is a Spring Boot starter for the [request-logging](https://github.com/azarai/request-logging) lib. It helps to follow reuqests along a microservices infrastructure; think Zipkin for the poor man - simple, with no overhead of running additional systems.


# Dependency

```xml
<dependency>
  <groupId>de.codeboje</groupId>
  <artifactId>request-logging-spring-boot-starter</artifactId>
  <version>0.0.1</version>
</dependency>
```


Now add _requestId_ as a user variable to your logback log pattern like:

```
%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} %X{requestId} - %msg
```

Send the header _X-REQUEST-ID_ with a unique ID when a user triggers an event like clicking the order button. This ID will now be passed along as long as the _RequestContextLoggingFilter_ is active and you use Springs _RestTemplate_ for subrequests.
