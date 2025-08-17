package com.s3k.backend.global.error;

import com.s3k.backend.global.enums.ApiResponseStatus;
import lombok.Getter;

@Getter
public class WalkiException extends RuntimeException {

  private final ApiResponseStatus status;

  public WalkiException(final ApiResponseStatus status) {
    super(status.getDesc());
    this.status = status;
  }

}
