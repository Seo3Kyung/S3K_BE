package com.s3k.backend.global.error.exceptions;

import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.global.enums.ApiResponseStatus;
import com.s3k.backend.global.error.ExceptionHandlerStrategy;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ConstraintViolationExceptionStrategy implements ExceptionHandlerStrategy {

  @Override
  public boolean supports(Exception ex) {
    // 컨트롤러에서 @Min 등의 검증을 실패 오류
    return ex instanceof ConstraintViolationException;
  }

  @Override
  public ApisResponse<?> handle(Exception ex) {
    ConstraintViolationException cve = (ConstraintViolationException) ex;

    String cause = cve.getConstraintViolations().stream()
        .map(v -> v.getPropertyPath() + ": " + v.getMessage())
        .collect(Collectors.joining(", "));

    return ApisResponse.error(cause, ApiResponseStatus.BAD_REQUEST);
  }
}
