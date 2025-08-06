package com.s3k.backend.global.error.exceptions;

import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.global.enums.ApiResponseStatus;
import com.s3k.backend.global.error.ExceptionHandlerStrategy;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;

@Component
public class HttpMessageNotReadableStrategy implements ExceptionHandlerStrategy {

  @Override
  public boolean supports(Exception ex) {
    // @RequestBody가 비어있을때 발생하는 오류
    return ex instanceof HttpMessageNotReadableException;
  }

  @Override
  public ApisResponse<?> handle(Exception ex) {
    HttpMessageNotReadableException hre = (HttpMessageNotReadableException) ex;

    return ApisResponse.error("Body Data가 존재하지 않습니다", ApiResponseStatus.BAD_REQUEST);
  }
}
