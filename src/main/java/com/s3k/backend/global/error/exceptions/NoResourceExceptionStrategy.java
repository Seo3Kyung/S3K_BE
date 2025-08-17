package com.s3k.backend.global.error.exceptions;

import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.global.enums.ApiResponseStatus;
import com.s3k.backend.global.error.ExceptionHandlerStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Component
public class NoResourceExceptionStrategy implements ExceptionHandlerStrategy {

  @Override
  public boolean supports(Exception ex) {
    // 존재하지 않는 리소스
    return ex instanceof NoResourceFoundException;
  }

  @Override
  public ApisResponse<?> handle(Exception ex) {
    return ApisResponse.error(ApiResponseStatus.NOT_FOUND);
  }
}
