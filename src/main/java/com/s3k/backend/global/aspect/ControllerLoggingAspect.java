package com.s3k.backend.global.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class ControllerLoggingAspect {

  private static final String REQUEST_PREFIX = "▶️";
  private static final String RESPONSE_PREFIX = "✅";

  private final ObjectMapper objectMapper;

  @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
  public void restController() {
  }

  @Around("restController()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    String className = methodSignature.getDeclaringType().getSimpleName();
    String methodName = methodSignature.getName();
    Object[] args = joinPoint.getArgs();

    Optional<HttpServletRequest> request = getCurrentRequest();
    if (request.isEmpty()) {
      return joinPoint.proceed();
    }

    requestLog(className, methodName, args, request.get());

    long start = System.nanoTime();
    Object result = joinPoint.proceed();
    responseLog(result, start);
    return result;
  }

  private void requestLog(String className, String methodName, Object[] args,
      HttpServletRequest request) {
    String httpMethod = request.getMethod();
    String uri = request.getRequestURI();
    String username = getCurrentUserId();

    log.info("{} API - [{}] {}", REQUEST_PREFIX, httpMethod, uri);
    log.info("{} Request Path - {}.{}", REQUEST_PREFIX, className, methodName);
    log.info("{} Parameter - {}", REQUEST_PREFIX, serializeArgs(args));
    log.info("{} UserId : {}", REQUEST_PREFIX, username);
  }

  private void responseLog(Object result, long start) {
    long elapsedMs = Duration.ofNanos(System.nanoTime() - start).toMillis();
    String elapsed = String.format("%.2f", elapsedMs / 1000.0);

    log.info("{} 실행 소요 시간: {}초", RESPONSE_PREFIX, elapsed);
    log.info("{} Response Data : {}", RESPONSE_PREFIX, result);
  }

  private String serializeArgs(Object[] args) {
    return Arrays.stream(args)
        .map(arg -> {
          try {
            return objectMapper.writeValueAsString(arg);
          } catch (Exception e) {
            return String.valueOf(arg);
          }
        })
        .collect(Collectors.joining(", "));
  }

  private Optional<HttpServletRequest> getCurrentRequest() {
    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    return Optional.ofNullable(attrs)
        .map(ServletRequestAttributes::getRequest);
  }

  private String getCurrentUserId() {
    try {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();

      if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
        return auth.getName();
      }

      return "guest";
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return null;
  }
}
