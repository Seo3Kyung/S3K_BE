package com.s3k.backend.global.error.exceptions;

import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.global.enums.ApiResponseStatus;
import com.s3k.backend.global.error.ExceptionHandlerStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;

@Component
public class MissingParameterExceptionStrategy implements ExceptionHandlerStrategy {

  @Override
  public boolean supports(Exception ex) {
    // @RequestParam을 사용한곳에 파라미터가 오류
    return ex instanceof MissingServletRequestParameterException;
  }

  @Override
  public ApisResponse<?> handle(Exception ex) {
    MissingServletRequestParameterException msrp = (MissingServletRequestParameterException) ex;
    return ApisResponse.error(msrp.getMessage(), ApiResponseStatus.BAD_REQUEST);
  }
}
