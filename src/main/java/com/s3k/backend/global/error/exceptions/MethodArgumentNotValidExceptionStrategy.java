package com.s3k.backend.global.error.exceptions;

import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.global.enums.ApiResponseStatus;
import com.s3k.backend.global.error.ExceptionHandlerStrategy;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
public class MethodArgumentNotValidExceptionStrategy implements ExceptionHandlerStrategy {

  @Override
  public boolean supports(Exception ex) {
    // @Valid @RequestBody 를 사용한 곳에서 검증 실패로 인한 오류
    return ex instanceof MethodArgumentNotValidException;
  }

  @Override
  public ApisResponse<?> handle(Exception ex) {
    MethodArgumentNotValidException manv = (MethodArgumentNotValidException) ex;

    String cause = manv.getBindingResult().getFieldErrors().stream()
        .map(err -> err.getField() + ": " + err.getDefaultMessage())
        .collect(Collectors.joining(", "));

    return ApisResponse.error(cause, ApiResponseStatus.BAD_REQUEST);
  }
}
