package com.s3k.backend.global.error.exceptions;

import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.global.enums.ApiResponseStatus;
import com.s3k.backend.global.error.ExceptionHandlerStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@Component
public class HttpRequestMethodNotSupportedExceptionStrategy implements ExceptionHandlerStrategy {

  @Override
  public boolean supports(Exception ex) {
    // 허용되지 않은 HTTP 메서드 오류
    return ex instanceof HttpRequestMethodNotSupportedException;
  }

  @Override
  public ApisResponse<?> handle(Exception ex) {
    return ApisResponse.error(ApiResponseStatus.METHOD_NOT_ALLOWED);
  }
}
