package com.s3k.backend.global.error;

import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.global.enums.ApiResponseStatus;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler {

  private final List<ExceptionHandlerStrategy> exceptionHandlerStrategies;

  @ExceptionHandler(Exception.class)
  public ApisResponse<?> handleException(final Exception ex, HttpServletRequest request) {

    String url = request.getMethod() + " " + request.getRequestURI();
    String errorData = "-";

    for (ExceptionHandlerStrategy exception : exceptionHandlerStrategies) {
      if (exception.supports(ex)) {
        ApisResponse<?> handle = exception.handle(ex);

        if (handle.getData() != null) {
          errorData = String.valueOf(handle.getData());
        }

        log.error("{}|{}|{}", url, handle.getMessage(), errorData);
        return handle;
      }
    }

    log.error("{}|{}|{}", url, ex.getMessage(), errorData, ex);
    return ApisResponse.error("알 수 없는 오류", ApiResponseStatus.INTERNAL_ERROR);
  }

}
