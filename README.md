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
%clr(%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %X{requestId} - %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}

```
Extends the default Spring boot 2 log pattern.

Send the header _X-REQUEST-ID_ with a unique ID when a user triggers an event like clicking the order button. This ID will now be passed along as long as the _RequestContextLoggingFilter_ is active and you use Springs _RestTemplate_ for subrequests.
