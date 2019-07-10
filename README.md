# CallLimiter

This a call limit tool for Spring Boot.

## How to use

In Application class:

```java
import io.github.u10.utils.calllimiter.annotation.EnableCallLimiter;

@SpringBootApplication
@EnableCallLimiter
```

On method:

```java
import io.github.u10.utils.calllimiter.annotation.CallLimit;

@CallLimit(debounce = 500, throttle = 5000)
public void test () {
  ...
}

```
