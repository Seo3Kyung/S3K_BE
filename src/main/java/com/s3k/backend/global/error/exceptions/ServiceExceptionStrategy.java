package com.s3k.backend.global.error.exceptions;

import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.global.error.ExceptionHandlerStrategy;
import com.s3k.backend.global.error.WalkiException;
import org.springframework.stereotype.Component;

@Component
public class ServiceExceptionStrategy implements ExceptionHandlerStrategy {

  @Override
  public boolean supports(Exception ex) {
    return ex instanceof WalkiException;
  }

  @Override
  public ApisResponse<?> handle(Exception ex) {
    WalkiException we = (WalkiException) ex;
    String cause = we.getStatus().getDesc();
    return ApisResponse.error(cause, we.getStatus());
  }
}
